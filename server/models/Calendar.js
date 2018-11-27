var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var calendarSchema  = new Schema({
	date: {type: Date, default: Date.now},
	school: {type: mongoose.Schema.Types.ObjectId, ref : 'School', required: true},
	class: {type: mongoose.Schema.Types.ObjectId, ref : 'Class', required: true},
	isHomeroom: {type: Boolean, default: false, required : true},
	studentsAttended: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}]
});

module.exports = mongoose.model('Calendar', calendarSchema);
