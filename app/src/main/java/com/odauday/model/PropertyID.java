package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 4/13/2018.
 */

public class PropertyID {
    
    @SerializedName("id")
    @Expose
    private String mPropertyId;
    
    public PropertyID(String propertyId) {
        mPropertyId = propertyId;
    }
    
    public String getPropertyId() {
        return mPropertyId;
    }
    
    public void setPropertyId(String propertyId) {
        mPropertyId = propertyId;
    }
}
