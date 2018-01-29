import omega_gpio
import time
import socket
import onionGpio
import threading
import subprocess
import Adafruit_ADS1x15

adc = Adafruit_ADS1x15.ADS1015(address=0x48, busnum=0)
relay_gpioNum = 8
motion_gpioNum = 12
temphumi_gpioNum = 7
sensorModel = 'DHT11'
GAIN = 1
mVperAmp = 100
Voltage = 0
VRMS = 0
AmpsRMS = 0

motion = onionGpio.OnionGpio(motion_gpioNum)
#motion.setInpuection()
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind(('',2900))
sock.listen(1)

HOST='192.168.43.123' 
PORT=5500
s=socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
s.connect((HOST,PORT))
PORT2=5555
s2=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s2.connect((HOST,PORT2))
			
motion_value =0
temperature = 0
humidity = 0
elect_value = 0
onoff_flag = 0

def millis():
    return int(round(time.time()*1000))

def getVPP():
#    result = 0.0
    readValue =0
    maxValue = 0
    minValue = 1024
    start_time = millis()
    while millis()-start_time < 1000:
       readValue = adc.read_adc(0,gain=GAIN)
      # print(readValue)
       if(readValue > maxValue):
           maxValue = readValue
       elif(readValue < minValue):
           minValue = readValue
    result = ((maxValue - minValue)*5.0)/1024.0
    return result


class motion_thread(threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		while True:
			time.sleep(2)
			global motion_value
			motion_value = int(motion.getValue())
			print motion_value
			if motion_value == 1:
				print 'detect'
				s2.send('GH-Bulb,'+'md')
			elif motion_value == 0:
				print 'not detect'
				s2.send('GH-Bulb,'+'mnd')

				
class switch_thread(threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		global onoff_flag
		while True:
			time.sleep(3)
			client_sock, addr = sock.accept()
			data = client_sock.recv(1024)
			print data.decode()
			if data == '1':
				onoff_flag = 1
				omega_gpio.setoutput(relay_gpioNum, 0)
				print 'on!!!!!!!!!!!!!!!!!!!'
			elif data == '0':
				onoff_flag = 0
				omega_gpio.setoutput(relay_gpioNum,1)
				print 'off!!!!!!!!!!!!!!!!!!'
		sock.close()
				
class temhumi_thread(threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		while True:
			proc = subprocess.Popen(['./checkHumidity ' + str(temphumi_gpioNum) + ' ' + sensorModel], stdout=subprocess.PIPE, shell=True)
			(out, err) = proc.communicate()

			sensor_data = out.split('\n')
			print sensor_data
			global humidity
			humidity = sensor_data[0]
			global temperature 
			temperature = sensor_data[1]

			print "Humidity:"
			print str(humidity) + "%"

			print "Temperature:"
			print str(temperature) + "C"
			if float(temperature) >28.0:
				print 'hot hot QQQQQQQQQQQQQQQQQQQQ'
				omega_gpio.setoutput(relay_gpioNum, 0)
class send_thread(threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		while True:
			time.sleep(60)
			s.send('jai'+','+str(motion_value)+','+str(temperature)+','+str(humidity)+','+str(elect_value))
		s.close()	
		
class power_thread(threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		while True:
			global Voltage
			Voltage = getVPP()
			global VRMS
			VRMS = (Voltage/2.1)*0.707
			global AmpsRMS
			AmpsRMS = (VRMS*1000)/mVperAmp
			global elect_value
			elect_value = AmpsRMS/2*220
			print(AmpsRMS/2*220)
		
motion_th = motion_thread(1,'th1',1)
swich_th = switch_thread(2,'th2',2)
temhum_th = temhumi_thread(3,'th3',3)
send_th = send_thread(4,'th4',4)
power_th = power_thread(5,'th5',5)

motion_th.start()
swich_th.start()
temhum_th.start()
send_th.start()
power_th.start()


