const express = require('express'),
	router = express.Router(),
	jwt = require('jsonwebtoken'),
	uniqid = require('uniqid'),
	User = require('./../models/User');
	Student = require('./../models/Student'),	
	Class = require('./../models/Class'),
	School = require('./../models/School'),
	Student = require('./../models/Student'),
	Attendence = require('./../models/Attendence'),
	Calendar = require('./../models/Calendar'),	

module.exports = function(passport) {

	router.get('/profile', passport.authenticate('jwt', {session:false}), function(req,res) {
		let findUser = User.findById(req.user._id).populate('school').populate('class').exec();
		findUser.then(function(user) {
			res.status(200).json({
				id: user._id,
				firstName : user.firstName,
				lastName : user.lastName,
	  			gender: user.gender,
	  			schoolId: user.school.schoolId,
	  			nationalId: user.nationalId,
	  			telephone: user.telephoneNumber,
	  			class: user.class.name
			})
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});			
		})
	})

	router.put('/profile', passport.authenticate('jwt', {session:false}), function(req,res) {
		let findUser = User.findOneAndUpdate({_id: req.user._id}, {$set: {firstName: req.body.firstName, lastName: req.body.lastName, gender: req.body.gender, nationalId: req.body.nationalid, telephoneNumber: req.body.telephone}}, {new: true}).populate('school').populate('class').exec();
		findUser.then(function(user) {
			res.status(200).json({
				id: user._id,
				firstName : user.firstName,
				lastName : user.lastName,
	  			gender: user.gender,
	  			schoolid: user.school.schoolId,
	  			nationalid: user.nationalId,
	  			telephone: user.telephoneNumber,
	  			class: user.class.name 
			})
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});			
		})
	})

	router.get('/students/:id', passport.authenticate('jwt', {session: false}), function(req, res) {
		let findStudent = Student.findById(req.params.id).exec();
		findStudent.then(function(students) {
			res.status(200).json({
				id: student._id,
				firstName: student.firstName,
				lastName: student.lastName,
				gender: student.gender,
				dateofbirth: "" + student.birthMonth + "/" + student.birthDay + "/" + student.birthYear,
				guardian: student.parentName,
				telephone: student.telephoneNumber,
				nationalid: student.nationalId,
				avegrade: student.averageGrade,
				shoesize: student.shoeSize 
			})
		})
	})

	router.delete('/students/:id', passport.authenticate('jwt', {session: false}), function(req, res) {
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);			
		let updateStudent = Student.findOneAndUpdate({_id: req.params.id}, {$set: {archived: true}}, {new: true}).exec();
		let updateClass = Class.findOneAndUpdate({_id: req.user.class}, {$pull: {students: req.params.id}}, {new: true}).exec();
		let updateSchool = School.findOneAndUpdate({_id: req.user.school}, {$pull: {students: req.params.id}}, {new: true}).exec();
		let updateAttendence = Attendence.findOneAndUpdate({class: req.user.class, date: midNight}, {$pull: {studentsPresent: req.params.id, studentsNotPresent: req.params.id}}, {new: true}).exec();
		let updateCalendar = updateAttendence.then(async function(updatedAttendence) {
			let attendences = await Attendence.find({school: req.user.school, date: midNight}).exec();
			let totalAttendence = 0;
			for (let i=0; i<attendences.length; i++) {
				totalAttendence += attendences[i].studentsPresent.length;
			}
			let updatedCalendar = await Calendar.findOneAndUpdate({school: req.user.school, date: midNight}, {$set: {attendence: totalAttendence}}, {new: true});
			return updatedCalendar;
		}).catch(function(error) {
			throw error;
		})
		Promise.all([updateStudent,updateClass,updateSchool, updateAttendence, updateCalendar]).then(function(result) {
			return res.status(200).json({success: true})
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})


	router.get('/students', passport.authenticate('jwt', {session:false}), function(req,res) {
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);			
		let getAttendence = Attendence.findOne({class: req.user.class, date: midNight}).populate('class').populate('studentsPresent').populate('studentsNotPresent').select('studentsPresent studentsNotPresent').exec();
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
					attendenceList.push({id: studentsPresent[i]._id, firstName: studentsPresent[i].firstName, lastName: studentsPresent[i].lastName, attending: true});
					i++;
				} else {
					attendenceList.push({id: studentsNotPresent[j]._id, firstName: studentsNotPresent[j].firstName, lastName: studentsNotPresent[j].lastName, attending: false});
					j++;
				}
			}
			if (i == studentsPresent.length) {
				studentsNotPresent.slice(j).forEach(function(student) {
					attendenceList.push({id: student._id, firstName: student.firstName, lastName: student.lastName, attending: false});
				})
			} else {
				studentsPresent.slice(i).forEach(function(student) {
					attendenceList.push({id: student._id, firstName: student.firstName, lastName: student.lastName, attending: true});
				})
			}
			return res.status(200).json({class: attendence.class.name, students: attendenceList});
		}).catch(function(error) {
			return res.status(400).json({message: "Error in fetching from database"});
		})
	})

	router.post('/students', passport.authenticate('jwt', {session: false}), function(req, res) {
        let birthArr = req.body.dateofbirth.split("/");
        let birthMonth = Number((birthArr[0][0] == "0") ? birthArr[0][1] : birthArr[0]);
        let birthDay = Number(birthArr[1]);
        let birthYear = Number(birthArr[2]);
        let findSchool = School.findOne({name: req.body.schoolname}).exec();
        let findClass = findSchool.then(async function(school) {
        	let studentClass = await Class.findOne({name: req.body.classname, school: school._id}).exec();
        	return studentClass;
        }).catch(function(error) {
        	throw error;
        })
        let makeStudent = findClass.then(async function(studentClass) {
        	let newStudent = new Student({
        		firstName: req.body.firstName,
        		lastName: req.body.lastName,
        		teacher: studentClass.teacher,
        		class: studentClass._id,
        		birthMonth: birthMonth,
        		birthDay: birthDay,
        		birthYear: birthYear,
        		gender: req.body.gender,
        		parentName: req.body.guardian,
        		telephoneNumber: req.body.telephone,
        		nationalId: req.body.nationalid,
        		studentId: uniqid(),
        		averageGrade: req.body.avegrade,
        		shoeSize: req.body.shoesize
        	})
        	let savedStudent = await newStudent.save();
        	return savedStudent;
        }).catch(function(error) {
        	throw error;
        })
        let updateClass = makeStudent.then(async function(student) {
        	let updatedClass = await Class.findOneAndUpdate({_id: student.class}, {$push: {students: student._id}},{new: true}).exec();
        	return updatedClass;
        }).catch(function(error) {
        	throw error;
        })
        let updateSchool = makeStudent.then(async function(student) {
        	let updatedSchool = await School.findOneAndUpdate({name: req.body.schoolname}, {$push: {students: student._id}}, {new: true}).exec();
        	return updatedSchool;
        }).catch(function(error) {
        	throw error;
        })
        Promise.all([findSchool, findClass, makeStudent, updateClass, updateSchool]).then(function(results) {
        	let student = results[2];
        	res.status(200).json({
				id: student._id,
				firstName: student.firstName,
				lastName: student.lastName,
				gender: student.gender,
				dateofbirth: "" + student.birthMonth + "/" + student.birthDay + "/" + student.birthYear,
				guardian: student.parentName,
				telephone: student.telephoneNumber,
				nationalid: student.nationalId,
				avegrade: student.averageGrade,
				shoesize: student.shoeSize        		
        	})
        }).catch(function(error) {
        	res.status(400).json({success: false, message: "Error in updating database"});
        })
	})

	router.put('/students', passport.authenticate('jwt', {session:false}), function(req,res) {
		const currDate = new Date();
		const midNight = new Date(Math.floor(currDate.getTime() / (1000*60*60*24))*1000*60*60*24);
		let updateAttendence = Attendence.findOneAndUpdate({class: req.user.class, date: midNight}, {$set: {studentsPresent: req.body.attending, studentsNotPresent: req.body.notAttending}}, {new: true}).exec();	
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
			return res.status(400).json({success: false, message: "Error in fetching from database"});
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
			return res.status(400).json({success: false, message: "Error in fetching from database"});
		})
	})


	router.delete('/students/:id', passport.authenticate('jwt', {session:false}), async function(req,res) {
		let updateStudent = await Student.findOneAndUpdate({_id: req.params.id}, {$set: {archived: true}}).exec();
		let updateClass = await Class.findOneAndUpdate({_id: req.user.class}, {$pull: {students: req.params.id}}).exec();
		let updateSchool = await School.findOneAndUpdate({_id: req.user.school}, {$pull: {students: req.params.id}}).exec();
		Promise.all([updateStudent, updateClass, updateSchool]).then(function(results) {
			res.status(200).json({success: true});
		}).catch(function(error) {
			res.status(401).json({succss: false, message: "Error in updating database."});
		})
	})	

	return router;
}