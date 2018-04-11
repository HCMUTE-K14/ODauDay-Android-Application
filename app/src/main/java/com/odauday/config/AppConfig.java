package com.odauday.config;

import com.odauday.BuildConfig;
import com.odauday.data.remote.search.model.Location;

/**
 * Created by infamouSs on 2/27/18.
 */

public class AppConfig {
    
    public static final int READ_TIMEOUT = 10;
    public static final int CONNECT_TIMEOUT = 10;
    
    public static final String DATABASE_NAME = "odauday_db";
    
    public static final int RATE_VND = 1000;
    
    public static final Location DEFAULT_LOCATION = new Location(10.780300f, 106.660862f);
    
    public static final int THREAD_POOL = 3;
    
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
