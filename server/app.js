const express = require('express'),
			app = express(),
			bodyParser = require('body-parser');
require('dotenv').config();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

//Mongoose==============================================================================================================================
const	mongoose = require('mongoose');
mongoose.connect(process.env.MONGO_URI);

//Routes============================================================================================================================================================
const	authenticate = require('./routes/authenticate');

app.use('/', authenticate);

//Connect===========================================================================================================================================================
app.listen(3000, function(req, res) {
	console.log("Listening on 3000");
})
