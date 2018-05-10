package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/25/18.
 */
public class MyEmail {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("property_id")
    @Expose String propertyId;
    
    public MyEmail() {
    
    }
    
    public MyEmail(String id, String email) {
        this.id = id;
        this.email = email;
    }
    
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
        return ObjectUtils.equals(id, myEmail.id) &&
               ObjectUtils.equals(email, myEmail.email);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, email);
    }
    
    @Override
    public String toString() {
        return "MyEmail{" +
               "id='" + id + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
