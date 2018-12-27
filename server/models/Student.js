var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var studentSchema = new Schema({
    firstName : {type: String, required: true},
    lastName : {type: String, required: true},
    teacher: {type: mongoose.Schema.Types.ObjectId, ref: 'Teacher', required: true},
    class: {type: mongoose.Schema.Types.ObjectId, ref: 'Class', required: true},   
    archived: {type: Boolean, default: false},
    birthMonth : {type: Number, required: true},
    birthDay : {type: Number, required: true},
    birthYear: {type: Number, required: true},
    gender: {type: String, required: true},
    parentName: {type: String, required: true},
    telephoneNumber: {type: String, required: true},
    nationalId: {type: String, required: true},
    studentId: {type: String, required: true},
    averageGrade: {type: Number},
    shoeSize: {type: Number, required: true}
});

module.exports = mongoose.model('Student', studentSchema);
