import time
import board
import busio as io
import pyrebase

i2c = io.I2C(board.SCL, board.SDA, frequency=100000)
detection = open("detection_data.txt", "r")

config = {
  "apiKey": "NgbKyCe7R39PoXjFcl4GTfcarJGAoyqQLFEpfCP2",
  "authDomain": "dashcap-d8eb3.firebaseapp.com",
  "databaseURL": "https://dashcap-d8eb3-default-rtdb.firebaseio.com/",
  "storageBucket": "dashcap-d8eb3.appspot.com"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()

while True:

  object = detection.readline()
  danger_rate = 0.0
  evaluation = "safe"

  if object == "person":
    danger_rate_1 += 1
  elif object == "car":
    danger_rate_1 += .25

  if danger_rate < 3:
    evaluation = "safe"
  elif danger_rate > 3 and danger < 10:
    evaluation = "warn"
  elif danger_rate > 10:
    evaluation = "danger"
   

  data = {
    "detected": object,
    "safety": evaluation,
  }
  db.child("dashcap").child("1-set").set(data)
  db.child("dashcap").child("2-push").push(data)

  time.sleep(2)