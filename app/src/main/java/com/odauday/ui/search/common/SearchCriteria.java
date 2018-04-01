package com.odauday.ui.search.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by infamouSs on 4/1/18.
 */

public class SearchCriteria implements Parcelable {
    
    public static final Creator<SearchCriteria> CREATOR = new Creator<SearchCriteria>() {
        @Override
        public SearchCriteria createFromParcel(Parcel in) {
            return new SearchCriteria(in);
        }
        
        @Override
        public SearchCriteria[] newArray(int size) {
            return new SearchCriteria[size];
        }
    };
    @SerializedName("type")
    @Expose
    private int searchType;
    @SerializedName("location")
    @Expose
    private Point location;
    @SerializedName("radius")
    @Expose
    private double radius;
    @SerializedName("price")
    @Expose
    private MinMaxObject<Double> price;
    @SerializedName("size")
    @Expose
    private MinMaxObject<Double> size;
    @SerializedName("property_type")
    @Expose
    private List<String> propertyType;
    @SerializedName("bedrooms")
    @Expose
    private MinMaxObject<Integer> bedrooms;
    @SerializedName("bathrooms")
    @Expose
    private MinMaxObject<Integer> bathrooms;
    
    //    @SerializedName("tags")
    //    @Expose
    //    private List<Tag> tags;
    @SerializedName("parking")
    @Expose
    private MinMaxObject<Integer> parking;
    
    public SearchCriteria() {
    
    }
    
    public SearchCriteria(int searchType, Point location, double radius,
              MinMaxObject<Double> price,
              MinMaxObject<Double> size, List<String> propertyType,
              MinMaxObject<Integer> bedrooms,
              MinMaxObject<Integer> bathrooms,
              MinMaxObject<Integer> parking) {
        this.searchType = searchType;
        this.location = location;
        this.radius = radius;
        this.price = price;
        this.size = size;
        this.propertyType = propertyType;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.parking = parking;
    }
    
    protected SearchCriteria(Parcel in) {
        searchType = in.readInt();
        location = in.readParcelable(Point.class.getClassLoader());
        radius = in.readDouble();
        price = in.readParcelable(MinMaxObject.class.getClassLoader());
        size = in.readParcelable(MinMaxObject.class.getClassLoader());
        propertyType = in.createStringArrayList();
        bedrooms = in.readParcelable(MinMaxObject.class.getClassLoader());
        bathrooms = in.readParcelable(MinMaxObject.class.getClassLoader());
        parking = in.readParcelable(MinMaxObject.class.getClassLoader());
    }
    
    public int getSearchType() {
        return searchType;
    }
    
    public SearchCriteria setSearchType(int searchType) {
        this.searchType = searchType;
        
        return this;
    }
    
    public Point getLocation() {
        return location;
    }
    
    public SearchCriteria setLocation(Point location) {
        this.location = location;
        
        return this;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public SearchCriteria setRadius(double radius) {
        this.radius = radius;
        
        return this;
    }
    
    public MinMaxObject<Double> getPrice() {
        return price;
    }
    
    public SearchCriteria setPrice(MinMaxObject<Double> price) {
        this.price = price;
        
        return this;
    }
    
    public MinMaxObject<Double> getSize() {
        return size;
    }
    
    public SearchCriteria setSize(MinMaxObject<Double> size) {
        this.size = size;
        
        return this;
    }
    
    public List<String> getPropertyType() {
        return propertyType;
    }
    
    public SearchCriteria setPropertyType(List<String> propertyType) {
        this.propertyType = propertyType;
        
        return this;
    }
    
    public MinMaxObject<Integer> getBedrooms() {
        return bedrooms;
    }
    
    public SearchCriteria setBedrooms(MinMaxObject<Integer> bedrooms) {
        this.bedrooms = bedrooms;
        
        return this;
    }
    
    public MinMaxObject<Integer> getBathrooms() {
        return bathrooms;
    }
    
    public SearchCriteria setBathrooms(MinMaxObject<Integer> bathrooms) {
        this.bathrooms = bathrooms;
        
        return this;
    }
    
    public MinMaxObject<Integer> getParking() {
        return parking;
    }
    
    public SearchCriteria setParking(MinMaxObject<Integer> parking) {
        this.parking = parking;
        
        return this;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        
        parcel.writeInt(searchType);
        parcel.writeParcelable(location, i);
        parcel.writeDouble(radius);
        parcel.writeStringList(propertyType);
    }
}
