package com.example.app.models;

import android.os.Parcel;
import android.os.Parcelable;

@org.parceler.Parcel
public class StudentProfile{

    private String mFirstName;

    private String mLastName;

    private boolean mAttendance;

    private String mId;

    private String mStudentId;

    private String mGender;

    private String mDateOfBirth;

    private String mGuardian;

    private String mClassName;

    private String mSchoolName;

    private String mTelephone;

    private String mNationalID;

    private String mAverageGrade;

    private String mShoesize;

    public StudentProfile() {
    }

    public StudentProfile(String firstName, String lastName, String id, boolean attendance) {
        mFirstName = firstName;
        mLastName = lastName;
        mAttendance = attendance;
        mId = id;
    }

    public StudentProfile(String firstname, String lastname, String id, String studentId, String gender, String dob, String guardian,
                          String schoolname, String classname, String tele, String nationalid, String aveGrade, String shoesize) {
        mFirstName = firstname;
        mLastName = lastname;
        mId = id;
        mStudentId = studentId;
        mGender = gender;
        mDateOfBirth = dob;
        mGuardian = guardian;
        mSchoolName = schoolname;
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

    public Boolean updateAttendance(Boolean bool) {
        return mAttendance = bool;
    }

    public String getId() {
        return mId;
    }

    public String getStudentId() {return mStudentId;}

    public String getGender() {
        return mGender;
    }

    public String getDOB() {
        return mDateOfBirth;
    }

    public String getGuardian() {
        return mGuardian;
    }

    public String getSchoolname() {return mSchoolName;}

    public String getClassname() {
        return mClassName;
    }

    public String getTelephone() {
        return mTelephone;
    }

    public String getNationalID() {
        return mNationalID;
    }

    public String getAvegrade() {
        return mAverageGrade;
    }

    public String getShoesize() {
        return mShoesize;
    }

}
