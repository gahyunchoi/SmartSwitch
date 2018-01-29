# Simple demo of reading each analog input from the ADS1x15 and printing it to
# the screen.
# Author: Tony DiCola
# License: Public Domain
import time

# Import the ADS1x15 module.
import Adafruit_ADS1x15

adc = Adafruit_ADS1x15.ADS1015(address=0x48, busnum=0)

# Choose a gain of 1 for reading voltages from 0 to 4.09V.
# Or pick a different gain to change the range of voltages that are read:
#  - 2/3 = +/-6.144V
#  -   1 = +/-4.096V
#  -   2 = +/-2.048V
#  -   4 = +/-1.024V
#  -   8 = +/-0.512V
#  -  16 = +/-0.256V
# See table 3 in the ADS1015/ADS1115 datasheet for more info on gain.

GAIN = 1
mVperAmp = 100
Voltage = 0
VRMS = 0
AmpsRMS = 0

def millis():
    return int(round(time.time()*1000))

def getVPP():
#    result = 0.0
    readValue =0
    maxValue = 0
    minValue = 1024
    start_time = millis()
    GAIN=1    

    while millis()-start_time < 1000:
       readValue = adc.read_adc(0,gain=GAIN)
      # print(readValue)
       if(readValue > maxValue):
           maxValue = readValue
       elif(readValue < minValue):
           minValue = readValue
    result = ((maxValue - minValue)*5.0)/1024.0
    return result

while True:
    Voltage = getVPP()
    print (Voltage)
    VRMS = (Voltage/2.1)*0.707
    AmpsRMS = (VRMS*1000)/mVperAmp
    print(AmpsRMS/2*220)

