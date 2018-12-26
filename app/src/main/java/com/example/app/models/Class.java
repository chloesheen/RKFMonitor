package com.example.app.models;

import java.util.ArrayList;

public class Class {

    private String mClassName;

    private String mId;

    private String mClassSize;

    private String mHomeroomTeacher;

    private ArrayList<Calendar> mClasscalendar;

    public Class(String name, String id, String size, String teacher) {
        mClassName = name;
        mId = id;
        mClassSize = size;
        mHomeroomTeacher = teacher;
        mClasscalendar = new ArrayList<>();
    }

    public String getName() {
        return mClassName;
    }

    public String getId() {
        return mId;
    }

    public String getClassSize() {
        return mClassSize;
    }

    public String getTeacher() { return mHomeroomTeacher; }

    public ArrayList<Calendar> getCalendars() {
        return mClasscalendar;
    }

    public void addCalendar(Calendar calendar) {
        mClasscalendar.add(calendar);
    }
}
