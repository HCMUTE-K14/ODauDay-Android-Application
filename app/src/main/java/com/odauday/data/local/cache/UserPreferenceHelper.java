package com.odauday.data.local.cache;

import com.google.gson.Gson;
import com.odauday.model.User;
import com.odauday.utils.TextUtils;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/29/18.
 */
public class UserPreferenceHelper {
    
    private final PreferencesHelper mPreferencesHelper;
    
    private final Gson mGson;
    
    @Inject
    public UserPreferenceHelper(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mGson = new Gson();
    }
    
    
    public String getAccessToken() {
        return mPreferencesHelper.get(PrefKey.ACCESS_TOKEN, "");
    }
    
    public String getUserId() {
        return mPreferencesHelper.get(PrefKey.USER_ID, "");
    }
    
    public User getCurrentUser() {
        String strUser = mPreferencesHelper.get(PrefKey.CURRENT_USER, "");
        if (TextUtils.isEmpty(strUser)) {
            return null;
        }
        
        return mGson.fromJson(strUser, User.class);
    }
    
    public String getCurrentUserAsString() {
        return mPreferencesHelper.get(PrefKey.CURRENT_USER, "");
    }
    
    
    public void putAccessToken(String accessToken) {
        mPreferencesHelper.put(PrefKey.ACCESS_TOKEN, accessToken);
    }
    
    public void putUserId(String userId) {
        mPreferencesHelper.put(PrefKey.USER_ID, userId);
    }
    
    public void putCurrentUser(User user) {
        mPreferencesHelper.put(PrefKey.CURRENT_USER, mGson.toJson(user));
    }
    
    public void putCurrentUser(String user) {
        mPreferencesHelper.put(PrefKey.CURRENT_USER, user);
    }
    
    
    public void logOut() {
        putAccessToken("");
        putUserId("");
        putCurrentUser("");
    }
}
