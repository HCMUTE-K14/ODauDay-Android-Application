package com.odauday.api;

/**
 * Created by infamouSs on 3/27/18.
 */
public enum ApiLanguageSupport {
    
    VI("vi"),
    EN("en");
    
    private final String lang;
    
    ApiLanguageSupport(String lang) {
        this.lang = lang;
    }
    
    public String getValue() {
        return lang;
    }
}
