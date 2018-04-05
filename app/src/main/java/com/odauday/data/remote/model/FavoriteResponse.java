package com.odauday.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.model.Property;
import java.util.List;

/**
 * Created by kunsubin on 4/4/2018.
 */

public class FavoriteResponse {
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("favorites")
    @Expose
    private List<Property> mProperties;
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public List<Property> getProperties() {
        return mProperties;
    }
    
    public void setProperties(List<Property> properties) {
        mProperties = properties;
    }
    
    @Override
    public String toString() {
        return "FavoriteResponse{" +
               "id='" + id + '\'' +
               ", mProperties=" + mProperties +
               '}';
    }
}
