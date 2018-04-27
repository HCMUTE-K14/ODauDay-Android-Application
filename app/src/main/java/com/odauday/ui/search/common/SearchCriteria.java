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
        this.price =
                  this.size = new MinMaxObject<>(-1, -1);
        this.bedrooms = new MinMaxObject<>(-1, -1);
        this.bathrooms = new MinMaxObject<>(-1, -1);
        this.parking = new MinMaxObject<>(-1, -1);
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

    public void setBedrooms(MinMaxObject<Integer> bedrooms) {
        this.bedrooms = bedrooms;
    }
    
    public MinMaxObject<Integer> getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(MinMaxObject<Integer> bathrooms) {
        this.bathrooms = bathrooms;
    }
    
    public MinMaxObject<Integer> getParking() {
        return parking;
    }

    public void setParking(MinMaxObject<Integer> parking) {
        this.parking = parking;
    }

    public SearchCriteriaDisplay getDisplay() {
        return display;
    }
    
    public void setDisplay(SearchCriteriaDisplay display) {
        this.display = display;
    }


    @SuppressWarnings("unchecked")
    public SearchCriteria normalize() {
        SearchCriteria searchCriteria = this;
        
        SearchCriteria normalize = new SearchCriteria();
        MinMaxObject<Integer> _price = null;
        MinMaxObject<Integer> _size = null;
        MinMaxObject<Integer> _bedrooms = null;
        MinMaxObject<Integer> _bathrooms = null;
        MinMaxObject<Integer> _parkings = null;
        
        if (searchCriteria.price != null) {
            _price = (MinMaxObject<Integer>) searchCriteria.price.normalize();
        }
        
        if (searchCriteria.size != null) {
            _size = (MinMaxObject<Integer>) searchCriteria.size.normalize();
        }
        
        if (searchCriteria.bedrooms != null) {
            _bedrooms = (MinMaxObject<Integer>) searchCriteria.bedrooms
                      .normalize();
        }
        
        if (searchCriteria.bathrooms != null) {
            _bathrooms = (MinMaxObject<Integer>) searchCriteria.bathrooms
                      .normalize();
        }
        
        if (searchCriteria.parking != null) {
            String minValueStr = String.valueOf(searchCriteria.parking.getMin());
            String maxValueStr = String.valueOf(searchCriteria.parking.getMax());
            
            int minParking = Integer.valueOf(minValueStr.equals("null") ? "0" : minValueStr);
            int maxParking = Integer.valueOf(maxValueStr.equals("null") ? "0" : maxValueStr);
            
            if (minParking == -1) {
                _parkings = null;
            } else {
                _parkings = new MinMaxObject<>(minParking, null);
            }
        }
        
        List<Tag> _tags;
        
        if (searchCriteria.getTags() == null) {
            _tags = null;
        } else {
            if (searchCriteria.getTags().isEmpty()) {
                _tags = null;
            } else {
                _tags = new ArrayList<>();
                for (Tag tag : searchCriteria.getTags()) {
                    _tags.add(new Tag(tag.getId(), null));
                }
            }
        }
        
        List<PropertyType> _propertyTypes;
        
        if (searchCriteria.getPropertyType() == null ||
            searchCriteria.getPropertyType().isEmpty()) {
            _propertyTypes = null;
        } else {
            _propertyTypes = new ArrayList<>(searchCriteria.getPropertyType());
        }
        
        normalize.setSearchType(searchCriteria.getSearchType());
        normalize.setPrice(_price);
        normalize.setSize(_size);
        normalize.setBedrooms(_bedrooms);
        normalize.setBathrooms(_bathrooms);
        normalize.setParking(_parkings);
        normalize.setTags(_tags);
        normalize.setPropertyType(_propertyTypes);
        normalize.setDisplay(null);
        
        return normalize;
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
