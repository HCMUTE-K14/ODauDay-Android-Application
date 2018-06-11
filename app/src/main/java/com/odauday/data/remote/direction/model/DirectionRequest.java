package com.odauday.data.remote.direction.model;

import com.odauday.data.remote.BaseRequest;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 5/12/18.
 */
public class DirectionRequest implements BaseRequest{
    
    private GeoLocation from;
    private GeoLocation to;
    private String mode;
    
    
    public DirectionRequest(GeoLocation from, GeoLocation to, String mode) {
        this.from = from;
        this.to = to;
        this.mode = mode;
    }
    
    public DirectionRequest() {
    
    }
    
    public GeoLocation getFrom() {
        return from;
    }
    
    public void setFrom(GeoLocation from) {
        this.from = from;
    }
    
    public GeoLocation getTo() {
        return to;
    }
    
    public void setTo(GeoLocation to) {
        this.to = to;
    }
    
    public String getMode() {
        return mode;
    }
    
    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public String formatFromLocation() {
        return TextUtils.formatGeoLocationForRequest(from);
    }
    
    public String formatToLocation() {
        return TextUtils.formatGeoLocationForRequest(to);
    }
    
    @Override
    public String toString() {
        return "DirectionRequest{" +
               "from=" + from +
               ", to=" + to +
               ", mode='" + mode + '\'' +
               '}';
    }
}
