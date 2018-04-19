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
    
    @SerializedName("latitude_ns")
    @Expose
    private float latitude_ns;
    
    @SerializedName("longitude_ns")
    @Expose
    private float longitude_ns;
    
    @SerializedName("latitude_sw")
    @Expose
    private float latitude_sw;
    
    @SerializedName("longitude_sw")
    @Expose
    private float longitude_sw;
    
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
    
    public float getLatitude_ns() {
        return latitude_ns;
    }
    
    public void setLatitude_ns(float latitude_ns) {
        this.latitude_ns = latitude_ns;
    }
    
    public float getLongitude_ns() {
        return longitude_ns;
    }
    
    public void setLongitude_ns(float longitude_ns) {
        this.longitude_ns = longitude_ns;
    }
    
    public float getLatitude_sw() {
        return latitude_sw;
    }
    
    public void setLatitude_sw(float latitude_sw) {
        this.latitude_sw = latitude_sw;
    }
    
    public float getLongitude_sw() {
        return longitude_sw;
    }
    
    public void setLongitude_sw(float longitude_sw) {
        this.longitude_sw = longitude_sw;
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
