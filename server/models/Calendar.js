var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var calendarSchema  = new Schema({
	year: {type: Number},
	month: {type: Number},
	date: {type: Number},
	school: {type: mongoose.Schema.Types.ObjectId, ref : 'School', required: true},
	class: {type: mongoose.Schema.Types.ObjectId, ref : 'Class', required: true},
	studentsPresent: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}],
	studentsNotPresent: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}]
	//food for a day
	// total attendance

});

module.exports = mongoose.model('Calendar', calendarSchema);
