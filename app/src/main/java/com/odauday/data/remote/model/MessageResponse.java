package com.odauday.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseResponse;

/**
 * Created by infamouSs on 3/23/18.
 */

public class MessageResponse implements BaseResponse {
    
    
    @SerializedName("message")
    @Expose
    private String message;
    
    
    public MessageResponse() {
    
    }
    
    public MessageResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "MessageResponse{" +
               "message='" + message + '\'' +
               '}';
    }
}
