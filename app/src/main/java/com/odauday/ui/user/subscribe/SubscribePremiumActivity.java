package com.odauday.ui.user.subscribe;

import com.odauday.R;
import com.odauday.databinding.ActivitySubscribePremiumBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.user.buypoint.BuyPointViewModel;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/31/18.
 */
public class SubscribePremiumActivity extends BaseMVVMActivity<ActivitySubscribePremiumBinding> {
    
    @Inject
    BuyPointViewModel mBuyPointViewModel;
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mBuyPointViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_subscribe_premium;
    }
}
