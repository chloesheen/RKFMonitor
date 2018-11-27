const express = require('express'),
      router = express.Router(),
      jwt = require('jsonwebtoken'),      
      secret = process.env.secret,
      User = require('./../models/User');

router.post('/login', function (req, res) {
  console.log("ayy");
  let getUser = User.findOne({username: req.body.username}).exec();
  getUser.then(function(user) {
    if (!user) {
      return res.status(404).json({success: false, message: "Authentication failed. No user with given username was found"});
    } else {
      user.comparePassword(req.body.password, function(err, isMatch) {
        if (isMatch && !err) {
          let token = jwt.sign({data: user}, secret);
          return res.status(200).json({ success: true, token: 'JWT' + token });
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
