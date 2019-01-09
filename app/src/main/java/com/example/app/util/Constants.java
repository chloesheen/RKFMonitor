package com.example.app.util;

public class Constants {

    public final static String SHARED_PREFS_KEY="user_keys";

    /**
     * Teacher Base URLs
     */
    public final static String REQUEST_LOGIN = "https://kotak.herokuapp.com/login";
    public final static String REQUEST_STUDENT_PROFILE = "https://kotak.herokuapp.com/teachers/students";
    public final static String REQUEST_TEACHER_PROFILE ="https://kotak.herokuapp.com/profile";
    public final static String REQUEST_STUDENT_LIST = "https://kotak.herokuapp.com/teachers/students";
    public final static String REQUEST_SUBMIT_ATTENDANCE = "https://kotak.herokuapp.com/teachers/students";
    public final static String REQUEST_ADD_NEW_STUDENT = "https://kotak.herokuapp.com/teachers/students";
    public final static String REQUEST_REGISTER_TEACHER="c";


    /**
     * Food Base urls
     */
    public final static String REQUEST_COOK_DASHBOARD = "https://kotak.herokuapp.com/cook";
    public final static String REQUEST_ADD_FOOD="https://kotak.herokuapp.com/cook/food";

    /**
     * Organization Base urls
     */
    public final static String REQUEST_ORG_DASHBOARD = "https://kotak.herokuapp.com/organization/schools";
    public final static String REQUEST_CLASS_LIST="https://kotak.herokuapp.com/schools";
    public final static String REQUEST_FOOD_INFO="https://kotak.herokuapp.com/schools";
    public final static String REQUEST_ATTENDANCE_INFO="https://kotak.herokuapp.com/classes";


    /**
     * GET Request Codes
     */
    public final static int GET_STUDENT_PROFILE = 1000;
    public final static int GET_TEACHER_PROFILE = 1001;
    public final static int GET_STUDENTLIST_VIEW = 1010;
    public final static int GET_TOTAL_ATTENDANCE = 1011;
    public final static int GET_DAILY_ATTENDANCE = 1100;
    public final static int GET_MONTHLY_ATTENDANCE = 1110;
    public final static int GET_DAILY_FOOD = 1101;
    public final static int GET_MONTHLY_FOOD = 1111;
    public final static int GET_SCHOOLS = 10;
    public final static int GET_CLASSES = 101;

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
    public final static int PUT_FOOD = 2111;

    /**
     * DELETE Request Codes
     */
    public final static int DELETE_STUDENT_PROFILE = 3000;

}
