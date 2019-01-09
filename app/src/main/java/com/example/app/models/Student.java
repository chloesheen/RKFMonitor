package com.example.app.models;

@org.parceler.Parcel
public class Student extends StudentProfile {

    public Student() {
    }

    public Student(String firstName, String lastName, String id, boolean attendance) {
        super(firstName, lastName, id, attendance);
    }

    @Override
    public String toString() {
        return ( "First name: " + getFirstName() + " "
                + "Last name: " + getLastName() + " " + "Id: " + getId()
                + " " + "Attendance: " + Boolean.toString(isPresent()));
    }
}
