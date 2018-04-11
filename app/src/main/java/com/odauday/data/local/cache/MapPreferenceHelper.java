package com.odauday.data.local.cache;

import com.google.gson.Gson;
import com.odauday.config.AppConfig;
import com.odauday.data.remote.search.model.Location;
import com.odauday.utils.TextUtils;
import javax.inject.Inject;

/**
 * Created by infamouSs on 4/11/18.
 */

public class MapPreferenceHelper {
    
    public static float DEFAULT_ZOOM_LEVEL = 15.0f;
    
    private final PreferencesHelper mPreferencesHelper;
    
    private final Gson mGson;
    
    @Inject
    public MapPreferenceHelper(PreferencesHelper preferencesHelper, Gson gson) {
        this.mPreferencesHelper = preferencesHelper;
        this.mGson = gson;
    }
    
    
    public void putLastLocation(Location location) {
        String str = mGson.toJson(location);
        
        mPreferencesHelper.put(PrefKey.LAST_LOCATION, str);
    }
    
    public Location getLastLocation() {
        String str = mPreferencesHelper.get(PrefKey.LAST_LOCATION, "");
        
        if (TextUtils.isEmpty(str)) {
            return AppConfig.DEFAULT_LOCATION;
        }
        
        return mGson.fromJson(str, Location.class);
    }
    
    public void putLastZoomLevel(float zoom) {
        mPreferencesHelper.put(PrefKey.LAST_ZOOM_LEVEL, zoom);
    }
    
    public float getLastZoomLevel() {
        return mPreferencesHelper.get(PrefKey.LAST_ZOOM_LEVEL, DEFAULT_ZOOM_LEVEL);
    }
}
