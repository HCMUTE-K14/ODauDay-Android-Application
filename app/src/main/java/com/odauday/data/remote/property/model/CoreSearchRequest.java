package com.odauday.data.remote.property.model;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.NumberUtils;

/**
 * Created by infamouSs on 4/11/18.
 */

public class CoreSearchRequest {
    
    
    @SerializedName("end")
    @Expose
    private GeoLocation end;
    
    @SerializedName("center")
    @Expose
    private GeoLocation center;
    
    @SerializedName("bound")
    @Expose
    private GeoLocation[] bounds;
    
    @SerializedName("radius")
    @Expose
    private double radius;
    
    public CoreSearchRequest(GeoLocation center, GeoLocation end) {
        this.center = center;
        this.end = end;
        this.radius = calRadius();
    }
    
    public CoreSearchRequest(GeoLocation end, GeoLocation center,
              LatLngBounds bounds) {
        this.end = end;
        this.center = center;
        this.bounds = geoLocationBoundsFromLatLngBounds(bounds);
        this.radius = calRadius();
    }
    
    public GeoLocation[] getBounds() {
        return bounds;
    }
    
    public void setBounds(GeoLocation[] bounds) {
        this.bounds = bounds;
    }
    
    public GeoLocation getEnd() {
        return end;
    }
    
    public void setEnd(GeoLocation end) {
        this.end = end;
    }
    
    public GeoLocation getCenter() {
        return center;
    }
    
    public void setCenter(GeoLocation center) {
        this.center = center;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    private GeoLocation[] geoLocationBoundsFromLatLngBounds(LatLngBounds bounds) {
        return new GeoLocation[]{
                  GeoLocation.fromLatLng(bounds.southwest),
                  GeoLocation.fromLatLng(bounds.northeast)
        };
    }

    private double calRadius() {
        return NumberUtils.distanceBetween2Location(this.center, this.end);
    }
}
