package com.odauday.data.remote.direction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseResponse;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 5/12/18.
 */
public class DirectionResponse implements BaseResponse{
    
    @SerializedName("duration")
    @Expose
    private long duration;
    
    
    @SerializedName("distance")
    @Expose
    private long distance;
    
    
    public DirectionResponse() {
    
    }
    
    public DirectionResponse(long duration, long distance) {
        this.duration = duration;
        this.distance = distance;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public long getDistance() {
        return distance;
    }
    
    public void setDistance(long distance) {
        this.distance = distance;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DirectionResponse that = (DirectionResponse) o;
        return duration == that.duration &&
               distance == that.distance;
    }
    
    @Override
    public int hashCode() {
        return ObjectUtils.hash(duration, distance);
    }
    
    @Override
    public String toString() {
        return "DirectionResponse{" +
               "duration=" + duration +
               ", distance=" + distance +
               '}';
    }
}
