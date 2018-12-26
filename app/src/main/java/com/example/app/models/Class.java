package com.example.app.models;

public class Class {

    private String mClassName;

    private String mId;

    private String mClassSize;

    private String mHomeroomTeacher;


    public Class(String name, String id, String size, String teacher) {
        mClassName = name;
        mId = id;
        mClassSize = size;
        mHomeroomTeacher = teacher;
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
}
