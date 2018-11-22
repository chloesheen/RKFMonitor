package com.example.app.models;

public class Student {

    private String mFirstName;

    private String mLastName;

    private boolean mAttendance;

    private String mId;

    public Student(String firstName, String lastName, String id, boolean attendance) {
        mFirstName = firstName;
        mLastName = lastName;
        mAttendance = attendance;
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public boolean isPresent() {
        return mAttendance;
    }

    public String getId() {
        return mId;
    }
}
