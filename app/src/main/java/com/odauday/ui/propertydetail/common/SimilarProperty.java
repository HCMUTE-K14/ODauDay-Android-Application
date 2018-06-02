package com.odauday.ui.propertydetail.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.model.Image;
import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 5/18/18.
 */
public class SimilarProperty {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("address")
    @Expose
    private String address;
    
    @SerializedName("price")
    @Expose
    private double price;
    
    @SerializedName("num_of_bedroom")
    @Expose
    private int numOfBedroom;
    
    @SerializedName("num_of_bathroom")
    @Expose
    private int numOfBathroom;
    
    @SerializedName("num_of_parking")
    @Expose
    private int numOfParking;
    
    @SerializedName("land_size")
    @Expose
    private long size;
    
    @SerializedName("image")
    @Expose
    private Image image = new Image();
    
    @SerializedName("isFavorited")
    @Expose
    private boolean isFavorite;
    
    public SimilarProperty() {
    
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getNumOfBedroom() {
        return numOfBedroom;
    }
    
    public void setNumOfBedroom(int numOfBedroom) {
        this.numOfBedroom = numOfBedroom;
    }
    
    public int getNumOfBathroom() {
        return numOfBathroom;
    }
    
    public void setNumOfBathroom(int numOfBathroom) {
        this.numOfBathroom = numOfBathroom;
    }
    
    public int getNumOfParking() {
        return numOfParking;
    }
    
    public void setNumOfParking(int numOfParking) {
        this.numOfParking = numOfParking;
    }
    
    public Image getImage() {
        return image;
    }
    
    public void setImage(Image image) {
        this.image = image;
    }
    
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimilarProperty that = (SimilarProperty) o;
        return ObjectUtils.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id);
    }
}
