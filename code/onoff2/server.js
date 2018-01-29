var express = require("express");
var bodyParser = require("body-parser");
var mongoose = require("mongoose");
var taskController = require("./controller/task");
var MongoClient = require("mongodb").MongoClient;
var schedule = require('node-schedule');
var Alarm = require("./model/alarm");
var Task = require("./model/task");
var Care = require("./model/care");

var port_1 = 5200;
var port_2 = 9999;
var port_3 = 9900;
//0 * * *  At midnight
//Export DB to csv
var shedule_job = schedule.scheduleJob('41 * * * * *',function(){
	taskController.exportDB();
	console.log("Start export DB to CSV");
})

//Alarm check
var shedule_job = schedule.scheduleJob('41 * * * * *',function(){
	
	taskController.checkAlarm();
	// taskController.checkElec();

})


var db;

MongoClient.connect("mongodb://localhost:27017/devices",function(err,database){
	if(err){
		console.log("unable to connect server",err);
	}
	else{
		console.log("success to connect server");
		exports.db = database;
		db = database;
	}
});


mongoose.connect("mongodb://localhost:27017/devices",function(err,database){
	if(err){
		console.log("unable to connect server",err);
	}
	else{
		console.log("success to connect server");
	}
});



var flag = 0;


var net = require('net'),fs = require("fs");
var is_motion ='';
var result ='';
var current_status ='';
var Motionserver = net.createServer(function(client) { 
	console.log('client connected');
	//client.write('hello\r\n'); //클라이언트에게 최초 접속시 데이터전달
	//클라이언트에게 받은 데이터
	client.on('data',function(data){ 
		//Receive Data
		console.log('client In Data : '+data);
		var sendData = 'hello '+data;
		console.log('client Send Data : '+sendData);
		// client.write(sendData); //클라이언트에게 추가로 데이터를 보냄
		
		//Split into each data
		sensorDatas = String(data);
		var temp = new Array();
		temp = sensorDatas.split(",");

		//Store data
		flag ++; 
		console.log("0:"+temp[1]);//DeviceName -> Motion -> Temp -> Humid -> Elec 
		console.log("0:"+temp[0]);

		if(temp[1]=='md'){
			console.log("DETECTED");
			//


			
			Task.findOne({deviceName:temp[0]},function(tasks,err){
				if(err){
					is_motion ='';
					result ='';
					//res.send(err);
					console.log("1@@@@@IP"+err.deviceIP);
					console.log("1@@@@@@MOTION"+err.MotionChecked);
					result = err.deviceIP;
					is_motion = err.MotionChecked;
					current_status = err.currentStatus;
				}
				else{
					//res.send(err);
					console.log("2@@@@@IP"+err.deviceIP);
					console.log("2@@@@@@MOTION"+err.MotionChecked);
					result = err.deviceIP;
					is_motion = err.MotionChecked;
					//console.log(tasks);
					// res.send(tasks);
				}
			});



			// console.log("Second"+req.body.currentStatus);
				// console.log("Second"+req.body.currentStatus);
			console.log("IP"+result);
			console.log("IS_MOTION"+is_motion);
			console.log("current_status"+current_status);

			if((is_motion=='3')&(current_status=='0')){

				console.log("ON!!!!!!!!");

				var net = require('net');
				var client = net.connect({port:port_1,host:result},function(){
					console.log('Client connected');
					client.write("1");
				});
				client.on('data',function(data){
					console.log(data.toString());
					client.end();
				});
				client.on('end',function(){
					console.log('Client Disconnected');
				});





				var collection = db.collection('tasks');
				
				collection.updateOne(
			        { "deviceName" : temp[0] } ,
			        { "$set" : { "currentStatus": "1" } },
			        function(err, result){
			        	if(err){
			        		console.log("failed to update CurrentStatus");
			        	}
			        	else{
			        		console.log(result);
			        		// res.send({message:"currentStatus was saved.",data:result});
			        	}
			        }
			    )
			}

			//
		}	
		else{
			console.log("NOT DETECTED");
		}
	});
	client.on('end', function() {
		console.log('client disconnected');
	});
});
Motionserver.listen(port_2, function() { //'listening' listener
    console.log('PID ['+process.pid+'] TCP Server listening');
});



var Sensorserver = net.createServer(function(client) { 
	console.log('client connected');
	//client.write('hello\r\n'); //클라이언트에게 최초 접속시 데이터전달
	//클라이언트에게 받은 데이터
	client.on('data',function(data){ 
		//Receive Data
		console.log('client In Data : '+data);
		var sendData = 'hello '+data;
		console.log('client Send Data : '+sendData);
		client.write(sendData); //클라이언트에게 추가로 데이터를 보냄
		
		//Split into each data
		sensorDatas = String(data);
		var temp = new Array();
		temp = sensorDatas.split(",");

		//Store data
			flag ++; 
			console.log("0:"+temp[0]);//DeviceName -> Motion -> Temp -> Humid -> Elec 
			console.log("1:"+temp[1]);
			console.log("2:"+temp[2]);
			console.log("3:"+temp[3]);
			console.log("4:"+temp[4]);

			//add Motion
			var collection = db.collection('tasks');
		 	var current_time = new Date();

		 	current_time.setDate(current_time.getDate());
			current_time.addHours(9)

		 	var log = {Motion:temp[1],changedTime:current_time};
		 	collection.updateOne(
		        { "deviceName" : temp[0]} ,
		        { "$push" : { "MotionLog": log } },
		        function(err, result){
		        	if(err){
		        		console.log("failed to save log");
		        	}
		        	else{
		        		console.log(result);
		        	}
		        }
		    )	
		    //add temp
		    var log = {Temp:temp[2],changedTime:current_time};
		 	console.log(log);
		 	collection.updateOne(
		        { "deviceName" : temp[0] } ,
		        { "$push" : { "TempLog": log } },
		        function(err, result){
		        	if(err){
		        		console.log("failed to save log");
		        	}
		        	else{
		        		console.log(result);
		        	}
		        }
		    )

		    //add Humid
		    log = {Humidity:temp[3],changedTime:current_time};
		 	collection.updateOne(
		        { "deviceName" : temp[0] } ,
		        { "$push" : { "HumidityLog": log } },
		        function(err, result){
		        	if(err){
		        		console.log("failed to save log");
		        	}
		        	else{
		        		console.log(result);
		        	}
		        }
		    )
		    //Elec
		    if(parseFloat(temp[4]) > 200){
		    	log = {Elec:temp[4],changedTime:current_time};
			 	console.log(log);
			 	collection.updateOne(
			        { "deviceName" : temp[0] } ,
			        { "$push" : { "ElecLog": log } },
			        function(err, result){
			        	if(err){
			        		console.log("failed to save log");
			        	}
			        	else{
			        		console.log(result);
			        	}
			        }
			    )	
		    }
		    
		    //update current status
			collection.updateOne(
		        { "deviceName" : temp[0] } ,
		        { "$set" : { "Humidity": temp[3] } },
		        function(err, result){
		        	if(err){
		        		console.log("failed to update CurrentStatus");
		        	}
		        	else{
		        		console.log(result);
		        	}
		        }
    		)
    		//update current status
			collection.updateOne(
		        { "deviceName" : temp[0] } ,
		        { "$set" : { "Elec": temp[4] } },
		        function(err, result){
		        	if(err){
		        		console.log("failed to update CurrentStatus");
		        	}
		        	else{
		        		console.log(result);
		        	}
		        }
    		)
    		//update current status
			collection.updateOne(
		        { "deviceName" : temp[0] } ,
		        { "$set" : { "Motion": temp[1] } },
		        function(err, result){
		        	if(err){
		        		console.log("failed to update CurrentStatus");
		        	}
		        	else{
		        		console.log(result);
		        	}
		        }
    		)
    		//update current status
			collection.updateOne(
		        { "deviceName" : temp[0] } ,
		        { "$set" : { "Temp": temp[2] } },
		        function(err, result){
		        	if(err){
		        		console.log("failed to update CurrentStatus");
		        	}
		        	else{
		        		console.log(result);
		        	}
		        }
    		)

	});
	client.on('end', function() {
		console.log('client disconnected');
	});
});

Sensorserver.listen(port_3, function() { //'listening' listener
    console.log('PID ['+process.pid+'] TCP Server listening');
});


var app = express();

app.use(bodyParser.urlencoded({
	extended:true
}))

var router = express.Router();

app.use("/api",router);

router.route("/exportDB").get(taskController.exportDB);

router.route("/getDeviceInfo/:deviceName").get(taskController.getDeviceInfo);

router.route("/addDevice").post(taskController.addDevice);

router.route("/addLogForDevice/:deviceName").post(taskController.addLogForDevice);

router.route("/getDevices").get(taskController.getDevices);

router.route("/getIpFromDeviceName/:deviceName").get(taskController.getIpFromDeviceName);

router.route("/updateCurrentStatus/:deviceName").post(taskController.updateCurrentStatusForDevice);


router.route("/getIpFromDeviceName/:deviceName").get(taskController.getIpFromDeviceName);

router.route("/getMotionFromDeviceName/:deviceName").get(taskController.getMotionFromDeviceName);
router.route("/addMotionForDevice/:deviceName").post(taskController.addMotionForDevice);


router.route("/getHumidityFromDeviceName/:deviceName").get(taskController.getHumidityFromDeviceName);
router.route("/addHumidityForDevice/:deviceName").post(taskController.addHumidityForDevice);


router.route("/getElecFromDeviceName/:deviceName").get(taskController.getElecFromDeviceName);
router.route("/addElecForDevice/:deviceName").post(taskController.addElecForDevice);

router.route("/getTempFromDeviceName/:deviceName").get(taskController.getTempFromDeviceName);
router.route("/addTempForDevice/:deviceName").post(taskController.addTempForDevice);

//Get Value
router.route("/getElec/:deviceName").get(taskController.getElec);
router.route("/getMotion/:deviceName").get(taskController.getMotion);

router.route("/getTemp/:deviceName").get(taskController.getTemp);
router.route("/getHumidity/:deviceName").get(taskController.getHumidity);

router.route("/updateMotion/:deviceName").post(taskController.updateMotion);






//user
router.route("/addUser").post(taskController.addUser);
router.route("/checkUser/:userID").get(taskController.checkUser);

router.route("/getUsers").get(taskController.getUsers);



//alarm
router.route("/addAlarm/:deviceName").post(taskController.addAlarm);
router.route("/getAlarm").get(taskController.getAlarm);


router.route("/DeleteAlarm/:deviceName").post(taskController.DeleteAlarm);

router.route("/updateMotion/:deviceName").post(taskController.updateMotion);



router.route("/getTotalElec").get(taskController.getTotalElec);
router.route("/getSeperateElec/:deviceName").get(taskController.getSeperateElec);




router.route("/addCare/:deviceName").post(taskController.addCare);
router.route("/getCare").get(taskController.getCare);

app.listen(3000);