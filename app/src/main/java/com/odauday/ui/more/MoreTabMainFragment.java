package com.odauday.ui.more;

import android.os.Bundle;
import com.odauday.R;
import com.odauday.databinding.FragmentMoreTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by infamouSs on 3/31/18.
 */

public class MoreTabMainFragment extends BaseMVVMFragment<FragmentMoreTabMainBinding> {
    
    //====================== Variable =========================//
    
    public static final String TAG = NavigationTab.MORE_TAB.getNameTab();
    
    //====================== Constructor =========================//
    
    public static MoreTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MoreTabMainFragment fragment = new MoreTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //====================== Override Base Method =========================//
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_more_tab_main;
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
