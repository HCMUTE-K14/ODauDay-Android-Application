package com.odauday.ui.addeditproperty;

/**
 * Created by infamouSs on 5/2/18.
 */
public interface AddEditPropertyContract {
    
    void onSuccessCreateProperty();
    
    void onErrorCreateProperty();
    
    void loading(boolean showing);
}
