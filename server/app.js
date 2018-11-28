require('dotenv').config();
const express = require('express'),
	app = express(),
	bodyParser = require('body-parser'),
	passport = require('passport');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

//Mongoose==============================================================================================================================
const	mongoose = require('mongoose');
mongoose.connect(process.env.MONGO_URI);

//Routes============================================================================================================================================================
require('./config/passport')(passport);
const authenticate = require('./routes/authenticate'),
	classes = require('./routes/class');


app.use('/', authenticate);
app.use('/classes', classes(passport));

//Connect===========================================================================================================================================================
app.listen(process.env.PORT || 3000, function(req, res) {
	console.log("Listening on 3000");
})
