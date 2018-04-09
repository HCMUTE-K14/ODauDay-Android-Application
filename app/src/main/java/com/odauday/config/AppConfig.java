package com.odauday.config;

import com.odauday.BuildConfig;

/**
 * Created by infamouSs on 2/27/18.
 */

public class AppConfig {
    
    public static final int READ_TIMEOUT = 30;
    public static final int CONNECT_TIMEOUT = 30;
    
    public static final String DATABASE_NAME = "odauday_db";
    
    public static final int RATE_VND = 1000;
    
    public static final int THREAD_POOL = 3;
    
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
