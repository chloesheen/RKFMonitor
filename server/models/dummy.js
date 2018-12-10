//dummy data for database


const Class = require('./Class');
const School = require('./School');
const User = require('./User');
const Student = require('./Student');
const Calendar = require('./Calendar');

var schoolNames = ['Valley Academy', 'NextGen', 'Soko Primary'];
var names = ['James',' Dean', 'Maria', 'Vivanco' , 'Ian', 'Ogolla' ,'Erina', 'Naomi', 'Bernas', 'Arianna', 'Anna', 'Leslie', 'Chloe', 'Eunsoo', 'Sean'];

//// --- CONNECTING TO DATABASE ----//
const mongoose = require('mongoose');
mongoose.connect('mongodb://kokakapp:kokak123@ds044577.mlab.com:44577/kokak_schools'); //link of the database
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  console.log('we are connected!');

});

function randomFromArray (array){
    return array[Math.floor(Math.random()*array.length)];
}

//--- HELPER FUNCTION TO GENERATE TEACHERS --//
function genTeachers (school) {
    // generate teachers for a given school
    var ofTeachers = new Array(4);
    var i;
    for (i = 0; i < ofTeachers.length; i++) {
        var teacher = new User ({
            _id: new mongoose.Types.ObjectId(),
            firstName: randomFromArray(names),
            lastName: randomFromArray(names),
            password: 'dummyPassword',
            school: school._id,
            isTeacher:true
        });
        teacher.username = teacher.firstName.concat(teacher.lastName);
        ofTeachers[i] = teacher;
        ofTeachers[i].save(function (err) {
           if (err) return handleError(err);
        });
    }
    return ofTeachers;
}



function genStudents (classs) { // used by genClass function: function to generate students for a given class
   var ofStudents = new Array(10);
   var i;
   for (i = 0; i < ofStudents.length; i++) {
       var student = new Student ({
            _id: new mongoose.Types.ObjectId(),
            firstName : randomFromArray(names),
            lastName : randomFromArray(names),
            teacher: classs.teacher._id,
            class : classs.teacher._id
       });
       ofStudents[i] = student;
       ofStudents[i].save(function (err) {
           if (err) return handleError(err);
       });
    }
    return ofStudents;
}


function genClasses (schooll) { // function to generate the classes for a given school
   var ofClasses = new Array(2);
   var i;
   for (i = 0; i < ofClasses.length; i++) {
       var tempClass = new Class ({
            _id: new mongoose.Types.ObjectId(),
            name : 'P' + (i+1),
            school: schooll._id,
            teacher: schooll.teachers[i],

       });
       tempClass.students = genStudents(tempClass, tempClass.teacher)
       ofClasses[i] = tempClass;
       ofClasses[i].save(function (err) {
           if (err) return handleError(err);
       });

    }
    return ofClasses;

}

function addAll (intoArray, classArray){
    var i;
    for (i = 0; i < classArray.length; i++){
        Array.prototype.push.apply(intoArray,classArray[i].students);
    }
    return intoArray;
}

// function to create schools/ save school to mlab database
function genSchool (schoolName){
    var dummySchool  =  new School({
       _id: new mongoose.Types.ObjectId(),
        name: schoolName
        //students: addAll (this.students, this.classes),
    });

    dummySchool.teachers = genTeachers(dummySchool);
    dummySchool.classes = genClasses(dummySchool);
    dummySchool.students = addAll (dummySchool.students, dummySchool.classes);

    dummySchool.save(function (err){
        if (err) return handleError(err);
    });
}

// one line function to create schools from array
var i;
for (i = 0 ; i < schoolNames.length; i++){
    genSchool(schoolNames[i]);

}





//School.
//  findOne({name: 'Valley Academy'}).
//  populate('teacher1').
//  exec(function (err, School) {
//    if (err) return handleError(err);
//    console.log('The author is %s', School.teacher1.firstName);
//    // prints "The author is Ian Fleming"
//  });
