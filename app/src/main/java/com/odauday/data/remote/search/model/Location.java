package com.odauday.data.remote.search.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 4/1/18.
 */

public class Location implements Parcelable {
    
    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }
        
        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    
    private String name = "";
    
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Location(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }
    
    protected Location(Parcel in) {
        latitude = in.readFloat();
        longitude = in.readFloat();
        name = in.readString();
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
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LatLng toLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }
    
    public android.location.Location toAndroidLocation() {
        android.location.Location location = new android.location.Location(this.name);
        location.setLatitude(this.latitude);
        location.setLongitude(this.longitude);
        
        return location;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Location location = (Location) o;
        
        if (Double.compare(location.latitude, latitude) != 0) {
            return false;
        }
        if (Double.compare(location.longitude, longitude) != 0) {
            return false;
        }
        return name != null ? name.equals(location.name) : location.name == null;
    }
    
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Location{" +
               "latitude=" + latitude +
               ", longitude=" + longitude +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(name);
    }
}
