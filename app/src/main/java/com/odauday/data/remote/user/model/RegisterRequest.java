package com.odauday.data.remote.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseRequest;

/**
 * Created by infamouSs on 3/23/18.
 */

public class RegisterRequest implements BaseRequest {
    
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("password")
    @Expose
    private String password;
    
    
    @SerializedName("display_name")
    @Expose
    private String displayName;
    
    public RegisterRequest() {

    }
    
    public RegisterRequest(String email, String password, String displayName) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return "RegisterRequest{" +
               "email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", displayName='" + displayName + '\'' +
               '}';
    }
}
