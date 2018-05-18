package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/25/18.
 */
public class MyEmail implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("property_id")
    @Expose
    String propertyId;
    
    public MyEmail() {
    
    }
    
    public MyEmail(String id, String email) {
        this.id = id;
        this.email = email;
    }
    
    public MyEmail(String email) {
        this.email = email;
    }
    
    protected MyEmail(Parcel in) {
        id = in.readString();
        email = in.readString();
        propertyId = in.readString();
    }
    
    public static final Creator<MyEmail> CREATOR = new Creator<MyEmail>() {
        @Override
        public MyEmail createFromParcel(Parcel in) {
            return new MyEmail(in);
        }
        
        @Override
        public MyEmail[] newArray(int size) {
            return new MyEmail[size];
        }
    };
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
        MyEmail myEmail = (MyEmail) o;
        return ObjectUtils.equals(email, myEmail.email);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(email);
    }
    
    @Override
    public String toString() {
        return "MyEmail{" +
               "id='" + id + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(propertyId);
    }
}
