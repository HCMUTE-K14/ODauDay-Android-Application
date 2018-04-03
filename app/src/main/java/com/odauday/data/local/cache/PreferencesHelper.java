package com.odauday.data.local.cache;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;

/**
 * Created by infamouSs on 2/28/18.
 */

public class PreferencesHelper {

    private static final String PREF_NAME = "app_pref";

    private final SharedPreferences mSharedPreferences;

    @Inject
    public PreferencesHelper(Context context) {
        this.mSharedPreferences = context.getApplicationContext()
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void put(String key, String defaultValue) {
        mSharedPreferences.edit().putString(key, defaultValue).apply();
    }

    public void put(String key, int defaultValue) {
        mSharedPreferences.edit().putInt(key, defaultValue).apply();
    }

    public void put(String key, float defaultValue) {
        mSharedPreferences.edit().putFloat(key, defaultValue).apply();
    }

    public void put(String key, boolean defaultValue) {
        mSharedPreferences.edit().putBoolean(key, defaultValue).apply();
    }

    public void put(String key, long defaultValue) {
        mSharedPreferences.edit().putLong(key, defaultValue).apply();
    }

    public String get(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public int get(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public float get(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public boolean get(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public long get(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }
}
