package com.odauday.config;

import com.odauday.BuildConfig;
import com.odauday.data.remote.property.model.GeoLocation;

/**
 * Created by infamouSs on 2/27/18.
 */

public class AppConfig {
    
    public static final int READ_TIMEOUT = 10;
    public static final int CONNECT_TIMEOUT = 10;
    
    public static final String DATABASE_NAME = "odauday_db";
    
    public static final int RATE_VND = 1000;
    
    public static final GeoLocation DEFAULT_GEO_LOCATION = new GeoLocation(16.158653, 107.476252);
    
    public static final int THREAD_POOL = 2;
    
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
