var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var schoolSchema = new Schema({
	name: {type: String, required: true},
	schoolId: {type: String, required: true},
	teachers: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
	cooks: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
	students: [{type: mongoose.Schema.Types.ObjectId, ref: 'Student'}],
	classes: [{type: mongoose.Schema.Types.ObjectId, ref: 'Class'}]
});

module.exports = mongoose.model('School', schoolSchema);
