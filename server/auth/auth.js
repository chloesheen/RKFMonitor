const passport = require('passport');
const localStrategy = require('passport-local').Strategy;
const User = require('../models/User');

passport.use('login', new localStrategy({
  usernameField : 'username',
  passwordField : 'password'
}, async (username, password, done) => {
  try {
    //Find the user associated with the email provided by the user
    const user = await User.findOne({username: username});
    if(!user){
      //If the user isn't found in the database, return a message
      return done(null, false, { message : 'User not found'});
    }
    //Validate password and make sure it matches with the corresponding hash stored in the database
    //If the passwords match, it returns a value of true.
    const validate = await user.isValidPassword(password);
    if( !validate ){
      return done(null, false, { message : 'Wrong Password'});
    }
    //Send the user information to the next middleware
    return done(null, user, { message : 'Logged in Successfully'});
  } catch (error) {
    return done(error);
  }
}));

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