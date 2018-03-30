package com.odauday.api;

import com.odauday.BuildConfig;

/**
 * Created by infamouSs on 2/27/18.
 */

public class EndPoint {
    
    public static final String BASE_URL = BuildConfig.BASE_URL;
    
    public static final String LOGIN_NORMAL = "auth";
    public static final String LOGIN_WITH_FACEBOOK = LOGIN_NORMAL + "/facebook";
    
    public static final String USERS = "users";
    public static final String REGISTER = USERS + "/registration";
    
    public static final String FORGOT_PASSWORD = USERS + "/forgot-password";
    public static final String CHANGE_PASSWORD = USERS + "/change-password";
    
    public static final String TAG="tag";
    
}
