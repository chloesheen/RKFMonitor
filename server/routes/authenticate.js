const express = require('express'),
      router = express.Router(),
      jwt = require('jsonwebtoken'),      
      secret = process.env.secret,
      User = require('./../models/User'),
      School = require('./../models/School'),
      Class = require('./../models/Class');

router.post('/login', async function (req, res) {
  let getUser = User.findOne({username: req.body.username}).populate('school').exec();
  getUser.then(function(user) {
    if (!user) {
      return res.status(404).json({success: false, message: "Authentication failed. No user with given username was found"});
    } else {
      user.comparePassword(req.body.password, async function(err, isMatch) {
        if (isMatch && !err) {
          let simplifiedUser = {_id: user._id};
          let token = jwt.sign({data: simplifiedUser}, secret);
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

router.post('/register', async function (req, res) {
  if (req.body.role == "cook") {
    let newUser = new User({
      firstName: req.body.firstName,
      lastName: req.body.lastName,
      username: req.body.username,
      password: req.body.password,
      telephoneNumber: req.body.contact,
      isCook: true
    })
    let saveUser = newUser.save();
    let updateSchool = saveUser.then(async function(cook) {
      let updatedSchool = await School.findOneAndUpdate({name: req.body.school}, {$push: {cooks: cook._id}}, {new: true}).exec();
      let updatedUser = await User.findOneAndUpdate({_id: cook._id}, {$set: {school: updatedSchool._id}}, {new: true}).exec();
      return res.status(200).json({success: true});
    }).catch(function(error) {
      return res.status(401).json({success: false});
    })
  } else if (req.body.role == "teacher") {
    let newUser = new User({
      firstName: req.body.firstName,
      lastName: req.body.lastName,
      username: req.body.username,
      password: req.body.password,
      telephoneNumber: req.body.contact,
      isTeacher: true
    })
    let saveUser = newUser.save();
    let updateSchool = saveUser.then(async function(teacher) {
      let updatedSchool = await School.findOneAndUpdate({name: req.body.school}, {$push: {teachers: teacher._id}}, {new: true}).exec();
      return updatedSchool;      
    }).catch(function(error) {
      throw error;
    })
    let updateClass = Promise.all([saveUser, updateSchool]).then(async function(results) {
      let teacher = results[0],
        updatedSchool = results[1];
      let updatedClass = await Class.findOneAndUpdate({name: req.body.class, school: updatedSchool._id}, {$set: {teacher: teacher._id}}, {new: true}).exec();
      let updatedUser = await User.findOneAndUpdate({_id: teacher._id}, {$set: {class: updatedClass._id, school: updatedSchool._id}}, {new: true}).exec();
      return res.status(200).json({success: true});
    }).catch(function(error) {
      return res.status(401).json({success: false});
    })
  } else {
    let newUser = new User({
      firstName: req.body.firstName,
      lastName: req.body.lastName,
      username: req.body.username,
      password: req.body.password,
      telephoneNumber: req.body.contact,
      isAdministrator: true
    })
    newUser.save().then(function(user) {
      return res.status(200).json({success: true});
    }).catch(function(error) {
      return res.status(401).json({success: false});
    }) 
  }
});



module.exports = router;
