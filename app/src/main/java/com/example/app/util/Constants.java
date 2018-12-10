package com.example.app.util;

public class Constants {

    public final static String GET_REQUEST = "Get";
    public final static String POST_REQUEST = "Post";
    public final static String PUT_REQUEST = "Put";

    /**
     * Base URLs
     */
    public final static String REQUEST_STUDENT_PROFILE = "";
    public final static String REQUEST_TEACHER_PROFILE ="d";
    public final static String REQUEST_STUDENT_LIST = "z";
    public final static String REQUEST_SUBMIT_ATTENDANCE = "";
    public final static String REQUEST_LOGIN = "https://kotak.herokuapp.com/login";
    public final static String REQUEST_ADD_NEW_STUDENT = "a";
    public final static String REQUEST_ADD_FOOD="b";
    public final static String REQUEST_REGISTER_TEACHER="c";

    public final static String SHARED_PREFS_KEY="user_keys";

    /**
     * GET Request Codes
     */
    public final static int GET_STUDENT_PROFILE = 1000;
    public final static int GET_TEACHER_PROFILE = 1001;
    public final static int GET_STUDENTLIST_VIEW = 1010;
    public final static int GET_TOTAL_ATTENDANCE = 1011;
    public final static int GET_SCHOOL_ATTENDANCE = 1100;
    public final static int GET_SCHOOL_FOOD = 1101;

    /**
     * POST Request Codes
     */
    public final static int POST_FOOD = 11100;
    public final static int POST_NEW_STUDENT = 111101;
    public final static int POST_NEW_SCHOOL = 111110;
    public final static int POST_NEW_TEACHER = 111111;
    public final static int POST_LOGIN = 111011;


    /**
     * PUT Request Codes
     */
    public final static int PUT_STUDENT_PROFILE = 2000;
    public final static int PUT_TEACHER_PROFILE = 2001;
    public final static int UPDATE_PASSWORD = 2010;
    public final static int PUT_STUDENT_ATTENDANCE = 2011;

}
