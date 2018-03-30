package com.odauday.data.remote.model.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 2/27/18.
 */

public class NormalAuthRequest extends AbstractAuthRequest {
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("password")
    @Expose
    private String password;
    
    public NormalAuthRequest() {
        super(LoginType.NORMAL);
    }
    
    public NormalAuthRequest(String email, String password) {
        super(LoginType.NORMAL);
        this.email = email;
        this.password = password;
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
    
    @Override
    public String toString() {
        return "NormalAuthRequest{" +
               "email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
