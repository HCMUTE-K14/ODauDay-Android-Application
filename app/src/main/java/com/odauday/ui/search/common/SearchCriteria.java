package com.odauday.ui.search.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.odauday.model.Tag;
import com.odauday.ui.search.navigation.PropertyType;
import java.util.ArrayList;
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
    @SerializedName("price")
    @Expose
    private MinMaxObject<Integer> price;
    @SerializedName("size")
    @Expose
    private MinMaxObject<Integer> size;
    @SerializedName("property_type")
    @Expose
    private List<PropertyType> propertyTypes;
    @SerializedName("bedrooms")
    @Expose
    private MinMaxObject<Integer> bedrooms;
    @SerializedName("bathrooms")
    @Expose
    private MinMaxObject<Integer> bathrooms;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags;
    @SerializedName("parking")
    @Expose
    private MinMaxObject<Integer> parking;
    private SearchCriteriaDisplay display;
    
    public SearchCriteria() {
        this.searchType = SearchType.ALL.getValue();
        this.price = new MinMaxObject<>(-1, -1);
        this.size = new MinMaxObject<>(-1, -1);
        this.bedrooms = new MinMaxObject<>(-1, -1);
        this.bathrooms = new MinMaxObject<>(-1, -1);
        this.parking = new MinMaxObject<>(-1, 0);
        this.propertyTypes = new ArrayList<>();
        this.display = new SearchCriteriaDisplay();
    }
    
    protected SearchCriteria(Parcel in) {
        searchType = in.readInt();
        price = in.readParcelable(MinMaxObject.class.getClassLoader());
        size = in.readParcelable(MinMaxObject.class.getClassLoader());
        bedrooms = in.readParcelable(MinMaxObject.class.getClassLoader());
        bathrooms = in.readParcelable(MinMaxObject.class.getClassLoader());
        tags = in.createTypedArrayList(Tag.CREATOR);
        parking = in.readParcelable(MinMaxObject.class.getClassLoader());
        display = in.readParcelable(SearchCriteriaDisplay.class.getClassLoader());
    }
    
    public int getSearchType() {
        return searchType;
    }
    
    public SearchCriteria setSearchType(int searchType) {
        this.searchType = searchType;
        
        return this;
    }
    
    public MinMaxObject<Integer> getPrice() {
        return price;
    }
    
    public void setPrice(MinMaxObject<Integer> price) {
        this.price = price;
    }
    
    public MinMaxObject<Integer> getSize() {
        return size;
    }
    
    public void setSize(MinMaxObject<Integer> size) {
        this.size = size;
    }
    
    public List<Tag> getTags() {
        return tags;
    }
    
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    public List<PropertyType> getPropertyType() {
        return propertyTypes;
    }
    
    public SearchCriteria setPropertyType(List<PropertyType> propertyType) {
        this.propertyTypes = propertyType;
        
        return this;
    }
    
    public SearchCriteria setPropertyTypeByListInteger(List<Integer> propertyType) {
        List<PropertyType> list = new ArrayList<>();
        for (int i : propertyType) {
            list.add(PropertyType.getById(i));
        }
        return this.setPropertyType(list);
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
    
    public SearchCriteriaDisplay getDisplay() {
        return display;
    }
    
    public void setDisplay(SearchCriteriaDisplay display) {
        this.display = display;
    }
    
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        
        parcel.writeInt(searchType);
        parcel.writeParcelable(price, i);
        parcel.writeParcelable(size, i);
        parcel.writeParcelable(bedrooms, i);
        parcel.writeParcelable(bathrooms, i);
        parcel.writeTypedList(tags);
        parcel.writeParcelable(parking, i);
        parcel.writeParcelable(display, i);
    }
    
    @Override
    public String toString() {
        return "SearchCriteria{" +
               "searchType=" + searchType +
               ", price=" + price +
               ", size=" + size +
               ", propertyTypes=" + propertyTypes +
               ", bedrooms=" + bedrooms +
               ", bathrooms=" + bathrooms +
               ", tags=" + tags +
               ", parking=" + parking +
               '}';
    }
}
