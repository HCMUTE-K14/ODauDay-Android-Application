package com.odauday.ui.search.navigation;

/**
 * Created by infamouSs on 4/2/18.
 */

public enum FilterOption {
    PRICE(1, "FILTER_PRICE"),
    SIZE(2, "FILTER_SIZE"),
    BEDROOMS(3, "FILTER_BEDROOM"),
    BATHROOMS(4, "FILTER_BATHROOM"),
    PARKING(5, "FILTER_PARKING"),
    PROPERTY_TYPE(6, "PROPERTY_TYPE"),
    TAGS(7, "TAGS");
    
    private final int mRequestCode;
    private final String mTag;
    
    FilterOption(int requestCode, String tag) {
        this.mRequestCode = requestCode;
        this.mTag = tag;
    }
    
    public static FilterOption getByRequestCode(int requestCode) {
        switch (requestCode) {
            case 1:
                return FilterOption.PRICE;
            case 2:
                return FilterOption.SIZE;
            case 3:
                return FilterOption.BEDROOMS;
            case 4:
                return FilterOption.BATHROOMS;
            case 5:
                return FilterOption.PARKING;
            case 6:
                return FilterOption.PROPERTY_TYPE;
            case 7:
                return FilterOption.TAGS;
            default:
                throw new IllegalArgumentException("Not found FilterOption with request code");
        }
    }
    
    public int getRequestCode() {
        return mRequestCode;
    }
    
    public String getTag() {
        return mTag;
    }
}
