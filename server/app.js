<<<<<<< HEAD
const express = require('express'),
			app = express(),
			bodyParser = require('body-parser');
require('dotenv').config();
=======
require('dotenv').config();
const express = require('express'),
	app = express(),
	bodyParser = require('body-parser'),
	passport = require('passport'),
	random = require('random-name');
>>>>>>> 9772b7f578fc9943c8fd3820a08bcbdf656a6244

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

<<<<<<< HEAD
//Mongoose==============================================================================================================================
const	mongoose = require('mongoose');
mongoose.connect(process.env.MONGO_URI);

//Routes============================================================================================================================================================
const	authenticate = require('./routes/authenticate');

app.use('/', authenticate);

//Connect===========================================================================================================================================================
app.listen(3000, function(req, res) {
=======

//Mongoose==============================================================================================================================
const mongoose = require('mongoose');
mongoose.connect(process.env.MONGO_URI);

//Routes============================================================================================================================================================
require('./config/passport')(passport);
const authenticate = require('./routes/authenticate'),
	organization = require('./routes/organization'),
	teacher = require('./routes/teacher'),
	cook = require('./routes/cook');

app.use('/', authenticate);
app.use('/organization', organization(passport));
app.use('/teachers', teacher(passport));
app.use('/cook', cook(passport));


//Connect===========================================================================================================================================================
app.listen(process.env.PORT || 3000, function(req, res) {
>>>>>>> 9772b7f578fc9943c8fd3820a08bcbdf656a6244
	console.log("Listening on 3000");
})
