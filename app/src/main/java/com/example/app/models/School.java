package com.example.app.models;

import java.util.ArrayList;

@org.parceler.Parcel
public class School {

    private String mSchoolID;

    private String mSchoolName;

    private ArrayList<Class> mClassNames;

    private ArrayList<Streams> mStreams;

    public School() {

    }

    public School(String name, String schoolid) {
        mSchoolName = name;
        mSchoolID = schoolid;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public String getSchoolID() {
        return mSchoolID;
    }

}