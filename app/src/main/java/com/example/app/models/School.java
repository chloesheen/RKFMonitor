package com.example.app.models;

import java.util.ArrayList;

@org.parceler.Parcel
public class School {

    private String mSchoolID;

    private String mSchoolName;

    private ArrayList<Class> mClasses;


    public School() {

    }

    public School(String name, String schoolid, ArrayList<Class> classes) {
        mSchoolName = name;
        mSchoolID = schoolid;
        mClasses = classes;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public String getSchoolID() {
        return mSchoolID;
    }

    public ArrayList<Class> getClasses() {
        return mClasses;
    }
}