var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var calendarSchema  = new Schema({
	date: {type: Date, required: true},
	school: {type: mongoose.Schema.Types.ObjectId, ref : 'School', required: true},
	attendence: {type: Number, default: 0},
	foodServed: {type: Number, default: 0}
});

module.exports = mongoose.model('Calendar', calendarSchema);
