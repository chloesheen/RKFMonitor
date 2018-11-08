const express = require('express'),
      passport = require('passport'),
      jwt = require('jsonwebtoken'),
      router = express.Router(),
      User = require('./../models/User');



router.post('/login', async (req, res, next) => {
  passport.authenticate('login', async function(error, user, info) {
    try {
      if(err || !user){
        const error = new Error('An Error occured')
        return next(error);
      }
      req.login(user, { session : false }, async function(error) {
        if (error) {
          return next(error)
        }
        const body = {_id: user._id, username: user.username};
        const token = jwt.sign({ user: body}, 'topse_kret');
        //need to replace with an actual secret signature;
        return res.json({success: true, token: token}).status(200);
      });
    } catch {
      return next(error);
    };
  });
});

module.exports = router;