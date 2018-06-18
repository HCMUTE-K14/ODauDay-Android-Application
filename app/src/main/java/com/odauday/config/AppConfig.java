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
    
    public static final String PATTERN_DATE = "dd/MM/yyyy HH:mm";
    
    public static final int RATE_VND = 1000;
    
    public static final GeoLocation DEFAULT_GEO_LOCATION = new GeoLocation(10.8518, 106.772);
    
    public static final String VN_CURRENCY = "VND";
    
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
    
    public static final int THREAD_POOL = 3;
    
    public static final int LIMIT_RECENT_SEARCH = 10;
    
    public enum LANGUAGE {
        EN("English (Tiếng Anh)", "en"),
        VI("Vietnamese (Tiếng Việt)", "vi");
        
        private final String mString;
        private final String mCode;
        
        LANGUAGE(String str, String code) {
            this.mString = str;
            this.mCode = code;
        }
        
        public String getDisplayString() {
            return this.mString;
        }
        
        public String getCode() {
            return mCode;
        }
        
        public static String getStringByCode(String code) {
            switch (code) {
                
                case "vi":
                    return VI.getDisplayString();
                case "en":
                default:
                    return EN.getDisplayString();
            }
        }
    }
}
