package com.odauday.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.odauday.utils.ObjectUtils;
import java.util.Date;

/**
 * Created by infamouSs on 4/18/18.
 */
public class Media implements Parcelable {
    
    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
    private String id;
    private String url;
    private Date dateCreated;
    
    public Media() {

    }
    
    public Media(String id, String url, Date dateCreated) {
        this.id = id;
        this.url = url;
        this.dateCreated = dateCreated;
    }
    
    protected Media(Parcel in) {
        id = in.readString();
        url = in.readString();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Media media = (Media) o;
        return ObjectUtils.equals(id, media.id) &&
               ObjectUtils.equals(url, media.url) &&
               ObjectUtils.equals(dateCreated, media.dateCreated);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, url, dateCreated);
    }
    
    @Override
    public String toString() {
        return "Media{" +
               "id='" + id + '\'' +
               ", url='" + url + '\'' +
               ", dateCreated=" + dateCreated +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        
        parcel.writeString(id);
        parcel.writeString(url);
    }
}
