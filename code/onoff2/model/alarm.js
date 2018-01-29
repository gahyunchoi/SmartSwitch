var mongoose = require("mongoose");

var Alarm = mongoose.Schema({
	deviceName:{
		type:String,
		default:"",
		required:true
	},
	StartAlarmHour:{
		type:String,
		default:"",
		required:true
	},
	StartAlarmMinute:{
		type:String,
		default:"",
		required:true
	},
	EndAlarmHour:{
		type:String,
		default:"",
		required:true
	},
	EndAlarmMinute:{
		type:String,
		default:"",
		required:true
	}
});

module.exports = mongoose.model("Alarm",Alarm);