package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 3/30/2018.
 */

public class Phone {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    
}
