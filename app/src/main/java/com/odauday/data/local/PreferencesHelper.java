package com.odauday.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;

/**
 * Created by infamouSs on 2/28/18.
 */

public class PreferencesHelper {
    
    private final SharedPreferences mSharedPreferences;
    
    private static final String PREF_NAME = "app_pref";
    
    @Inject
    public PreferencesHelper(Context context) {
        this.mSharedPreferences = context.getApplicationContext()
                  .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    public String getAccessToken() {
        return "ACCESS_TOKEN";
    }
    
    public String getCurrentUserId() {
        return "USER_ID";
    }
}
