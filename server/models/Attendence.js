var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var attendenceSchema  = new Schema({
	date: {type: Date},
	school: {type: mongoose.Schema.Types.ObjectId, ref : 'School', required: true},
	class: {type: mongoose.Schema.Types.ObjectId, ref : 'Class', required: true},
	studentsPresent: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}],
	studentsNotPresent: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}]
});

module.exports = mongoose.model('Attendence', attendenceSchema);
