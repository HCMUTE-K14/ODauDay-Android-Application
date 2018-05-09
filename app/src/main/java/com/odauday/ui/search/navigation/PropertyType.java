package com.odauday.ui.search.navigation;

import com.google.gson.annotations.SerializedName;
import com.odauday.R;
import com.odauday.model.Category;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/3/2018.
 */
public enum PropertyType {
    @SerializedName("0")
    APARTMENT(0, R.string.property_type_apartment),
    
    @SerializedName("1")
    LEVEL_1(1, R.string.property_type_level_1),
    
    @SerializedName("2")
    LEVEL_2(2, R.string.property_type_level_2),
    
    @SerializedName("3")
    LEVEL_3(3, R.string.property_type_level_3),
    
    @SerializedName("4")
    LEVEL_4(4, R.string.property_type_level_4),
    
    @SerializedName("5")
    LAND(5, R.string.property_type_land);
    
    private int mId;
    private int mDisplayStringResource;
    
    PropertyType(int id, int displayString) {
        this.mId = id;
        this.mDisplayStringResource = displayString;
    }
    
    public static PropertyType getById(int id) {
        switch (id) {
            case 0:
                return PropertyType.APARTMENT;
            case 1:
                return PropertyType.LEVEL_1;
            case 2:
                return PropertyType.LEVEL_2;
            case 3:
                return PropertyType.LEVEL_3;
            case 4:
                return PropertyType.LEVEL_4;
            case 5:
                return PropertyType.LAND;
            default:
                return PropertyType.APARTMENT;
        }
        
    }
    
    public static List<Integer> convertToArrayInt(List<PropertyType> list) {
        List<Integer> integers = new ArrayList<>();
        if (list == null) {
            return integers;
        }
        if (list.isEmpty()) {
            return integers;
        }
        for (PropertyType propertyType : list) {
            integers.add(propertyType.getId());
        }
        return integers;
    }
    
    
    public static List<PropertyType> convertCategoriesToPropertyType(List<Category> categories) {
        List<PropertyType> propertyTypes = new ArrayList<>();
        for (Category category : categories) {
            propertyTypes.add(PropertyType.getById(Integer.valueOf(category.getId())));
        }
        
        return propertyTypes;
    }
    
    public int getId() {
        return mId;
    }
    
    public void setId(int id) {
        mId = id;
    }
    
    public int getDisplayStringResource() {
        return mDisplayStringResource;
    }
    
    public void setDisplayStringResource(int displayStringResource) {
        mDisplayStringResource = displayStringResource;
    }
}
