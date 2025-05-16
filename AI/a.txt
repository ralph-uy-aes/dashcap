from datetime import datetime
import time
import cv2
import pygame
pygame.init()
pygame.mixer.init(devicename="bcm2835 Headphones, bcm2835 Headphones")

def save_data(detection_dic, detection_coordinates_dic, filename):
    with open(filename, 'a+') as fp:
        for k,v in detection_dic.items():
            fp.write(str(datetime.now()) + '\t' + k + '\t' + str(v) + '\t' + str(detection_coordinates_dic[k]).replace('[','').replace(']','') + '\n')

    return True

def make_noise(wavfile):
    sound_obj = pygame.mixer.Sound(wavfile)
    sound_obj.play()

def show_a_picture(img_file_path, length=5):
    image=cv2.imread(img_file_path)
    cv2.imshow("Image", image)
    cv2.waitKey(length*1000)