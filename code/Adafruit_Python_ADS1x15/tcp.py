import socket
import omega_gpio

relay_gpioNum = 8
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind(('',3300))
sock.listen(1)
while True:
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
