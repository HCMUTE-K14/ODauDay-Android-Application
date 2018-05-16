package com.odauday.ui.propertydetail;

import com.odauday.model.PropertyDetail;

/**
 * Created by infamouSs on 5/16/18.
 */
public interface PropertyDetailContract {
    
    void onSuccessGetDetailProperty(PropertyDetail propertyDetail);
    
    void onFailureGetDetailProperty(Throwable ex);
    
    void showProgressBar();
    
    void hideProgressBar();
}
