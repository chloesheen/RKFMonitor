var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var classSchema  = new Schema({
	school: {type: mongoose.Schema.Types.ObjectId, ref: 'School', required: true},	
	teacher: {type: mongoose.Schema.Types.ObjectId, ref : 'Teacher', required : true},
	students: [{type: mongoose.Schema.Types.ObjectId, ref : 'Student'}]
});

module.exports = mongoose.model('Class', classSchema);
