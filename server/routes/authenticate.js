const express = require('express'),
      passport = require('passport'),
      jwt = require('jsonwebtoken'),
      router = express.Router(),
      User = require('./../models/User');



router.post('/login', function (req, res) {
  User.findOne({
    username: req.body.username.toLowerCase()
  }, function(err, user) {
    if (err) throw err;
    if (!user) {
      res.send({ success: false, message: 'Authentication failed. User not found.'});
    } else {
      // Check if password matches
      user.comparePassword(req.body.password, function(err, isMatch) {
        if (isMatch && !err) {
          // Create token if the password matched and no error was thrown
          let token = jwt.sign({data: user}, 'topse_kret');
          res.json({ success: true, token: 'JWT' + token });
        } else {
          res.send(401, { success: false, message: 'Authentication failed. Incorrect password.'});
        }
      });
    }
  });

});

module.exports = router;
