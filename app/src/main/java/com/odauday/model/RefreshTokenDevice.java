package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 6/5/2018.
 */

public class RefreshTokenDevice {
    
    @SerializedName("user_id")
    @Expose
    private String mUserId;
    
    @SerializedName("registration_token")
    @Expose
    private String mRegistrationToken;
    
    public RefreshTokenDevice(String userId, String registrationToken) {
        mUserId = userId;
        mRegistrationToken = registrationToken;
    }
    
    public RefreshTokenDevice() {
    
    }
    
    @Override
    public String toString() {
        return "RefreshTokenDevice{" +
               "mUserId='" + mUserId + '\'' +
               ", mRegistrationToken='" + mRegistrationToken + '\'' +
               '}';
    }
}
