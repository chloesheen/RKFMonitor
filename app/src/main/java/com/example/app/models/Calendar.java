package com.example.app.models;

import com.example.app.util.Pair;

import java.util.ArrayList;
import java.util.Date;

public class Calendar<T> extends Pair {

    private Date mDate;
    private T mInfo;

    public Calendar(Date date, T data) {
        super(date, data);

    }
}


//query by date to get classes attendance and food quantities
//query by month to get classes attendance and food quantities
//store each class and their daily or monthly attendance
//store each feeding program and their quantities
