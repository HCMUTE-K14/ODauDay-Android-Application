package com.odauday.ui.confirmproperty;

import com.odauday.R;
import com.odauday.databinding.ActivityConfirmPropertyBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by kunsubin on 4/26/2018.
 */

public class ActivityConfirmProperty extends BaseMVVMActivity<ActivityConfirmPropertyBinding> {
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_property;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {

    }
}
