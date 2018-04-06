package com.odauday.ui.base;

/**
 * Created by infamouSs on 3/27/18.
 */

public interface BaseContract {
    
    void loading(boolean isLoading);
    
    void onSuccess(Object object);
    
    void onFailure(Exception ex);
}
