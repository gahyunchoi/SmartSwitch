var mongoose = require("mongoose");

var User = mongoose.Schema({
	userID:{
		type:String,
		default:"",
		required:true
	},
	userPW:{
		type:String,
		default:"",
		required:true
	}
});

module.exports = mongoose.model("User",User);