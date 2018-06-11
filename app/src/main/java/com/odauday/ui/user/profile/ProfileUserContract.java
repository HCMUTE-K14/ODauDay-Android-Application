package com.odauday.ui.user.profile;

/**
 * Created by infamouSs on 5/29/18.
 */
public interface ProfileUserContract {
    
    void onSuccessClearHistory();
    
    void onFailureClearHistory();
    
    void onSuccessUpdateProfile();
    
    void onFailureUpdateProfile();
    
    void loading(boolean isLoading);
}
