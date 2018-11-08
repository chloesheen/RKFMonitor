const express = require('express'),
			app = express(),
			mongoose = require('mongoose'),


require('dotenv').config();


//Routes============================================================================================================================================================
const	authenticate = require('./routes/authenticate');

app.use('/', authenticate);

//Connect===========================================================================================================================================================
app.listen(3000, function(req, res) {
	console.log("Listening on 3000");
	mongoose.connect(process.env.MONGO_URI);
})
