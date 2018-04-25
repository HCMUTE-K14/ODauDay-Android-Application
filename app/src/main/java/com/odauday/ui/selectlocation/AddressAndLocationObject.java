package com.odauday.ui.selectlocation;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by infamouSs on 4/25/18.
 */
public class AddressAndLocationObject implements Parcelable {
    
    private String address;
    private LatLng location;
    
    public AddressAndLocationObject() {
    
    }
    
    public AddressAndLocationObject(String address, LatLng location) {
        this.address = address;
        this.location = location;
    }
    
    protected AddressAndLocationObject(Parcel in) {
        address = in.readString();
        location = in.readParcelable(LatLng.class.getClassLoader());
    }
    
    public static final Creator<AddressAndLocationObject> CREATOR = new Creator<AddressAndLocationObject>() {
        @Override
        public AddressAndLocationObject createFromParcel(Parcel in) {
            return new AddressAndLocationObject(in);
        }
        
        @Override
        public AddressAndLocationObject[] newArray(int size) {
            return new AddressAndLocationObject[size];
        }
    };
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public LatLng getLocation() {
        return location;
    }
    
    public void setLocation(LatLng location) {
        this.location = location;
    }
    
    @Override
    public String toString() {
        return "AddressAndLocationObject{" +
               "address='" + address + '\'' +
               ", location=" + location +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(address);
        dest.writeParcelable(location, flags);
    }
}
