<<<<<<< HEAD
var mongoose = require('mongoose')
  , Schema = mongoose.Schema
  , model = mongoose.model

var schoolSchema = new Schema({
	name     : {type: String, required: true},
	teachers : [{type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true}],
	cooks    : [{type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true}],
	students : [{type: mongoose.Schema.Types.ObjectId, ref: 'Student', required: true}],
	totalStudentsPresent : {type: Int, default: 0, required: true}
=======
var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var schoolSchema = new Schema({
	name: {type: String, required: true},
	teachers: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
	cooks: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
	students: [{type: mongoose.Schema.Types.ObjectId, ref: 'Student'}],
	classes: [{type: mongoose.Schema.Types.ObjectId, ref: 'Class'}]
>>>>>>> 9772b7f578fc9943c8fd3820a08bcbdf656a6244
});

module.exports = mongoose.model('School', schoolSchema);
