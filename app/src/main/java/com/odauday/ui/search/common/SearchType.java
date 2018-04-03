package com.odauday.ui.search.common;

import com.odauday.R;

/**
 * Created by infamouSs on 4/1/18.
 */

public enum SearchType {

    RENT(R.string.txt_rent, 0),
    BUY(R.string.txt_buy, 1),
    ALL(R.string.txt_all, 2);

    private final int mValue;
    private final int mResourceId;

    SearchType(int resourceId, int value) {
        this.mResourceId = resourceId;
        this.mValue = value;
    }

    public static SearchType getByValue(int value) {
        switch (value) {
            case 0:
                return SearchType.RENT;
            case 1:
                return SearchType.BUY;
            case 2:
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
