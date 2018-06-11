package com.odauday.data.remote.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseRequest;

/**
 * Created by infamouSs on 3/23/18.
 */

public class ChangePasswordRequest implements BaseRequest {
    
    
    @SerializedName("id")
    @Expose
    private String userId;
    
    @SerializedName("old_password")
    @Expose
    private String oldPassword;
    
    @SerializedName("new_password")
    @Expose
    private String newPassword;
    
    
    public ChangePasswordRequest() {
    }
    
    public ChangePasswordRequest(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
    
    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
               "userId='" + userId + '\'' +
               ", oldPassword='" + oldPassword + '\'' +
               ", newPassword='" + newPassword + '\'' +
               '}';
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
