package com.odauday.model;

import com.odauday.R;
import com.odauday.config.AppConfig;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.utils.TextUtils;
import java.util.List;

/**
 * Created by infamouSs on 5/7/18.
 */
public class PropertyDetail {
    
    private List<Image> images;
    private int type;
    private double price;
    private String address;
    private String contactTime;
    private int numOfBedrooms;
    private int numOfBathrooms;
    private int numOfParkings;
    private double size;
    private List<Category> categories;
    private List<Tag> tags;
    private String description;
    private GeoLocation location;
    
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<Image> getImages() {
        return images;
    }
    
    public void setImages(List<Image> images) {
        this.images = images;
    }
    
    public String getContactTime() {
        return contactTime;
    }
    
    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getTextType() {
        return type == 1 ? R.string.txt_buy_uppercast : R.string.txt_rent_upppercast;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getTextPrice() {
        return TextUtils.formatIntToCurrency((float) getPrice() *
                                             AppConfig.RATE_VND);
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
    
    public double getSize() {
        return size;
    }
    
    public void setSize(double size) {
        this.size = size;
    }
    
    public List<Category> getCategories() {
        return categories;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public List<Tag> getTags() {
        return tags;
    }
    
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    public GeoLocation getLocation() {
        return location;
    }
    
    public void setLocation(GeoLocation location) {
        this.location = location;
    }
}
