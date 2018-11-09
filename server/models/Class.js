var mongoose = require('mongoose')
  , Schema = mongoose.Schema
  , model = mongoose.model

var classSchema  = new Schema({
	createdAt  : {type: Date, default: Date.now},
  teacher    : {type: mongoose.Schema.Types.ObjectId, ref : 'Teacher ', required : true},
	students   : [{type: mongoose.Schema.Types.ObjectId, ref : 'Student ', required : true}],
  studentsPresent : {type: Int, default: 0},   //total of students present in class
   // only homerooms to be counted in attendance to avoid duplicates in attendance
  isHomeroom : {type: Boolean, default: false, required : true},
	school: {type: mongoose.Schema.Types.ObjectId, ref: 'School', required: true},
});

module.exports = mongoose.model('Class', classSchema);
