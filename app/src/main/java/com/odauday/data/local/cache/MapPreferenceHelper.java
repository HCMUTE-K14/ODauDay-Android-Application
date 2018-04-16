package com.odauday.data.local.cache;

import com.google.gson.Gson;
import com.odauday.config.AppConfig;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.SearchType;
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
    public MapPreferenceHelper(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mGson = new Gson();
    }
    
    
    public void putLastLocation(GeoLocation geoLocation) {
        String str = mGson.toJson(geoLocation);
        
        mPreferencesHelper.put(PrefKey.LAST_LOCATION, str);
    }
    
    public GeoLocation getLastLocation() {
        String str = mPreferencesHelper.get(PrefKey.LAST_LOCATION, "");
        
        if (TextUtils.isEmpty(str)) {
            return AppConfig.DEFAULT_GEO_LOCATION;
        }
        
        return mGson.fromJson(str, GeoLocation.class);
    }
    
    public void putLastZoomLevel(float zoom) {
        mPreferencesHelper.put(PrefKey.LAST_ZOOM_LEVEL, zoom);
    }
    
    public float getLastZoomLevel() {
        return mPreferencesHelper.get(PrefKey.LAST_ZOOM_LEVEL, DEFAULT_ZOOM_LEVEL);
    }
    
    public void putRecentSearchCriteria(SearchCriteria searchCriteria) {
        String str = mGson.toJson(searchCriteria, SearchCriteria.class);
        if (searchCriteria.getSearchType() == SearchType.ALL.getValue()) {
            mPreferencesHelper.put(PrefKey.RECENT_SEARCH_CRITERIA_MODE_ALL, str);
        } else if (searchCriteria.getSearchType() == SearchType.BUY.getValue()) {
            mPreferencesHelper.put(PrefKey.RECENT_SEARCH_CRITERIA_MODE_BUY, str);
        } else if (searchCriteria.getSearchType() == SearchType.RENT.getValue()) {
            mPreferencesHelper.put(PrefKey.RECENT_SEARCH_CRITERIA_MODE_RENT, str);
        }
    }
    
    public SearchCriteria getRecentSearchCriteria(int mode) {
        String str;
        if (mode == SearchType.ALL.getValue()) {
            str = mPreferencesHelper.get(PrefKey.RECENT_SEARCH_CRITERIA_MODE_ALL, "");
        } else if (mode == SearchType.BUY.getValue()) {
            str = mPreferencesHelper.get(PrefKey.RECENT_SEARCH_CRITERIA_MODE_BUY, "");
        } else if (mode == SearchType.RENT.getValue()) {
            str = mPreferencesHelper.get(PrefKey.RECENT_SEARCH_CRITERIA_MODE_RENT, "");
        } else {
            str = "";
        }
        
        if (TextUtils.isEmpty(str)) {
            SearchCriteria defaultSearchCriteria = new SearchCriteria();
            if (mode == SearchType.ALL.getValue() || mode == SearchType.BUY.getValue() ||
                mode == SearchType.RENT.getValue()) {
                defaultSearchCriteria.setSearchType(mode);
            } else {
                defaultSearchCriteria.setSearchType(SearchType.ALL.getValue());
            }
            
            return defaultSearchCriteria;
        }
        
        return mGson.fromJson(str, SearchCriteria.class);
    }
    
    public void putLastSearchMode(int mode) {
        mPreferencesHelper.put(PrefKey.LAST_SEARCH_MODE, mode);
    }
    
    public int getLastSearchMode() {
        return mPreferencesHelper.get(PrefKey.LAST_SEARCH_MODE, SearchType.ALL.getValue());
    }
    
    public void putLastBounds(GeoLocation[] bounds) {
        String str = mGson.toJson(bounds);
        
        mPreferencesHelper.put(PrefKey.LAST_BOUNDS, str);
    }
    
    public GeoLocation[] getLastBounds() {
        String str = mPreferencesHelper.get(PrefKey.LAST_BOUNDS, "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        
        return mGson.fromJson(str, GeoLocation[].class);
    }
    
}
