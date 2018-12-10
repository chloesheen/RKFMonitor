var mongoose = require('mongoose'),
<<<<<<< HEAD
    Schema = mongoose.Schema
=======
    Schema = mongoose.Schema,
>>>>>>> 9772b7f578fc9943c8fd3820a08bcbdf656a6244
    model = mongoose.model,
    bcrypt = require('bcrypt-nodejs');


var userSchema = new Schema({
	createdAt: {type: Date, default: Date.now},
	firstName : {type: String, required: true},
	lastName : {type: String, required: true},
	username: {type: String, required: true},
	password : {type: String, required: true},
<<<<<<< HEAD
	school: {type: mongoose.Schema.Types.ObjectId, ref: 'School'},
	isAdministrator: {type: Boolean, default: false},
	isTeacher: {type: Boolean, default: false},
	isChef: {type: Boolean, default: false}
=======
  school: {type: mongoose.Schema.Types.ObjectId, ref: 'School'},
  class: {type: mongoose.Schema.Types.ObjectId, ref: 'Class'},
	isAdministrator: {type: Boolean, default: false},
	isTeacher: {type: Boolean, default: false},
	isCook: {type: Boolean, default: false}
>>>>>>> 9772b7f578fc9943c8fd3820a08bcbdf656a6244
});

userSchema.pre('save', function(next) {
  var user = this;
  var SALT_FACTOR = 5;

  if (!user.isModified('password')) return next();

  bcrypt.genSalt(SALT_FACTOR, function(err, salt) {
    if (err) return next(err);

    bcrypt.hash(user.password, salt, null, function(err, hash) {
      if (err) return next(err);
      user.password = hash;
      next();
    });
  });
});

userSchema.methods.comparePassword = function(candidatePassword, cb) {
  bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
    if (err) return cb(err);
    cb(null, isMatch);
  });
};


module.exports = mongoose.model('User', userSchema);
