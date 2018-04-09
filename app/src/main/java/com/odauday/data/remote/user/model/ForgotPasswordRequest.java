package com.odauday.data.remote.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 3/23/18.
 */

public class ForgotPasswordRequest {

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
