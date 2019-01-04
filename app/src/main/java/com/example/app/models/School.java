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

    public School(String name, String schoolid) {
        mSchoolName = name;
        mSchoolID = schoolid;
        mSchoolCalendars = new ArrayList<>();
        mClasses = new ArrayList<>();
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

    public void addClasses(ArrayList<Class> classesList) {
        mClasses.addAll(classesList);
    }
}