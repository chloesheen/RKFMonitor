require('dotenv').config();
const express = require('express'),
	app = express(),
	bodyParser = require('body-parser'),
	passport = require('passport');
var random = require('random-name');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

const Class = require('./models/Class');
const School = require('./models/School');
const User = require('./models/User');
const Student = require('./models/Student');
const Calendar = require('./models/Calendar');

//Mongoose==============================================================================================================================
const	mongoose = require('mongoose');
mongoose.connect(process.env.MONGO_URI);

//Routes============================================================================================================================================================
require('./config/passport')(passport);
const authenticate = require('./routes/authenticate'),
	classes = require('./routes/class');

app.use('/', authenticate);
app.use('/classes', classes(passport));

app.get('/', async function(req, res) {
	let classes = await Class.find();

	for (let i=0; i<classes.length; i++) {
		let newCalendar = new Calendar({
			year: 2018,
			month: 11,
			date: 29,
			school: classes[i].school,
			class: classes[i]._id,
			studentsNotPresent: classes[i].students
		})
		let savedCalendar = await newCalendar.save();
		console.log("saved calendar", savedCalendar._id);
	}
	res.json({success: true});
})



//Connect===========================================================================================================================================================
app.listen(process.env.PORT || 3000, function(req, res) {
	console.log("Listening on 3000");
})
