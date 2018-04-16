package com.odauday.data.remote.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseRequest;

/**
 * Created by infamouSs on 3/23/18.
 */

public class ForgotPasswordRequest implements BaseRequest {
    
    @SerializedName("email")
    @Expose
    private String email;
    
    public ForgotPasswordRequest() {
    }
    
    public ForgotPasswordRequest(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "ForgotPasswordRequest{" +
               "email='" + email + '\'' +
               '}';
    }
}
