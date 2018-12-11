const express = require('express'),
      router = express.Router(),
      jwt = require('jsonwebtoken'),      
      secret = process.env.secret,
      User = require('./../models/User'),
      School = require('./../models/School'),
      Class = require('./../models/Class');

router.post('/login', function (req, res) {
  let getUser = User.findOne({username: req.body.username}).populate('school').exec();
  getUser.then(function(user) {
    if (!user) {
      return res.status(404).json({success: false, message: "Authentication failed. No user with given username was found"});
    } else {
      user.comparePassword(req.body.password, async function(err, isMatch) {
        if (isMatch && !err) {
          let token = jwt.sign({data: user}, secret);
          let schoolId = user.school;
          let classId = user.class;
          let userSchool = await School.findById(schoolId).exec();
          let userClass = await Class.findById(classId).exec();
          return res.status(200).json({ success: true, JWT: 'JWT ' + token , school: (userSchool) ? userSchool.name : "", class:  (userClass) ? userClass.name : "", isAdministrator: user.isAdministrator, isTeacher: user.isTeacher, isCook: user.isCook});
        } else {
          return res.send(404, { success: false, message: 'Authentication failed. Incorrect password.'});
        }
      });
    }
  }).catch(function(error) {
    return res.status(404).json({success: false, message: "Error in the server"});
  })
});


module.exports = router;
