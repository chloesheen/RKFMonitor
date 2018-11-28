var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var calendarSchema  = new Schema({
	year: {type: Number},
	month: {type: Number},
	date: {type: Number},
	school: {type: mongoose.Schema.Types.ObjectId, ref : 'School', required: true},
	class: {type: mongoose.Schema.Types.ObjectId, ref : 'Class', required: true},
	studentsAttended: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}]
});

module.exports = mongoose.model('Calendar', calendarSchema);
