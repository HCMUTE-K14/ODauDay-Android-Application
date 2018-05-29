package com.odauday.data.local.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.odauday.ui.propertydetail.common.DirectionLocation;
import com.odauday.utils.TextUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/12/18.
 */
public class DirectionsPreferenceHelper {
    
    
    private final PreferencesHelper mPreferencesHelper;
    private final Gson mGson;
    
    @Inject
    DirectionsPreferenceHelper(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        this.mGson = builder.create();
    }
    
    public void putDirection(List<DirectionLocation> directionLocationList) {
        String str = mGson.toJson(directionLocationList, new TypeToken<List<DirectionLocation>>() {
        }.getType());
        if (!TextUtils.isEmpty(str)) {
            mPreferencesHelper.put(PrefKey.PREF_LIST_DIRECTION_LOCATION, str);
        } else {
            mPreferencesHelper.put(PrefKey.PREF_LIST_DIRECTION_LOCATION, "");
        }
    }
    
    public void putDirection(DirectionLocation location) {
        List<DirectionLocation> list = getDirection();
        list.add(location);
        
        putDirection(list);
    }
    
    public List<DirectionLocation> getDirection() {
        String str = mPreferencesHelper.get(PrefKey.PREF_LIST_DIRECTION_LOCATION, "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        } else {
            return mGson
                .fromJson(str, new TypeToken<List<DirectionLocation>>() {
                }.getType());
        }
    }
    
    public void clearDirection() {
        mPreferencesHelper.put(PrefKey.PREF_LIST_DIRECTION_LOCATION, "");
    }
    
}
