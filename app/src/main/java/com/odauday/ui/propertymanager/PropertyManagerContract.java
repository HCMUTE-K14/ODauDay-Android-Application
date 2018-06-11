package com.odauday.ui.propertymanager;

import com.odauday.ui.base.BaseContract;

/**
 * Created by kunsubin on 4/18/2018.
 */

public interface PropertyManagerContract extends BaseContract {
    
    void onSuccessDeleteProperty(Object object);
    
    void onErrorDeleteProperty(Object object);
    
    void onSuccessMarkTheEnd(Object object);
    void onFailureMarkTheEnd(Object object);
}
