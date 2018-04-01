package com.odauday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;
import com.odauday.data.UserRepository;
import com.odauday.databinding.ActivityMainBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.common.NavigationController;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.AnimationUtils;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class MainActivity extends BaseMVVMActivity<ActivityMainBinding> implements
                                                                        HasSupportFragmentInjector {
    
    
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    
    @Inject
    NavigationController mNavigationController;
    
    @Inject
    MainActivityViewModel mMainActivityViewModel;
    
    @Inject
    UserRepository mUserRepository;
    
    private String mCurrentTab;
    
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mMainActivityViewModel;
    }
    
    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 1) {
            //Show Dialog Confirm Quit ?
            finish();
        } else {
            String tag = getSupportFragmentManager()
                      .getBackStackEntryAt(count - 2)
                      .getName();
            getSupportFragmentManager().popBackStack();
            mCurrentTab = tag;
            mBinding.bottomNavBar.select(tag, false);
        }
    }
    
    private void init() {
        mBinding.bottomNavBar.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                String nameTab = mBinding.bottomNavBar.getNameTab(position);
                mNavigationController.navigateTo(nameTab);
            }
            
            @Override
            public void onTabUnselected(int position) {
            
            }
            
            @Override
            public void onTabReselected(int position) {
                String nameTab = mBinding.bottomNavBar.getNameTab(position);
                if (!nameTab.equals(mCurrentTab)) {
                    mCurrentTab = nameTab;
                    mNavigationController.navigateTo(nameTab);
                }
            }
        });
        mBinding.bottomNavBar.select(0, true);
        mCurrentTab = NavigationTab.SEARCH_TAB.getNameTab();
    }
    
    public void toggleBottomBar(boolean show) {
        mBinding.bottomNavBar.setVisibility(show ? View.VISIBLE : View.GONE);
        if (mBinding.bottomNavBar.getVisibility() == View.VISIBLE) {
            mBinding.bottomNavBar
                      .startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        }
    }
}
