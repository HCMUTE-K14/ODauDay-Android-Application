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
    private double latitude;
    
    @SerializedName("longitude")
    @Expose
    private double longitude;
    
    @SerializedName("latitude_ns")
    @Expose
    private double latitude_ns;
    
    @SerializedName("longitude_ns")
    @Expose
    private double longitude_ns;
    
    @SerializedName("latitude_sw")
    @Expose
    private double latitude_sw;
    
    @SerializedName("longitude_sw")
    @Expose
    private double longitude_sw;
    
    @SerializedName("date_created")
    @Expose
    private String date_created;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public double getLatitude_ns() {
        return latitude_ns;
    }
    
    public void setLatitude_ns(double latitude_ns) {
        this.latitude_ns = latitude_ns;
    }
    
    public double getLongitude_ns() {
        return longitude_ns;
    }
    
    public void setLongitude_ns(double longitude_ns) {
        this.longitude_ns = longitude_ns;
    }
    
    public double getLatitude_sw() {
        return latitude_sw;
    }
    
    public void setLatitude_sw(double latitude_sw) {
        this.latitude_sw = latitude_sw;
    }
    
    public double getLongitude_sw() {
        return longitude_sw;
    }
    
    public void setLongitude_sw(double longitude_sw) {
        this.longitude_sw = longitude_sw;
    }
    
    public String getDate_created() {
        return date_created;
    }
    
    public void setDate_created(String date_created) {
        this.date_created = date_created;
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
