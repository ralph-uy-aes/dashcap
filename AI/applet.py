import os
import cv2
from cv2 import FONT_HERSHEY_DUPLEX
import imutils
from imutils.video import VideoStream
from PIL import Image
import tflite_runtime.interpreter as tflite
import detect
from time import sleep
from datetime import datetime, timedelta
from csv import DictReader
import pygame
from config import *

def readLabels():
    label_dic = {}
    with open('labels.txt', 'r') as fp:
        lines = fp.readlines()
    for line in lines:
        key, value = line.split('\t')
        label_dic[key]=value.strip()

    return label_dic

def mouse_click(event, x, y, flags, param):
    if event == 7:  # If screen detects a double-click
        print("Existing...")
        cv2.destroyAllWindows()
        exit(0)

def main():
    # Load AI model and initialize it
    model_path = os.path.join('models','ssd_mobilenet_v1_1_metadata_1.tflite')
    interpreter = tflite.Interpreter(model_path)
    interpreter.allocate_tensors()

    # Read labels.txt
    label_dic = readLabels()

    # Sticky status
    sticky_status = False

    # Start camera
    use_pi_camera = True if CAMERA_TYPE == 'picam' else False
    vs = VideoStream(usePiCamera=use_pi_camera, resolution=(640, 480))
    vs.start()
    sleep(1.0)

    # Set up OpeCV window
    cv2.namedWindow("Image", cv2.WINDOW_NORMAL)
    cv2.setWindowProperty("Image", cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)
    #cv2.resizeWindow("Image", (800, 480))
    cv2.setMouseCallback("Image", mouse_click)

    last_detection_time = datetime.now() - timedelta(7)

    print("Starting...")

    # Start video
    while True:
        # Read a frame and reformat
        image = vs.read()
        #image = imutils.resize(image, width=300)
        pil_im = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        pil_im = Image.fromarray(pil_im)
        scale = detect.set_input(interpreter, pil_im.size, lambda size: pil_im.resize(size, Image.ANTIALIAS))
        
        # Detection
        (H, W) = image.shape[:2]
        interpreter.invoke()
        ans = detect.get_output(interpreter, MIN_CONFIDENCE, scale)

        detection_count_dic = {}
        detection_coordinates_dic = {}
        
        if ans: # If the model detecs anything at all
            for obj in ans: # Scan all detection results within one frame
                label_name = label_dic[str(obj.id)]

                # Do stuff only if the detected object is in OBJECTS_TO_DETECT list
                if label_name in OBJECTS_TO_DETECT:
                    # Get coordinates of detection box
                    bbox = obj.bbox
                    (startX, startY, endX, endY) = (int(bbox.xmin), int(bbox.ymin), int(bbox.xmax), int(bbox.ymax))

                    # Display label and bounding box
                    cv2.putText(image, label_name, (startX, startY-16), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 255, 0), 1)
                    cv2.rectangle(image, (startX, startY), (endX, endY), (0, 255, 0), thickness=1)

                    if label_name in detection_count_dic:
                        detection_count_dic[label_name] += 1
                        detection_coordinates_dic[label_name].append((startX, startY, endX, endY))
                    else:
                        detection_count_dic[label_name] = 1
                        detection_coordinates_dic[label_name] = [tuple([startX, startY, endX, endY])]

        
        # If there's at least one detection after an interval, then do something
        elapsed_time = (datetime.now() - last_detection_time).total_seconds()
        if detection_count_dic and elapsed_time > EVENT_INTERVAL:
            object_detected(detection_count_dic, detection_coordinates_dic)
            last_detection_time = datetime.now()
        
        
        # Show image
        cv2.imshow("Image", image)
        # Wait for a milisecond and exit app if ESC key is pressed
        key = cv2.waitKey(1)
        if key == 27:
            break

    # Close image windows and stop video stream 
    cv2.destroyAllWindows()
    vs.stop()

if __name__ == "__main__":
    main()