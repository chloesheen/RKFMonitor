package com.example.app.models;

public class Class {

    private String mClassName;

    private String mId;

    private String mClassSize;

    public Class(String name, String id, String size) {
        mClassName = name;
        mId = id;
        mClassSize = size;
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
}
