package com.odauday.data.remote.model;

/**
 * Created by infamouSs on 2/27/18.
 */

public class NormalAuthRequest implements AuthenticationRequest {
    
    private String username;
    private String password;
    
    public NormalAuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
