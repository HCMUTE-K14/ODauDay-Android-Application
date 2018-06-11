package com.odauday.ui.user.subscribe;

/**
 * Created by infamouSs on 6/1/18.
 */
public interface SubscribePremiumContract {
    
    void onSuccessSubscribe();
    
    void onFailureSubscribe();
    
    void showProgress();
    
    void hideProgress();
}
