from do_stuff import *

# ---------- PARAMETERS YOU CAN MODIFY ----------
OBJECTS_TO_DETECT = ['person']      # See labels.txt for the entire list
MIN_CONFIDENCE = 0.6                # Minimum confidence lebel
CAMERA_TYPE = 'person'               # Pi Camera: 'picam', USB Webcam: 'webcam'
EVENT_INTERVAL = 1                  # interval between objecte_detected() is triggered (in seconds)
STICKINESS = 3

def object_detected(detection_dic, detection_coordinates_dic):
    print("Detected.")

    # Uncomment to activate (remove '#') ----------
    save_data(detection_dic, detection_coordinates_dic, 'detection_data.txt')
    make_noise("196106__aiwha__ding16.wav")
    # show_a_picture("images/private_property_no_trespassing.png", 3)
    # ---------------------------------------------

    return True