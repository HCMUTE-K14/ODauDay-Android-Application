package com.odauday.ui.user.buypoint;

import com.odauday.model.Premium;
import java.util.List;

/**
 * Created by infamouSs on 5/31/18.
 */
public interface BuyPointContract {
    
    void onSuccessGetPremium(List<Premium> premiums);
    
    void onFailureGetPremium();
    
    void showProgress();
    
    void hideProgress();
}
