var mongoose = require('mongoose')
  , Schema = mongoose.Schema
  , model = mongoose.model

var userSchema = new Schema({
	createdAt: {type: Date, default: Date.now},
	firstName : {type: String, required: true},
	lastName : {type: String, required: true},
	username: {type: String, required: true},
	password : {type: String, required: true},
	school: {type: mongoose.Schema.Types.ObjectId, ref: 'School', required: true},
	isAdministrator: {type: Boolean, default: false},
	isTeacher: {type: Boolean, default: false},
	isChef: {type: Boolean, default: false}
});

module.exports = mongoose.model('User', userSchema);
