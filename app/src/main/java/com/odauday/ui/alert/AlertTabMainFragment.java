package com.odauday.ui.alert;

import android.os.Bundle;
import com.odauday.R;
import com.odauday.databinding.FragmentAlertTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by infamouSs on 3/31/18.
 */

public class AlertTabMainFragment extends BaseMVVMFragment<FragmentAlertTabMainBinding> {
    
    //====================== Variable =========================//
    
    public static final String TAG = NavigationTab.ALERT_TAB.getNameTab();
    
    //====================== Override Base Method =========================//
    
    public static AlertTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AlertTabMainFragment fragment = new AlertTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_alert_tab_main;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {

    }
    
    //====================== ViewBinding Method =========================//
    
    //====================== Contract Method =========================//
    //====================== Helper Method =========================//
}
