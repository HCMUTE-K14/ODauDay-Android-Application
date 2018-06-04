package com.odauday.ui.alert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.firebase.iid.FirebaseInstanceId;
import com.odauday.R;
import com.odauday.databinding.FragmentAlertTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.viewmodel.BaseViewModel;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class AlertTabMainFragment extends BaseMVVMFragment<FragmentAlertTabMainBinding> {
    
    public static final String TAG = NavigationTab.ALERT_TAB.getNameTab();
    
    public static AlertTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AlertTabMainFragment fragment = new AlertTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(TAG).d("Token: " + FirebaseInstanceId.getInstance().getToken());
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
    
}
