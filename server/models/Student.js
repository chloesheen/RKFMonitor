var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var studentSchema = new Schema({
    firstName : {type: String, required: true},
    lastName : {type: String, required: true},
    archived: {type: Boolean, default: false},
    birthMonth : {type: String, required: true},
    birthDay : {type: Number, required: true},
    birthYear: {type: Number, required: true},
    gender: {type: String, required: true},
    parentName: {type: String, required: true},
    telephoneNumber: {type: Number, required: true},
    nationalId: {type: String, required: true},
    averageGrade: {type: Number},
    shoeSize: {type: Number, required: true},
    homeRoom: {type: mongoose.Schema.Types.ObjectId, ref: 'Class', required: true},
    datesAttended: [{type: Date}]
});

module.exports = mongoose.model('Student', studentSchema);
