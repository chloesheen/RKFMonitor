const express = require('express'),
	router = express.Router(),
	jwt = require('jsonwebtoken'),
	Calendar = require('./../models/Calendar');

module.exports = function(passport) {

	router.get('/', passport.authenticate('jwt', {session:false}), function(req,res) {
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);	
		let getCalendar = Calendar.findOne({school: req.user.school, date: midNight}).select('attendence').exec();
		getCalendar.then(function(calendar) {
			return res.status(200).json({success: true, numOfStudents: calendar.attendence})
		}).catch(function(error) {
			return res.status(400).json({success: false, message: "Problem fetching from database"})
		})
	})

	router.put('/food', passport.authenticate('jwt', {session:false}), function(req,res) {
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);	
		let getCalendar = Calendar.findOneAndUpdate({school: req.user.school, date: midNight}, {foodServed: req.body.foodServed}).select('attendence').exec();
		getCalendar.then(function(calendar) {
			return res.status(200).json({success: true})
		}).catch(function(error) {
			return res.status(400).json({success: false, message: "Problem fetching from database"})
		})
	})

	return router;
}