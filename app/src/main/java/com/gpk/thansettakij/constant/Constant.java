package com.gpk.thansettakij.constant;

/**
 * Created by nobtingtong on 11/11/2017.
 */

public class Constant {

    public static final String ROOT_URL = "http://event.tnews.co.th:9000/";

    public static final String API_ACTIVE = ROOT_URL + "api/event/active";
    public static final String API_NEWS= ROOT_URL + "api/list/news/%s"; //event_id
    public static final String API_PRODUCT = ROOT_URL + "api/list/product/%s"; //event_id
    public static final String API_BOOTHS = ROOT_URL + "api/list/booth/%s"; //event_id
    public static final String API_TEAMS = ROOT_URL + "api/list/team/%s"; //event_id
    public static final String API_PROFILE = ROOT_URL + "api/profit";
    public static final String API_EDUCATION = ROOT_URL + "api/education";
    public static final String API_SEX = ROOT_URL + "api/sex";
    public static final String API_REGISTER = ROOT_URL + "api/registers?name=%s&phone=%s&sex=%s&education=%s&company=%s&profit=%s&email=%s&event=%s&token_device=%s";
    public static final String API_CHECK_IN = ROOT_URL + "api/checkin/%s/%s/%s";


    public static int EVENT_ID = -1;
    public static String EVENT_NAME = "";

    public static final String URL_ABOUT = ROOT_URL + "view/about";
}
