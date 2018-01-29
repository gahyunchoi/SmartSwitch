var mongoose = require("mongoose");

var statusLogSchema = mongoose.Schema({
	status:{
		type:Number,
		default:0,
		required:false
	},
	changedTime:{
		type:Date,
		default:"",
		required:false
	}
});

var HumidityLogSchema = mongoose.Schema({
	Humidity:{
		type:Number,
		default:0,
		required:true
	},
	changedTime:{
		type:Date,
		default:"",
		required:false
	}
});

var TempLogSchema = mongoose.Schema({
	Temp:{
		type:Number,
		default:0,
		required:true
	},
	changedTime:{
		type:Date,
		default:"",
		required:false
	}
});

var MotionLogSchema = mongoose.Schema({
	Motion:{
		type:Number,
		default:0,
		required:true
	},
	changedTime:{
		type:Date,
		default:"",
		required:false
	}
});

var ElecLogSchema = mongoose.Schema({
	Elec:{
		type:Number,
		default:0,
		required:true
	},
	changedTime:{
		type:Date,
		default:"",
		required:false
	}
});

var Task = mongoose.Schema({
	deviceName:{
		type:String,
		default:"",
		required:true
	},
	deviceIP:{
		type:String,
		default:"",
		required:true
	},
	statusLog:[statusLogSchema],
	currentStatus:{
		type:Number,
		default:0,
		required:true
	},
	HumidityLog:[HumidityLogSchema],
	TempLog:[TempLogSchema],
	MotionLog:[MotionLogSchema],
	ElecLog:[ElecLogSchema],
	Humidity:{
		type:Number,
		default:0,
		required:true
	},
	Temp:{
		type:Number,
		default:0,
		required:true
	},
	Motion:{
		type:Number,
		default:0,
		required:true
	},
	Elec:{
		type:Number,
		default:0,
		required:true
	},
	MotionChecked:{
		type:Number,
		default:0,
		required:true
	}
});


module.exports = mongoose.model("Task",Task);