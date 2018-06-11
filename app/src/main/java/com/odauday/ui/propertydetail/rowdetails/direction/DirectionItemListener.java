package com.odauday.ui.propertydetail.rowdetails.direction;

import com.odauday.ui.propertydetail.common.DirectionLocation;

/**
 * Created by infamouSs on 5/12/18.
 */
public interface DirectionItemListener {
    
    void onSuccess(DirectionLocation directionLocation);
    
    void onError(DirectionLocation directionLocation);
    
    void showProgress();
    
    void hideProgress();
    
    void doFinally();
}
