package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 4/9/2018.
 */

public class Search {
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("latitude")
    @Expose
    private float latitude;
    
    @SerializedName("longitude")
    @Expose
    private float longitude;
    
    @SerializedName("date_created")
    @Expose
    private String date_created;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public float getLatitude() {
        return latitude;
    }
    
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    
    public float getLongitude() {
        return longitude;
    }
    
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
    public String getDate_created() {
        return date_created;
    }
    
    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Search{" +
               "id='" + id + '\'' +
               ", latitude='" + latitude + '\'' +
               ", longitude='" + longitude + '\'' +
               ", date_created='" + date_created + '\'' +
               '}';
    }
}
