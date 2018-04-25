package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/25/18.
 */
public class MyPhone {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    
    public MyPhone() {
    
    }
    
    public MyPhone(String id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }
    
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
}
