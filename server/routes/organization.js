const express = require('express'),
	router = express.Router(),
	jwt = require('jsonwebtoken'),
	moment = require('moment'),
	School = require('./../models/School'),
	Class = require('./../models/Class'),
	Calendar = require('./../models/Calendar'),
	Attendence = require('./../models/Attendence');

module.exports = function(passport) {
	router.get('/schools', passport.authenticate('jwt', {session:false}), function(req,res) {
		let getSchools = School.find().select('_id name').exec();
		getSchools.then(function(schools) {
			return res.status(200).json({schools: schools});
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})

	router.get('/schools/:id/food', passport.authenticate('jwt', {session:false}), function(req,res) {
		let getCalendars = Calendar.find({school: req.params.id}).sort({date: -1}).exec();
		getCalendars.then(function(calendars) {
			let responseBody = {"daily": {}, "monthly": {}};
			for (let i=0; i<calendars.length; i++) {
	           	let date = moment(calendars[i].date).format('LL').split(",")[0]
			 	let month = moment(calendars[i].date).format('LL').split(",")[0].split(" ")[0]
			 	responseBody["daily"][date] = calendars[i].foodServed
			 	if (responseBody["monthly"][month]) {
			 		responseBody["monthly"][month] += calendars[i].foodServed
			 	} else {
			 		responseBody["monthly"][month] = calendars[i].foodServed
			 	}
			}
			return res.status(200).json(responseBody);
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})

	router.get('/schools/:id/classes', passport.authenticate('jwt', {session:false}), function(req,res) {
		let getSchool = School.findById(req.params.id).exec();
		let getClasses = Class.find({school: req.params.id}).select('_id name').exec();
		Promise.all([getSchool,getClasses]).then(function(values) {
			let school = values[0],
				classes = values[1];
			return res.status(200).json({school: school.name , classes: classes});
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})

	router.get('/classes/:id/attendence', passport.authenticate('jwt', {session:false}), function(req,res) {
		let getAttendences = Attendence.find({class: req.params.id}).populate('class').sort({date: -1}).exec();
		getAttendences.then(function(attendences) {
			let responseBody = {"daily": {}, "monthly": {}};
			for (let i=0; i<attendences.length; i++) {
	           	let date = moment(calendars[i].date).format('LL').split(",")[0];
			 	let month = moment(calendars[i].date).format('LL').split(",")[0].split(" ")[0];
			 	if (responseBody["daily"][date]) {
			 		responseBody["daily"][date][attendences[i].class.name] = attendences[i].class.studentsPresent.length;
			 	} else {
			 		responseBody["daily"][date] = {};
			 		responseBody["daily"][date][attendences[i].class.name] = attendences[i].class.studentsPresent.length;
			 	}
			 	if (responseBody["monthly"][month]) {
			 		if (responseBody["monthly"][month][attendences[i].class.name]) {
			 			responseBody["monthly"][month][attendences[i].class.name] += attendences[i].class.studentsPresent.length;
			 		} else {
			 			responseBody["monthly"][month][attendences[i].class.name] = attendences[i].class.studentsPresent.length;
			 		}
			 	} else {
			 		responseBody["monthly"][month] = {};
			 		responseBody["monthly"][month][attendences[i].class.name] = attendences[i].class.studentsPresent.length;
			 	}
			}
			return res.status(200).json(responseBody);
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})		

	return router;

}
