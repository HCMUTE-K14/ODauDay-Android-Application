package com.odauday.ui.user.profile.detail;

/**
 * Created by infamouSs on 5/30/18.
 */
public interface ProfileDetailContract {
    
    void onSuccessUpdateProfile();
    
    void onFailureUpdateProfile();
    
    void onSuccessReSendActivation();
    
    void onFailureReSendActivation();
    
    void onSuccessChangePassword();
    
    void onFailureChangePassword();
    
    void onSuccessGetAmount(long amount);
    
    void onFailureGetAmount();
    
    void loading(boolean isLoading);
}
