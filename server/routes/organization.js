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

	router.get('/schools/:id/attendence', passport.authenticate('jwt', {session:false}), function(req,res) {
		let getCalendars = Calendar.find({school: req.params.id}).sort({date: -1}).exec();
		getCalendars.then(function(calendars) {
			let responseBody = {"daily": {}, "monthly": {}};
			for (let i=0; i<calendars.length; i++) {
	           	let date = moment(calendars[i].date).format('LL').split(",")[0]
			 	let month = moment(calendars[i].date).format('LL').split(",")[0].split(" ")[0]
			 	responseBody["daily"][date] = calendars[i].attendence
			 	if (responseBody["monthly"][month]) {
			 		responseBody["monthly"][month] += calendars[i].attendence
			 	} else {
			 		responseBody["monthly"][month] = calendars[i].attendence
			 	}
			}
			return res.status(200).json(responseBody);
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
		let getClasses = Class.find({school: req.params.id}).select('_id name').exec();
		getClasses.then(function(classes) {
			return res.status(200).json({classes: classes});
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})	

	router.get('/classes/:id/attendence', passport.authenticate('jwt', {session:false}), function(req,res) {
		let getAttendences = Attendence.find({class: req.params.id}).sort({date: -1}).exec();
		getAttendences.then(function(attendences) {
			let responseBody = {"daily": {}, "monthly": {}};
			for (let i=0; i<attendences.length; i++) {
	           	let date = moment(attendences[i].date).format('LL').split(",")[0]
			 	let month = moment(attendences[i].date).format('LL').split(",")[0].split(" ")[0]
			 	responseBody["daily"][date] = attendences[i].studentsPresent.length
			 	if (responseBody["monthly"][month]) {
			 		responseBody["monthly"][month] += attendences[i].studentsPresent.length
			 	} else {
			 		responseBody["monthly"][month] = attendences[i].studentsPresent.length
			 	}
			}
			return res.status(200).json(responseBody);
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})		

	return router;

}
