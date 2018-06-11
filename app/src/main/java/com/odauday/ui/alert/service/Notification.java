package com.odauday.ui.alert.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by kunsubin on 5/17/2018.
 */

public class Notification {
    
    private String mId;
    
    @SerializedName("title")
    @Expose
    private String mTitle;
    
    @SerializedName("body")
    @Expose
    private String mBody;
    
    @SerializedName("user_id")
    @Expose
    private String mUserID;
    
    @SerializedName("property_id")
    @Expose
    private String mPropertyID;
    
    @SerializedName("image")
    @Expose
    private String mImage;
    
    @SerializedName("type")
    @Expose
    private String mType;
    
    private long mDate;
    
    public Notification() {
        this.mId="";
        this.mTitle="";
        this.mBody="";
        this.mUserID="";
        this.mPropertyID="";
        this.mImage="";
        this.mType="";
        this.mDate=0;
    }
    public Notification(String id,String title, String body, String userID, String propertyID,
        String image, String type,long date) {
        mId=id;
        mTitle = title;
        mBody = body;
        mUserID = userID;
        mPropertyID = propertyID;
        mImage = image;
        mType = type;
        mDate=date;
    }
    
    public String getId() {
        return mId;
    }
    
    public void setId(String id) {
        mId = id;
    }
    
    public String getTitle() {
        return mTitle;
    }
    
    public void setTitle(String title) {
        mTitle = title;
    }
    
    public String getBody() {
        return mBody;
    }
    
    public void setBody(String body) {
        mBody = body;
    }
    
    public String getUserID() {
        return mUserID;
    }
    
    public void setUserID(String userID) {
        mUserID = userID;
    }
    
    public String getPropertyID() {
        return mPropertyID;
    }
    
    public void setPropertyID(String propertyID) {
        mPropertyID = propertyID;
    }
    
    public String getImage() {
        return mImage;
    }
    
    public void setImage(String image) {
        mImage = image;
    }
    
    public String getType() {
        return mType;
    }
    
    public void setType(String type) {
        mType = type;
    }
    
    public long getDate() {
        return mDate;
    }
    
    public void setDate(long date) {
        mDate = date;
    }
    
    @Override
    public String toString() {
        return "RefreshTokenDevice{" +
               "mId='" + mId + '\'' +
               ", mTitle='" + mTitle + '\'' +
               ", mBody='" + mBody + '\'' +
               ", mUserID='" + mUserID + '\'' +
               ", mPropertyID='" + mPropertyID + '\'' +
               ", mImage='" + mImage + '\'' +
               ", mType='" + mType + '\'' +
               ", mDate=" + mDate +
               '}';
    }
}
