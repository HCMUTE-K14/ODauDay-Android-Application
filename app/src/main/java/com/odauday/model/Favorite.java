package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 4/4/2018.
 */

public class Favorite {
    
    @SerializedName("user_id")
    @Expose
    private String userId;
    
    @SerializedName("property_id")
    private String propertyId;
    
    public String getUserId() {
        return userId;
    }
    
    public String getPropertyId() {
        return propertyId;
    }
}
