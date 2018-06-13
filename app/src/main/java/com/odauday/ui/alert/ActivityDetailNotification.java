package com.odauday.ui.alert;

import com.odauday.R;
import com.odauday.databinding.ActivityDetailNotificationBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by kunsubin on 6/12/2018.
 */

public class ActivityDetailNotification extends BaseMVVMActivity<ActivityDetailNotificationBinding> {
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_notification;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
}
