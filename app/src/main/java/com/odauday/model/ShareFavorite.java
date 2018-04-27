package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by kunsubin on 4/14/2018.
 */

public class ShareFavorite {
    
    @SerializedName("email_friend")
    @Expose
    private String email_friend;
    
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("email_from")
    @Expose
    private String email_from;
    
    @SerializedName("properties")
    @Expose
    private List<Property> mProperties;
    
    public String getEmail_friend() {
        return email_friend;
    }
    
    public void setEmail_friend(String email_friend) {
        this.email_friend = email_friend;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail_from() {
        return email_from;
    }
    
    public void setEmail_from(String email_from) {
        this.email_from = email_from;
    }
    
    public List<Property> getProperties() {
        return mProperties;
    }
    
    public void setProperties(List<Property> properties) {
        mProperties = properties;
    }
    
    @Override
    public String toString() {
        return "ShareFavorite{" +
               "email_friend='" + email_friend + '\'' +
               ", name='" + name + '\'' +
               ", email_from='" + email_from + '\'' +
               ", mProperties=" + mProperties +
               '}';
    }
}
