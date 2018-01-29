var Task = require("../model/task");
var User = require("../model/user");
var Alarm = require("../model/alarm");
var Care = require("../model/care");
var ServerFile = require("../server");

result = ''; 



var port_1 = 5200;



exports.checkElec = function(req,res){
	Task.aggregate({"$project": {"deviceName": 1,"sum": "$ElecLog.Elec"} },function(tasks,err){
	// Task.aggregate({ "$unwind": "$ElecLog" },{"$project": {"deviceName": 1,"sum": {"$sum":"$ElecLog.Elec" } } },function(tasks,err){
		if(err){
			console.log("IFIF"+err);
			//console.log("if"+err);
			var data = err;
			for(var i=0;i<data.length;i++){
				console.log(data[i].deviceName);
				// console.log(data[i].numberOf);
				// var numberOf_tmp = data[i].numberOf.substring(1, data[i].numberOf.length);
				// for(int j=0;j<data[i].)
			}
				// console.log(data[i].Type2);
				// console.log(data[i].Type2);
			// 	//console.log(data[i].StartAlarmHour);
			// 	if((data[i].StartAlarmHour==current_time.getHours())&&(data[i].StartAlarmMinute==current_time.getMinutes())){
			// 		console.log("Same1");
			// 		//DB Change
			// 		Task.findOne({deviceName:data[i].deviceName},function(tasks,err){
			// 			if(err){
			// 				//res.send(err);
			// 				//DB
			// 				if(err.currentStatus=='0'){
			// 					console.log(err.currentStatus);
			// 					Task.updateOne(
			// 				        { "deviceName" : err.deviceName } ,
			// 				        { "$set" : { "currentStatus": 1 } },
			// 				        function(err, result){
			// 				        	if(err){
			// 				        		console.log("failed to save log");
			// 				        	}
			// 				        	else{
			// 				        		console.log("result"+result);
			// 				        		//res.send({message:"log was saved.",data:result});
			// 				        	}
			// 				        }
			// 				    )
			// 				    Task.findOne({deviceName:err.deviceName},function(tasks,err){
			// 						if(err){
			// 								//res.send(err);
			// 							console.log(err.deviceIP);
			// 							// res.json(err.deviceIP);
			// 							result = err.deviceIP;
			// 						}
			// 						else{
			// 							//console.log(tasks);
			// 							res.send(tasks);
			// 						}
			// 					});
			// 					//Connect with Omega
			// 					var net = require('net');
			// 					var client = net.connect({port:port_1,host:result},function(){
			// 						console.log('Client connected');
			// 						console.log("result"+result);
			// 						console.log("port_1"+port_1);
			// 						client.write("1");
			// 					});
			// 					client.on('data',function(data){
			// 						console.log(data.toString());
			// 						client.end();
			// 					});
			// 					client.on('end',function(){
			// 						console.log('Client Disconnected');
			// 					});
			// 				}
			// 			}
			// 			else{
							
			// 				//console.log(tasks);
			// 				console.log(err);
			// 			}
			// 		});
			// 		//Connect with Omega
					
			// 	}
			// 	if((data[i].EndAlarmHour==current_time.getHours())&&(data[i].EndAlarmMinute==current_time.getMinutes())){
			// 		Task.findOne({deviceName:data[i].deviceName},function(tasks,err){
			// 			if(err){
			// 				//res.send(err);
			// 				console.log(err.currentStatus);
			// 				if(err.currentStatus=='1'){
			// 					console.log("INININ");
			// 					Task.updateOne(
			// 				        { "deviceName" : err.deviceName } ,
			// 				        { "$set" : { "currentStatus": 0 } },
			// 				        function(err, result){
			// 				        	if(err){
			// 				        		console.log("failed to save log");
			// 				        	}
			// 				        	else{
			// 				        		console.log(result);
			// 				        		//res.send({message:"log was saved.",data:result});
			// 				        	}
			// 				        }
			// 				    )
			// 				    Task.findOne({deviceName:err.deviceName},function(tasks,err){
			// 						if(err){
			// 								//res.send(err);
			// 							console.log(err.deviceIP);
			// 							// res.json(err.deviceIP);
			// 							result = err.deviceIP;
			// 						}
			// 						else{
			// 							//console.log(tasks);
			// 							res.send(tasks);
			// 						}
			// 					});
								
			// 					//Omega
			// 					var net = require('net');
			// 					var client = net.connect({port:port_1,host:result},function(){
			// 						console.log('Client connected');
			// 						client.write("0");
			// 					});
			// 					client.on('data',function(data){
			// 						console.log(data.toString());
			// 						client.end();
			// 					});
			// 					client.on('end',function(){
			// 						console.log('Client Disconnected');
			// 					});
			// 				}

			// 			}
			// 			else{
							
			// 				//console.log(tasks);
			// 				console.log(err);
			// 			}
			// 		});
					
			// 	}
			// }
		}

		else{
			console.log("ELSE"+tasks);
		}


	})
};


//Care
exports.addCare = function(req,res){

	var care = new Care();

	care.deviceName = req.params.deviceName;
	care.Type1 = req.body.Type1;
	care.Type2 = req.body.Type2;
	care.Value = req.body.Value;
	
	care.save(function(err){
		if(err){
			console.log(req.body);
			console.log(care);
			res.send(err);
		}
		else{
			res.send({message:"task was saved.",data:care});
			console.log(care);
		}
	});
};

exports.getCare = function(req,res){
	Care.find(function(tasks,err){
		if(err){
			res.send(err);
		}
		else{
			res.json(tasks);
		}
	})
};




//Elec
exports.getTotalElec = function(req,res){
	Task.aggregate({"$project": {"deviceName": 1,"sum": "$ElecLog.Elec"} },function(tasks,err){
	// Task.aggregate({ "$unwind": "$ElecLog" },{"$project": {"deviceName": 1,"sum": {"$sum":"$ElecLog.Elec" } } },function(tasks,err){
		if(err){
			// for(var i=0;i<err.numberOf.size();i++){

			// }
			res.send(err);
			// console.log(err.numberOf.length);
		}
		else{
			res.json(tasks);
			console.log(tasks);
		}
	})
};


exports.getSeperateElec = function(req,res){

	Task.find({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			// res.send(err);
			console.log("IF");
		}
		else{
			// res.json(tasks);
			console.log("ELSE");
		}
	})

};




//Alarm


exports.DeleteAlarm = function(req,res){


	// 	alarm.deviceName = req.params.deviceName;
	// alarm.StartAlarmHour = req.body.StartAlarmHour;
	// alarm.StartAlarmMinute = req.body.StartAlarmMinute;
	// alarm.EndAlarmHour = req.body.EndAlarmHour;
	// alarm.EndAlarmMinute = req.body.EndAlarmMinute;

	// console.log("get IN2");
	// Alarm.remove({deviceName:req.params.deviceName,StartAlarmHour:req.body.StartAlarmHour,StartAlarmMinute:req.body.StartAlarmMinute,EndAlarmHour:req.body.EndAlarmHour,EndAlarmMinute:req.body.EndAlarmMinute},function(tasks,err){
	// 	if(err){
	// 		//res.send(err);
	// 		// console.log("IF");
	// 		res.json("SUCCESS");
	// 		console.log("IF");
	// 	}
	// 	else{
	// 		//console.log(tasks);c
	// 		// res.send(tasks);
	// 		console.log("ELSE");
	// 		res.send("SUCCESS");

	// 	}
	// });
	console.log("1");
	console.log(req.params.deviceName);
	console.log("2");
	console.log(req.body.StartAlarmHour);
	console.log("3");
	console.log(req.body.StartAlarmMinute);
	console.log("4");
	console.log(req.body.EndAlarmHour);
	console.log("5");
	console.log(req.body.EndAlarmMinute);


	Alarm.remove({deviceName:req.params.deviceName,StartAlarmHour:req.body.StartAlarmHour,StartAlarmMinute:req.body.StartAlarmMinute,EndAlarmHour:req.body.EndAlarmHour,EndAlarmMinute:req.body.EndAlarmMinute},function(tasks,err){
		if(err){
			//res.send(err);
			// console.log("IF");
			// res.json(tasks);
			res.send(tasks);
			console.log("IF");
		}
		else{
			//console.log(tasks);c
			res.send(tasks);
			console.log("ELSE");

		}
	});
};



exports.checkAlarm = function(req,res){
	var current_time = new Date();

 	console.log(current_time.getHours());
	console.log(current_time.getMinutes());


	Alarm.find(function(tasks,err){
		if(err){
			//console.log("if"+err);
			var data = err;
			for(var i=0;i<data.length;i++){
				//console.log(data[i].deviceName);
				//console.log(data[i].StartAlarmHour);
				if((data[i].StartAlarmHour==current_time.getHours())&&(data[i].StartAlarmMinute==current_time.getMinutes())){
					console.log("Same1");
					//DB Change
					Task.findOne({deviceName:data[i].deviceName},function(tasks,err){
						if(err){
							//res.send(err);
							//DB
							if(err.currentStatus=='0'){
								console.log(err.currentStatus);
								Task.updateOne(
							        { "deviceName" : err.deviceName } ,
							        { "$set" : { "currentStatus": 1 } },
							        function(err, result){
							        	if(err){
							        		console.log("failed to save log");
							        	}
							        	else{
							        		console.log("result"+result);
							        		//res.send({message:"log was saved.",data:result});
							        	}
							        }
							    )
							    Task.findOne({deviceName:err.deviceName},function(tasks,err){
									if(err){
											//res.send(err);
										console.log(err.deviceIP);
										// res.json(err.deviceIP);
										result = err.deviceIP;
									}
									else{
										//console.log(tasks);
										res.send(tasks);
									}
								});
								//Connect with Omega
								var net = require('net');
								var client = net.connect({port:port_1,host:result},function(){
									console.log('Client connected');
									console.log("result"+result);
									console.log("port_1"+port_1);
									client.write("1");
								});
								client.on('data',function(data){
									console.log(data.toString());
									client.end();
								});
								client.on('end',function(){
									console.log('Client Disconnected');
								});
							}
						}
						else{
							
							//console.log(tasks);
							console.log(err);
						}
					});
					//Connect with Omega
					
				}
				if((data[i].EndAlarmHour==current_time.getHours())&&(data[i].EndAlarmMinute==current_time.getMinutes())){
					Task.findOne({deviceName:data[i].deviceName},function(tasks,err){
						if(err){
							//res.send(err);
							console.log(err.currentStatus);
							if(err.currentStatus=='1'){
								console.log("INININ");
								Task.updateOne(
							        { "deviceName" : err.deviceName } ,
							        { "$set" : { "currentStatus": 0 } },
							        function(err, result){
							        	if(err){
							        		console.log("failed to save log");
							        	}
							        	else{
							        		console.log(result);
							        		//res.send({message:"log was saved.",data:result});
							        	}
							        }
							    )
							    Task.findOne({deviceName:err.deviceName},function(tasks,err){
									if(err){
											//res.send(err);
										console.log(err.deviceIP);
										// res.json(err.deviceIP);
										result = err.deviceIP;
									}
									else{
										//console.log(tasks);
										res.send(tasks);
									}
								});
								
								//Omega
								var net = require('net');
								var client = net.connect({port:port_1,host:result},function(){
									console.log('Client connected');
									client.write("0");
								});
								client.on('data',function(data){
									console.log(data.toString());
									client.end();
								});
								client.on('end',function(){
									console.log('Client Disconnected');
								});
							}

						}
						else{
							
							//console.log(tasks);
							console.log(err);
						}
					});
					
				}
			}
		}
		else{
			console.log("else"+tasks);
		}
	})
};



exports.addAlarm = function(req,res){

	var alarm = new Alarm();

	alarm.deviceName = req.params.deviceName;
	alarm.StartAlarmHour = req.body.StartAlarmHour;
	alarm.StartAlarmMinute = req.body.StartAlarmMinute;
	alarm.EndAlarmHour = req.body.EndAlarmHour;
	alarm.EndAlarmMinute = req.body.EndAlarmMinute;
	
	alarm.save(function(err){
		if(err){
			console.log(req.body);
			console.log(alarm);
			res.send(err);
		}
		else{
			res.send({message:"task was saved.",data:alarm});
		}
	});
};


exports.getAlarm = function(req,res){
	Alarm.find(function(tasks,err){
		if(err){
			res.send(err);
		}
		else{
			res.json(tasks);
		}
	})
};





//Task



//
exports.updateMotion = function(req,res){
	console.log("MotionChecked"+req.body.MotionChecked);
	
	// var net = require('net');
	// var client = net.connect({port:3300,host:result},function(){
	// 	console.log('Client connected');
	// 	client.write(req.body.currentStatus);
	// });
	// client.on('data',function(data){
	// 	console.log(data.toString());
	// 	client.end();
	// });
	// client.on('end',function(){
	// 	console.log('Client Disconnected');
	// });

	var collection = ServerFile.db.collection('tasks');
	
	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$set" : { "MotionChecked": req.body.MotionChecked } },
        function(err, result){
        	if(err){
        		console.log("failed to update CurrentStatus");
        	}
        	else{
        		console.log(result);
        		res.send({message:"currentStatus was saved.",data:result});
        	}
        }
    )

    //Connect with omega
}

Date.prototype.addHours = function(h) {    
   this.setTime(this.getTime() + (h*60*60*1000)); 
   return this;   
}

exports.getDeviceInfo = function(req,res){
	Task.find({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			res.send(err);
		}
		else{
			res.json(tasks);
		}
	})
};




exports.exportDB = function(req,res){
	let exec = require('child_process').exec;
	exec("python ~/Desktop/onoff2/csv/export2.py", (error, stdout, stderr) => {
		console.log("Export DB successfully")
		//Deep Learning
		// exec("python3 ~/Desktop/onoff2/deepL/example.py", (error, stdout, stderr) => {
		// 	console.log("Running Python successfully")
		// 	console.log(stderr)
		// 	console.log(error)
		// })
	})	
};

exports.getDevices = function(req,res){
	Task.find(function(tasks,err){
		if(err){
			res.send(err);
		}
		else{
			res.json(tasks);
		}
	})
};



exports.addDevice = function(req,res){
	var task = new Task();

	task.deviceName = req.body.deviceName;
	task.deviceIP = req.body.deviceIP;
	
	task.save(function(err){
		if(err){
			console.log(req.body);
			console.log(task);
			res.send(err);
		}
		else{
			res.send({message:"task was saved.",data:task});
		}
	});
}

exports.getIpFromDeviceName = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.deviceIP);
			res.json(err.deviceIP);
			result = err.deviceIP;
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};

exports.getMotionFromDeviceName = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.MotionLog);
			res.send(err.MotionLog);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};

exports.addMotionForDevice = function(req,res){
	var collection = ServerFile.db.collection('tasks');
	
 	var current_time = new Date();

 	current_time.setDate(current_time.getDate());
	current_time.addHours(9)

 	var log = {Motion:req.body.Motion,changedTime:current_time};
 	console.log(log);
 	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$push" : { "MotionLog": log } },
        function(err, result){
        	if(err){
        		console.log("failed to save log");
        	}
        	else{
        		console.log(result);
        		res.send({message:"log was saved.",data:result});
        	}
        }
    )
}

exports.getHumidityFromDeviceName = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.HumidityLog);
			res.send(err.HumidityLog);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};
exports.addHumidityForDevice = function(req,res){
	var collection = ServerFile.db.collection('tasks');
	
 	var current_time = new Date();

 	current_time.setDate(current_time.getDate());
	current_time.addHours(9)

 	var log = {Humidity:req.body.Humidity,changedTime:current_time};
 	console.log(log);
 	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$push" : { "HumidityLog": log } },
        function(err, result){
        	if(err){
        		console.log("failed to save log");
        	}
        	else{
        		console.log(result);
        		res.send({message:"log was saved.",data:result});
        	}
        }
    )
}

exports.getElecFromDeviceName = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.ElecLog);
			res.json(err.ElecLog);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};


exports.addElecForDevice = function(req,res){
	var collection = ServerFile.db.collection('tasks');
	
 	var current_time = new Date();

 	current_time.setDate(current_time.getDate());
	current_time.addHours(9)

 	var log = {Elec:req.body.Elec,changedTime:current_time};
 	console.log(log);
 	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$push" : { "ElecLog": log } },
        function(err, result){
        	if(err){
        		console.log("failed to save log");
        	}
        	else{
        		console.log(result);
        		res.send({message:"log was saved.",data:result});
        	}
        }
    )
}

exports.getTempFromDeviceName = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.TempLog);
			res.json(err.TempLog);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};
exports.addTempForDevice = function(req,res){
	var collection = ServerFile.db.collection('tasks');
	
 	var current_time = new Date();

 	current_time.setDate(current_time.getDate());
	current_time.addHours(9)

 	var log = {Temp:req.body.Temp,changedTime:current_time};
 	console.log(log);
 	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$push" : { "TempLog": log } },
        function(err, result){
        	if(err){
        		console.log("failed to save log");
        	}
        	else{
        		console.log(result);
        		res.send({message:"log was saved.",data:result});
        	}
        }
    )
}


exports.addLogForDevice = function(req,res){
	var collection = ServerFile.db.collection('tasks');
	
 	var current_time = new Date();

 	current_time.setDate(current_time.getDate()-10);
	current_time.addHours(9)

 	var log = {status:req.body.status,changedTime:current_time};
 	console.log("First Status input1:"+req.body.status);

 	console.log("First Status input2:"+req);
 	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$push" : { "statusLog": log } },
        function(err, result){
        	if(err){
        		console.log("failed to save log");
        	}
        	else{
        		console.log(result);
        		res.send({message:"log was saved.",data:result});
        	}
        }
    )
}

exports.updateCurrentStatusForDevice = function(req,res){
	// Omega
	console.log("Second CurrentStatus"+req.body.currentStatus);
	var net = require('net');
	var client = net.connect({port:port_1,host:result},function(){
		console.log('Client connected');
		client.write(req.body.currentStatus);
	});
	client.on('data',function(data){
		console.log(data.toString());
		client.end();
	});
	client.on('end',function(){
		console.log('Client Disconnected');
	});






	var collection = ServerFile.db.collection('tasks');
	
	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$set" : { "currentStatus": req.body.currentStatus } },
        function(err, result){
        	if(err){
        		console.log("failed to update CurrentStatus");
        	}
        	else{
        		console.log(result);
        		res.send({message:"currentStatus was saved.",data:result});
        	}
        }
    )

    //Connect with omega
}

exports.getElecFromDeviceName = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.ElecLog);
			res.json(err.ElecLog);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};


//get Sensor Data
exports.getHumidity = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.Humidity);
			res.json(err.Humidity);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};

exports.getTemp = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.Temp);
			res.json(err.Temp);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};

exports.getMotion = function(req,res){
	Task.find({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.Motion);
			res.json(err.Motion);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};
exports.getElec = function(req,res){
	Task.findOne({deviceName:req.params.deviceName},function(tasks,err){
		if(err){
			//res.send(err);
			console.log(err.Elec);
			res.json(err.Elec);
		}
		else{
			//console.log(tasks);
			res.send(tasks);
		}
	});
};








//User
exports.addUser = function(req,res){
	var user = new User();

	user.userID = req.body.userID;
	user.userPW = req.body.userPW;
	
	user.save(function(err){
		if(err){
			console.log(req.body);
			console.log(user);
			res.send(err);
		}
		else{
			res.send({message:"task was saved.",data:user});
		}
	});
};

exports.checkUser = function(req,res){
	console.log("get IN");
	User.findOne({userID:req.params.userID},function(tasks,err){
		if(err){
			//res.send(err);
			console.log("IF");
			console.log(err.userPW);
			res.json(err.userPW);
		}
		else{
			//console.log(tasks);c
			res.send(tasks);

		}
	});
};
exports.getUsers = function(req,res){
	User.find(function(tasks,err){
		if(err){
			res.send(err);
		}
		else{
			res.json(tasks);
		}
	})
};




exports.updateMotion = function(req,res){
	// Omega
	// console.log("Second Motion"+req.body.MotionChecked);
	// var net = require('net');
	// var client = net.connect({port:4444,host:result},function(){
	// 	console.log('Client connected');
	// 	client.write(req.body.MotionChecked);
	// });
	// client.on('data',function(data){
	// 	console.log(data.toString());
	// 	client.end();
	// });
	// client.on('end',function(){
	// 	console.log('Client Disconnected');
	// });





	var collection = ServerFile.db.collection('tasks');
	
	collection.updateOne(
        { "deviceName" : req.params.deviceName } ,
        { "$set" : { "MotionChecked": req.body.MotionChecked } },
        function(err, result){
        	if(err){
        		console.log("failed to update CurrentStatus");
        	}
        	else{
        		console.log(result);
        		res.send({message:"Motionchecked was saved.",data:result});
        	}
        }
    )

    //Connect with omega
}



