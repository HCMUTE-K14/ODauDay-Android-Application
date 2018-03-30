package com.odauday.data.remote.model.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseResponse;

/**
 * Created by infamouSs on 3/23/18.
 */

public class LoginResponse implements BaseResponse {
    
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    
    public LoginResponse() {

    }
    
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        LoginResponse that = (LoginResponse) o;
        
        return accessToken != null ? accessToken.equals(that.accessToken)
                  : that.accessToken == null;
    }
    
    @Override
    public int hashCode() {
        return accessToken != null ? accessToken.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
               "accessToken='" + accessToken + '\'' +
               '}';
    }
}
