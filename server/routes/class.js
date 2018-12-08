const express = require('express'),
	router = express.Router(),
	jwt = require('jsonwebtoken'),
	Class = require('./../models/Class');
	Calendar = require('./../models/Calendar');


module.exports = function(passport) {

	router.get('/', passport.authenticate('jwt', {session:false}), function(req,res) {
		let getClasses = Class.find({teacher: req.user._id});
		getClasses.then(function(classes) {
			let parsedClasses = [];
			classes.forEach(function(singleClass) {
				parsedClasses.push({_id: singleClass._id, name: singleClass.name});
			})
			return res.status(200).json({classes: parsedClasses});
		}).catch(function(err) {
			return res.status(400).json({success: false, message: "Problems fetching from database."});
		})
	})

	router.get('/:id/students', passport.authenticate('jwt', {session:false}), function(req,res) {
		// const today = Date.now(),
		// 	year = today.getFullYear(),
		// 	month = today.getMonth(),
		// 	date = today.getDate(); 
		let getCalendar = Calendar.findOne({class: req.params.id, year: 2018, month: 11, date: 29})
												.populate('studentsPresent')
												.populate('studentsNotPresent')
												.select('studentsPresent studentsNotPresent').exec();
		getCalendar.then(function(calendar) {
			let i = 0, 
				j = 0,
				attendence = [];
			const plength = calendar.studentsPresent.length,
				  nplength = calendar.studentsNotPresent.length;
				  				let studentsPresent = calendar.studentsPresent;
				let studentsNotPresent =calendar.studentsNotPresent;
			while (i != plength && j != nplength) {
				if (studentsPresent[i].firstName+studentsPresent[i].lastName < studentsNotPresent[j].firstName+studentsNotPresent[j].lastName) {
					attendence.push({_id: studentsPresent[i]._id, firstName: studentsPresent[i].firstName, lastName: studentsPresent[i].lastName, attending: true});
					i++;
				} else {
					attendence.push({_id: studentsNotPresent[j]._id, firstName: studentsNotPresent[j].firstName, lastName: studentsNotPresent[j].lastName, attending: false});
					j++;
				}
			}
			console.log("here 1");
			if (i == studentsPresent.length) {
				console.log("here2");
				studentsNotPresent.slice(j).forEach(function(student) {
					attendence.push({_id: student._id, firstName: student.firstName, lastName: student.lastName, attending: false});
				})
			} else {
								console.log("here3");
				studentsPresent.slice(i).forEach(function(student) {
					attendence.push({_id: student._id, firstName: student.firstName, lastName: student.lastName, attending: true});
				})
			}
			return res.status(200).json({students: attendence});
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})

	router.put('/:id/students', passport.authenticate('jwt', {session:false}), function(req,res) {
		const today = Date.now(),
			year = today.getFullYear(),
			month = today.getMonth(),
			date = today.getDate(); 
		let updateCalendar = Calendar.findOneAndUpdate({class: req.params.id, year: year, month: month, date: date}, 
			{$set: {studentsPresent: attending}, $set: {studentsNotPresent: req.body.notAttending}}).exec();
		updateCalendar.then(function(calendar) {
			res.status(200).json({success: true});
		}).catch(function(error) {
			res.status(400).json({success: false});
		})
	})	

	return router;
}