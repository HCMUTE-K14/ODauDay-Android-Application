package com.odauday.data.remote.search.model;

import com.odauday.utils.NumberUtils;

/**
 * Created by infamouSs on 4/11/18.
 */

public class CoreSearchRequest {
    
    private Location end;
    private Location center;
    private double radius;
    
    public CoreSearchRequest(Location center, Location end) {
        this.center = center;
        this.end = end;
        this.radius = calRadius();
    }
    
    public Location getTop() {
        return end;
    }
    
    public void setTop(Location top) {
        this.end = top;
    }
    
    public Location getCenter() {
        return center;
    }
    
    public void setCenter(Location center) {
        this.center = center;
    }
    
    public double getRadius() {
        return radius;
    }
    
    private double calRadius() {
        return NumberUtils.distanceBetween2Location(this.center, this.end);
    }
}
