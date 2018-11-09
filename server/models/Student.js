var mongoose = require('mongoose')
  , Schema = mongoose.Schema
  , model = mongoose.model

var studentSchema = new Schema({
	firstName    : {type: String, required: true},
  lastName     : {type: String, required: true},
  // Date of Birth
  month : {type: String, required: true},
  day : {type: Int, required: true},
  year : {type: Int, required: true},
  isPresent : {type: Boolean, default: true}
});

module.exports = mongoose.model('Student', studentSchema);
