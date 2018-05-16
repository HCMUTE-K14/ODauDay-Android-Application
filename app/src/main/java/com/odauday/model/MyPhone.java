package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/25/18.
 */
public class MyPhone implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    
    @SerializedName("property_id")
    @Expose
    String propertyId;
    
    
    public MyPhone() {
    
    }
    
    public MyPhone(String id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }
    
    protected MyPhone(Parcel in) {
        id = in.readString();
        phoneNumber = in.readString();
        propertyId = in.readString();
    }
    
    public static final Creator<MyPhone> CREATOR = new Creator<MyPhone>() {
        @Override
        public MyPhone createFromParcel(Parcel in) {
            return new MyPhone(in);
        }
        
        @Override
        public MyPhone[] newArray(int size) {
            return new MyPhone[size];
        }
    };
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyPhone myPhone = (MyPhone) o;
        return ObjectUtils.equals(id, myPhone.id);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id);
    }
    
    @Override
    public String toString() {
        return "MyPhone{" +
               "id='" + id + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(id);
        dest.writeString(phoneNumber);
        dest.writeString(propertyId);
    }
}
