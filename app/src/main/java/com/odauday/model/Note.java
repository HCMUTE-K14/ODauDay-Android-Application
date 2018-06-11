package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 5/16/18.
 */
public class Note implements Parcelable {
    
    
    @SerializedName("user_id")
    @Expose
    private String userId;
    
    @SerializedName("property_id")
    @Expose
    private String propertyId;
    
    @SerializedName("content")
    @Expose
    private String content;
    
    
    public Note() {
    
    }
    
    public Note(String userId, String propertyId, String content) {
        this.userId = userId;
        this.content = content;
        this.propertyId = propertyId;
    }
    
    protected Note(Parcel in) {
        userId = in.readString();
        propertyId = in.readString();
        content = in.readString();
    }
    
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }
        
        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeString(userId);
        dest.writeString(propertyId);
        dest.writeString(content);
    }
}
