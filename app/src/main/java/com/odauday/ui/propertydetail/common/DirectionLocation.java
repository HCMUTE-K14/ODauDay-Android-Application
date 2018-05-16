package com.odauday.ui.propertydetail.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 5/12/18.
 */
public class DirectionLocation {
    
    @SerializedName("arrival_time")
    @Expose
    private long arrivalTime;
    
    @SerializedName("to_location")
    @Expose
    private GeoLocation toLocation;
    
    
    private GeoLocation fromLocation;
    
    @SerializedName("label")
    @Expose
    private String label;
    
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    
    @SerializedName("distance")
    @Expose
    private double distance;
    
    
    @SerializedName("mode")
    @Expose
    private DirectionMode mode;
    
    public DirectionLocation() {
    
    }
    
    public GeoLocation getFromLocation() {
        return fromLocation;
    }
    
    public void setFromLocation(GeoLocation fromLocation) {
        this.fromLocation = fromLocation;
    }
    
    public DirectionMode getMode() {
        return mode;
    }
    
    public void setMode(DirectionMode mode) {
        this.mode = mode;
    }
    
    public long getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public GeoLocation getToLocation() {
        return toLocation;
    }
    
    public void setToLocation(GeoLocation toLocation) {
        this.toLocation = toLocation;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public String getFullAddress() {
        return fullAddress;
    }
    
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DirectionLocation that = (DirectionLocation) o;
        return ObjectUtils.equals(toLocation, that.toLocation) &&
               mode == that.mode;
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(toLocation, mode);
    }
    
    @Override
    public String toString() {
        return "DirectionLocation{" +
               "arrivalTime=" + arrivalTime +
               ", toLocation=" + toLocation +
               ", label='" + label + '\'' +
               ", fullAddress='" + fullAddress + '\'' +
               ", distance=" + distance +
               ", mode=" + mode.getApiString() +
               '}';
    }
}
