package com.odauday.ui.search.common;

/**
 * Created by infamouSs on 6/2/18.
 */
public enum SortType {
    
    NEWEST(0),
    LOWEST_PRICE(1),
    HIGHEST_PRICE(2);
    
    int value;
    
    SortType(int value) {
        this.value = value;
    }
    
    public static SortType getSortType(int value) {
        switch (value) {
            case 0:
                return NEWEST;
            case 1:
                return LOWEST_PRICE;
            case 2:
                return HIGHEST_PRICE;
            default:
                return null;
        }
    }
}
