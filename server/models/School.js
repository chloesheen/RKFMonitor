var mongoose = require('mongoose')
  , Schema = mongoose.Schema
  , model = mongoose.model

var schoolSchema = new Schema({
	name     : {type: String, required: true},
  teachers : [{type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true}],
	cooks    : [{type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true}],
	students : [{type: mongoose.Schema.Types.ObjectId, ref: 'Student', required: true}],
  totalStudentsPresent : {type: Int, default: 0, required: true}
});

module.exports = mongoose.model('School', schoolSchema);
