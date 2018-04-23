package com.odauday.ui.search.common;

import com.odauday.R;

/**
 * Created by infamouSs on 4/1/18.
 */

public enum SearchType {
    ALL(R.string.txt_all, 0),
    BUY(R.string.txt_buy, 1),
    RENT(R.string.txt_rent, 2);
    
    private final int mValue;
    private final int mResourceId;
    
    SearchType(int resourceId, int value) {
        this.mResourceId = resourceId;
        this.mValue = value;
    }
    
    public static SearchType getByValue(int value) {
        switch (value) {
            case 0:
                return SearchType.ALL;
            case 1:
                return SearchType.BUY;
            case 2:
                return SearchType.RENT;
            default:
                return SearchType.ALL;
        }
    }
    
    public int getValue() {
        return mValue;
    }
    
    public int getResourceId() {
        return mResourceId;
    }
}
