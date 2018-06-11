package com.odauday.data.remote.premium.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.data.remote.BaseRequest;

/**
 * Created by infamouSs on 5/31/18.
 */
public class SubscribeRequest implements BaseRequest {
    
    @SerializedName("user_id")
    @Expose
    private String userId;
    
    @SerializedName("type_id")
    @Expose
    private String premiumId;
    
    @SerializedName("serial")
    @Expose
    private String serialNumber;
    
    @SerializedName("pin")
    @Expose
    private String pinNumber;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPremiumId() {
        return premiumId;
    }
    
    public void setPremiumId(String premiumId) {
        this.premiumId = premiumId;
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public String getPinNumber() {
        return pinNumber;
    }
    
    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }
    
    @Override
    public String toString() {
        return "SubscribeRequest{" +
               "userId='" + userId + '\'' +
               ", premiumId='" + premiumId + '\'' +
               ", serialNumber='" + serialNumber + '\'' +
               ", pinNumber='" + pinNumber + '\'' +
               '}';
    }
}
