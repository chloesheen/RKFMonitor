package com.example.app.models;

import java.util.ArrayList;

@org.parceler.Parcel
public class School {

    private String mSchoolID;

    private String mSchoolName;

    private ArrayList<Class> mClasses;

    private ArrayList<Calendar> mSchoolCalendars;


    public School() {

    }

    public School(String name, String schoolid, ArrayList<Class> classes) {
        mSchoolName = name;
        mSchoolID = schoolid;
        mClasses = classes;
        mSchoolCalendars = new ArrayList<>();
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

    public ArrayList<Calendar> getCalendars() {
        return mSchoolCalendars;
    }

    public void addCalendar(Calendar calendar) {
        mSchoolCalendars.add(calendar);
    }
}