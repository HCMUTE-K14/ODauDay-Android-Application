package com.odauday.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.utils.ObjectUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infamouSs on 5/29/18.
 */
public class HistoryDetail {
    
    
    @SerializedName("id")
    @Expose
    private String propertyId;
    
    @SerializedName("date_created")
    @Expose
    private Date dateCreated;
    
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<>();
    
    @SerializedName("num_of_bedroom")
    @Expose
    private int numOfBedrooms;
    
    @SerializedName("num_of_bathroom")
    @Expose
    private int numOfBathrooms;
    
    @SerializedName("num_of_parking")
    @Expose
    private int numOfParkings;
    
    @SerializedName("address")
    @Expose
    private String address;
    
    @SerializedName("is_favorite")
    @Expose
    private boolean isFavorite;
    
    public HistoryDetail() {
    
    }
    
    public String getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public List<Image> getImages() {
        return images;
    }
    
    public void setImages(List<Image> images) {
        this.images = images;
    }
    
    public int getNumOfBedrooms() {
        return numOfBedrooms;
    }
    
    public void setNumOfBedrooms(int numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }
    
    public int getNumOfBathrooms() {
        return numOfBathrooms;
    }
    
    public void setNumOfBathrooms(int numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
    }
    
    public int getNumOfParkings() {
        return numOfParkings;
    }
    
    public void setNumOfParkings(int numOfParkings) {
        this.numOfParkings = numOfParkings;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    @Override
    public String toString() {
        return "HistoryDetail{" +
               "propertyId='" + propertyId + '\'' +
               ", dateCreated=" + dateCreated +
               ", images=" + images +
               ", address='" + address + '\'' +
               ", isFavorite=" + isFavorite +
               '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryDetail that = (HistoryDetail) o;
        return ObjectUtils.equals(propertyId, that.propertyId) &&
               ObjectUtils.equals(dateCreated, that.dateCreated);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(propertyId, dateCreated);
    }
}
