package com.odauday.ui.admin.giftmanager;

import android.os.Bundle;
import com.odauday.R;
import com.odauday.databinding.FragmentGiftManagerBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by kunsubin on 5/4/2018.
 */

public class FragmentGiftManager extends BaseMVVMFragment<FragmentGiftManagerBinding> {
    public static final String TAG=FragmentGiftManager.class.getSimpleName();
    
    public static FragmentGiftManager newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentGiftManager fragment = new FragmentGiftManager();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_gift_manager;
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
