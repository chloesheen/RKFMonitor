package com.example.app.models;


@org.parceler.Parcel
public class TeacherProfile {

    private String mFirstName;

    private String mLastName;

    private String mSchoolId;

    private String mGender;

    private String mClassName;

    private String mTelephone;

    private String mNationalID;

    public TeacherProfile() {

    }

    public TeacherProfile(String firstname, String lastname, String schoolid,
                          String gender, String classname, String contact, String nationalid) {
        mFirstName = firstname;
        mLastName = lastname;
        mSchoolId = schoolid;
        mGender = gender;
        mClassName = classname;
        mTelephone = contact;
        mNationalID = nationalid;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getId() {
        return mSchoolId;
    }

    public String getGender() {
        return mGender;
    }

    public String getClassname() {
        return mClassName;
    }

    public String getTelephone() {
        return mTelephone;
    }

    public String getNationalID() {
        return mNationalID;
    }

}
