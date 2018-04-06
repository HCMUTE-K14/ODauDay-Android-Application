package com.odauday.data.remote.model.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 3/23/18.
 */

public class ChangePasswordRequest {


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
}
