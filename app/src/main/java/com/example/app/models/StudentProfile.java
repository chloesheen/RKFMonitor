package com.example.app.models;

public class StudentProfile {

    private String mFirstName;

    private String mLastName;

    private boolean mAttendance;

    private String mId;

    private String mGender;

    private String mDateOfBirth;

    private String mGuardian;

    private String mClassName;

    private int mTelephone;

    private int mNationalID;

    private int mAverageGrade;

    private int mShoesize;

    public StudentProfile() {
    }

    public StudentProfile(String firstName, String lastName, String id, boolean attendance) {
        mFirstName = firstName;
        mLastName = lastName;
        mAttendance = attendance;
        mId = id;
    }

    public StudentProfile(String firstname, String lastname, String id,
                          boolean attendance, String gender, String dob, String guardian,
                          String classname, int tele, int nationalid, int aveGrade, int shoesize) {
        mFirstName = firstname;
        mLastName = lastname;
        mAttendance = attendance;
        mId = id;
        mGender = gender;
        mDateOfBirth = dob;
        mGuardian = guardian;
        mClassName = classname;
        mTelephone = tele;
        mNationalID = nationalid;
        mAverageGrade = aveGrade;
        mShoesize = shoesize;

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

    public String getGender() {
        return mGender;
    }

    public String getDOB() {
        return mDateOfBirth;
    }

    public String getGuardian() {
        return mGuardian;
    }

    public String getClassname() {
        return mClassName;
    }

    public int getTelephone() {
        return mTelephone;
    }

    public int getNationalID() {
        return mNationalID;
    }

    public int getAvegrade() {
        return mAverageGrade;
    }

    public int getShoesize() {
        return mShoesize;
    }

}
