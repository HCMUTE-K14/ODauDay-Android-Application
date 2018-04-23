package com.odauday.data.remote.property.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/1/18.
 */

public class GeoLocation implements Parcelable {
    
    public static final Creator<GeoLocation> CREATOR = new Creator<GeoLocation>() {
        @Override
        public GeoLocation createFromParcel(Parcel in) {
            return new GeoLocation(in);
        }
        
        @Override
        public GeoLocation[] newArray(int size) {
            return new GeoLocation[size];
        }
    };
    @SerializedName("latitude")
    @Expose
    private double latitude;
    
    @SerializedName("longitude")
    @Expose
    private double longitude;
    
    private String name = "";
    
    public GeoLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public GeoLocation(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }
    
    protected GeoLocation(Parcel in) {
        latitude = in.readFloat();
        longitude = in.readFloat();
        name = in.readString();
    }
    
    public static GeoLocation fromLatLng(LatLng latLng) {
        return new GeoLocation(latLng.latitude, latLng.longitude);
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
        GeoLocation that = (GeoLocation) o;
        return Double.compare(that.latitude, latitude) == 0 &&
               Double.compare(that.longitude, longitude) == 0;
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(latitude, longitude);
    }
    
    @Override
    public String toString() {
        return "GeoLocation{" +
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
