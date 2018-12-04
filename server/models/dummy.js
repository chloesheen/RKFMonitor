//dummy data for database


//// --- CONNECTING TO DATABASE ----//
const mongoose = require('mongoose');
mongoose.connect('mongodb://kokakapp:kokak123@ds044577.mlab.com:44577/kokak_schools'); //link of the database
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  console.log('we are connected!');
});

const Class = require('./Class');
const School = require('./School');
const User = require('./User');
const Student = require('./Student');
const Calendar = require('./Calendar');
//--- HELPER FUNCTION TO GENERATE TEACHERS --//
//function genTeachers (school){
//    // generate teachers for a given school
//    var ofTeachers = new Array();
//
//}


////----- SCHOOL VARIABLES --- //
//
////////var sokoPrimary = new School({
////////   _id: new mongoose.Types.ObjectId(),
////////    name: 'SOKO Primary'
////////});
////////var nextgen = new School({
////////  _id: new mongoose.Types.ObjectId(),
////////   name: 'NextGen'
////////   teachers: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
////////	cooks: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
////////	students: [{type: mongoose.Schema.Types.ObjectId, ref: 'Student'}],
////////	classes: [{type: mongoose.Schema.Types.ObjectId, ref: 'Class'}]
//////});


// --- TEST USING SCHOOL SCHEMA
var valleyAcademy =  new School({
   _id: new mongoose.Types.ObjectId(),
    name: 'Valley Academy'

});

valleyAcademy.save(function (err){
    if (err) return handleError(err);

    //teacher field
    var teacher1 = new User({
       	firstName : 'Tessa',
       	lastName : 'Oumer',
       	username: 'tessaoumer',
       	password : 'tessa',
       	school: valleyAcademy._id,
       	isTeacher: true
    });

    teacher1.save(function (err){
        if (err) return handleError(err);

    });
    //student field
    var student1 = new Student({
        _id: new mongoose.Types.ObjectId(),
        firstName : 'Maria',
        lastName : 'Vivanco',
        teacher: teacher1._id
    });


    student1.save(function (err) {
        if (err) return handleError(err);
    });

    var class1 = new Class({
     	name: 'P1',
     	school: valleyAcademy._id,
     	teacher: teacher1._id,
     	student: student1._id
    });
    class1.save(function (err) {
         if (err) return handleError(err);
    });
});

School.
  findOne({ name: 'Valley Academy' }).
  populate('teacher1').
  exec(function (err, School) {
    if (err) return handleError(err);
    console.log('The author is %s', School.teacher1.firstName);
    // prints "The author is Ian Fleming"
  });


//
//    var teacher1 = new Teacher({
//    	firstName : 'Tessa',
//    	lastName : 'Oumer',
//    	username: 'tessaoumer',
//    	password : 'tessa',
//    	isTeacher: true
//    });
//       //author: student1._id    // assign the _id from the person
//      teacher1.save(function (err) {
//        if (err) return handleError(err);
//
//
//        });
//      });
//
//}


//  createdAt: {type: Date, default: Date.now},
//  	firstName : {type: String, required: true},
//  	lastName : {type: String, required: true},
//  	username: {type: String, required: true},
//  	password : {type: String, required: true},
//    school: {type: mongoose.Schema.Types.ObjectId, ref: 'School', required: true},
//  	isAdministrator: {type: Boolean, default: false},
//  	isTeacher: {type: Boolean, default: false},
//  	isChef: {type: Boolean, default: false}

//});