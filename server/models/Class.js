<<<<<<< HEAD
var mongoose = require('mongoose')
  , Schema = mongoose.Schema
  , model = mongoose.model

var classSchema  = new Schema({
	createdAt: {type: Date, default: Date.now},
	teacher: {type: mongoose.Schema.Types.ObjectId, ref : 'Teacher', required : true},
	students: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student', required : true}],
	studentsPresent: {type: Int, default: 0},   //total of students present in class
	// only homerooms to be counted in attendance to avoid duplicates in attendance
	isHomeroom: {type: Boolean, default: false, required : true},
	school: {type: mongoose.Schema.Types.ObjectId, ref: 'School', required: true},
=======
var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var classSchema  = new Schema({
	name: {type: String},
	school: {type: mongoose.Schema.Types.ObjectId, ref: 'School', required: true},	
	teacher: {type: mongoose.Schema.Types.ObjectId, ref : 'Teacher'},
	students: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}]
>>>>>>> 9772b7f578fc9943c8fd3820a08bcbdf656a6244
});

module.exports = mongoose.model('Class', classSchema);
