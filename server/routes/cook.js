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
			console.log("error is", error);
			return res.status(400).json({success: false, message: "Problem fetching from database"})
		})
	})

	router.put('/food', passport.authenticate('jwt', {session:false}), function(req,res) {
		console.log("body is", req.body);
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);	
		let getCalendar = (req.body.MealType === "Uji") ? Calendar.findOneAndUpdate({school: req.user.school, date: midNight}, {$set: {uji: {MealType: "Breakfast", Flour: req.body.Flour, Sugar: req.body.Sugar, Water: req.body.Water}}}).exec() : 
			(req.body.MealType === "Githeri") ? Calendar.findOneAndUpdate({school: req.user.school, date: midNight}, {$set: {githeri: {MealType: "Lunch (Githeri)", Maize: req.body.Maize, Beans: req.body.Beans, Salt: req.body.Salt, Oil: req.body.Oil}}}).exec() :
			Calendar.findOneAndUpdate({school: req.user.school, date: midNight}, {$set: {rb: {MealType: "Lunch (Rice and Beans)", Rice: req.body.Rice, Beans: req.body.Beans, Salt: req.body.Salt, Oil: req.body.Oil}}}).exec();
		getCalendar.then(function(calendar) {
			console.log("updated calendar", calendar);
			return res.status(200).json({success: true})
		}).catch(function(error) {
			console.log("error", error);
			return res.status(400).json({success: false, message: "Problem fetching from database"})
		})
	})

	return router;
}