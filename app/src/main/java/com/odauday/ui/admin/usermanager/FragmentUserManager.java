package com.odauday.ui.admin.usermanager;

import android.os.Bundle;
import com.odauday.R;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by kunsubin on 5/4/2018.
 */

public class FragmentUserManager extends BaseMVVMFragment {
    
    public static final String TAG=FragmentUserManager.class.getSimpleName();
    
    public static FragmentUserManager newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentUserManager fragment = new FragmentUserManager();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_manager;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    @Override
    protected void injectDI() {
    
    }
}
