package com.example.app.models;

public class Class {

    private String mClassName;

    private String mId;

    private int mClassSize;

    public Class(String name, String id, int size) {
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

    public int getClassSize() {
        return mClassSize;
    }
}
