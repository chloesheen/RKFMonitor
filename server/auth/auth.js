const passport = require('passport');
const localStrategy = require('passport-local').Strategy;
const User = require('../models/User');

passport.use(new LocalStrategy({
        usernameField: 'email',
        passwordField: 'password'
    }, 
    function (email, password, cb) {
        User.findOne({email: {$eq: email}})
           .then(user => {
               if (!user) {
                   return cb(null, false, {message: 'Incorrect email or password.'});
               }
               return cb(null, user, {message: 'Logged In Successfully'});
          })
          .catch(err => cb(err));
    }
));


passport.use(new JWTstrategy({
  //secret we used to sign our JWT
  secretOrKey : 'topse_kret',
  jwtFromRequest : ExtractJwt.fromHeader("authorization");
  //need to
}, async (token, done) => {
  try { make 
    return done(null, token.user);
  } catch (error) {
    done(error);
  }
}));