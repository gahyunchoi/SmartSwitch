var mongoose = require("mongoose");

var Care = mongoose.Schema({
	deviceName:{
		type:String,
		default:"",
		required:true
	},
	//온도 - 1 인지 습도 - 2 
	Type1:{
		type:String,
		default:"",
		required:true
	},
	//상한선 - 1 인지 하한선 - 2 인지
	Type2:{
		type:String,
		default:"",
		required:true
	},
	//온도습도 값
	Value:{
		type:Number,
		default:"",
		required:true
	}
});

module.exports = mongoose.model("Care",Care);