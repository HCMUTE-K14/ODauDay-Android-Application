package com.odauday.config;

/**
 * Created by infamouSs on 2/27/18.
 */

public class AppConfig {
    
    public static final int READ_TIMEOUT = 30;
    public static final int CONNECT_TIMEOUT = 30;
    
    public static final int THREAD_POOL = 3;
    
    public enum LANGUAGE {
        EN("English (Tiếng Anh)"),
        VI("Vietnamese (Tiếng Việt)");
        
        private final String mString;
        
        LANGUAGE(String str) {
            this.mString = str;
        }
        
        public String getData() {
            return this.mString;
        }
    }
}
