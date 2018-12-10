const express = require('express'),
	router = express.Router(),
	jwt = require('jsonwebtoken'),
	Attendence = require('./../models/Attendence'),
	Calendar = require('./../models/Calendar'),
	Class = require('./../models/Class'),
	Student = require('./../models/Student');

module.exports = function(passport) {


	router.get('/students', passport.authenticate('jwt', {session:false}), function(req,res) {
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);			
		let getAttendence = Attendence.findOne({class: req.user.class, date: midNight}).populate('studentsPresent').populate('studentsNotPresent').select('studentsPresent studentsNotPresent').exec();
		getAttendence.then(function(attendence) {
			let i = 0, 
				j = 0,
				attendenceList = [];
			const plength = attendence.studentsPresent.length,
				  nplength = attendence.studentsNotPresent.length;
			let studentsPresent = attendence.studentsPresent;
			let studentsNotPresent = attendence.studentsNotPresent;
			while (i != plength && j != nplength) {
			if (studentsPresent[i].firstName+studentsPresent[i].lastName < studentsNotPresent[j].firstName+studentsNotPresent[j].lastName) {
				attendenceList.push({_id: studentsPresent[i]._id, firstName: studentsPresent[i].firstName, lastName: studentsPresent[i].lastName, attending: true});
				i++;
			} else {
				attendenceList.push({_id: studentsNotPresent[j]._id, firstName: studentsNotPresent[j].firstName, lastName: studentsNotPresent[j].lastName, attending: false});
				j++;
			}
			}
			if (i == studentsPresent.length) {
				studentsNotPresent.slice(j).forEach(function(student) {
					attendenceList.push({_id: student._id, firstName: student.firstName, lastName: student.lastName, attending: false});
				})
			} else {
				studentsPresent.slice(i).forEach(function(student) {
					attendenceList.push({_id: student._id, firstName: student.firstName, lastName: student.lastName, attending: true});
				})
			}
			return res.status(200).json({students: attendenceList});
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})

	router.put('/students', passport.authenticate('jwt', {session:false}), function(req,res) {
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);		
		let updateAttendence = Attendence.findOneAndUpdate({class: req.user.class, date: midNight}, {$set: {studentsPresent: req.body.attending, studentsNotPresent: req.body.notAttending}}).exec();	
		let getAttendences = updateAttendence.then(async function(updatedAttendence) {
			let totalAttendence = 0;
			let attendences = await Attendence.find({school: req.user.school, date: midNight});
			attendences.forEach(function(attendence) {
				totalAttendence += attendence.studentsPresent.length;
			})
			return totalAttendence;
		})
		getAttendences.then(async function(totalAttendence) {
			let updatedCalendar = await Calendar.findOneAndUpdate({school: req.user.school, date: midNight}, {attendence: totalAttendence} , {new: true}).exec();
			return res.status(200).json({success: true});
		}).catch(function(error) {
			return res.status(400).json({success: false, message: "Problem fetching from database"});
		})
	})	

	router.get('/students/:id', passport.authenticate('jwt', {session:false}), async function(req,res) {
		let getClass = await Class.findById(req.user.class).select('name').exec();
		let getStudent = await Student.findById(req.params.id).exec();
		Promise.all([getClass, getStudent]).then(function(results) {
			let studentClass = results[0],
				student = results[1],
				dateofbirth = "";
			if (student.birthMonth < 10) {
				dateofbirth += "0" + student.birthMonth;
			} else {
				dateofbirth += student.birthMonth;
			}
			dateofbirth += "/" + student.birthDay + "/" + student.birthYear;
			const responseBody = {
            	"id": student._id,
            	"firstName": student.firstName,
            	"lastName": student.lastName,
            	"gender": student.gender,
            	"dateofbirth": dateofbirth,
            	"classname": studentClass.name,
            	"guardian": student.parentName,
            	"telephone": student.telephoneNumber,
            	"nationalid": student.nationalId,
            	"avegrade": student.averageGrade,
            	"showsize": student.showSize
        	}
        	return res.status(200).json(responseBody);
		}).catch(function(error) {
			return res.status(400).json({success: false, message: "Problem fetching from database"});
		})
	})

	return router;
}