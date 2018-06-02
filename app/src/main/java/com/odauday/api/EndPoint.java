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
    public static final String RESEND_ACTIVATION = USERS + "/resend-activation";
    public static final String GET_AMOUNT = USERS + "/get-amount";
    
    
    public static final String SEARCH_PROPERTY = "search";
    
    public static final String AUTOCOMPLETE_PLACE = "auto-complete-place";
    
    public static final String GEO_INFO = "geo-info";
    public static final String TAG = "tag";
    public static final String FAVORITE = "favorite";
    public static final String SAVE_SEARCH = "save-search";
    public static final String PROPERTY = "property";
    public static final String HISTORY = "history";
    
    public static final String IMAGE = "image";
    public static final String IMAGE_UPLOAD = IMAGE + "/upload";
    
    public static final String STATIC_MAP = "static-map";
    
    public static final String DIRECTION = "direction";
    
    public static final String PROPERTY_DETAIL = PROPERTY + "/detail";
    public static final String NOTE = "note";
    
    public static final String PREMIUM = "premium";
    public static final String SUBSCRIBE = PREMIUM + "/subscribe";
    
    public static final String SIMILAR_PROPERTY = "similar-property";
}
