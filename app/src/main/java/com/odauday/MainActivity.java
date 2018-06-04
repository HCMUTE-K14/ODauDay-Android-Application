package com.odauday;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;
import com.odauday.data.UserRepository;
import com.odauday.databinding.ActivityMainBinding;
import com.odauday.ui.alert.service.FirebaseMessaging;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.common.NavigationController;
import com.odauday.ui.search.SearchTabMainFragment;
import com.odauday.ui.user.login.LoginActivity;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import java.util.Stack;
import javax.inject.Inject;

public class MainActivity extends BaseMVVMActivity<ActivityMainBinding> implements
                                                                        HasSupportFragmentInjector {
    
    final Stack<String> mTabStack = new Stack<>();
    
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    
    @Inject
    NavigationController mNavigationController;
    
    @Inject
    MainActivityViewModel mMainActivityViewModel;
    
    @Inject
    UserRepository mUserRepository;
    
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mUserRepository.isNeedLogin()) {
            ViewUtils.startActivity(this, LoginActivity.class);
            finish();
            return;
        }
        init();
    }
    
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
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
        try {
            if (mTabStack.empty()) {
                mTabStack.push(NavigationTab.SEARCH_TAB.getNameTab());
            }
            String tag = mTabStack.pop();
            if (tag.equals(NavigationTab.SEARCH_TAB.getNameTab())) {
                SearchTabMainFragment searchTabMainFragment = (SearchTabMainFragment) getSupportFragmentManager()
                    .findFragmentByTag(SearchTabMainFragment.TAG);
                if (searchTabMainFragment.isDrawerOpening()) {
                    return;
                }
                finish();
                return;
            }
            
            if (mTabStack.empty()) {
                finish();
                return;
            }
            
            mBinding.bottomNavBar.select(mTabStack.peek(), false);
            getSupportFragmentManager()
                .popBackStack(mTabStack.peek(), 0);
        } catch (Exception ex) {
            finish();
        }
    }
    
    private void init() {
        
        mBinding.bottomNavBar.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                String nameTab = mBinding.bottomNavBar.getNameTab(position);
                mTabStack.push(nameTab);
                mNavigationController.navigateTo(nameTab);
            }
            
            @Override
            public void onTabUnselected(int position) {
            
            }
            
            @Override
            public void onTabReselected(int position) {
                String nameTab = mBinding.bottomNavBar.getNameTab(position);
                if (!nameTab.equals(mTabStack.peek())) {
                    mNavigationController.navigateTo(nameTab);
                }
            }
        });
        mBinding.bottomNavBar.select(0, true);
    }
    
    public void toggleBottomBar(boolean show) {
        mBinding.bottomNavBar.setVisibility(show ? View.VISIBLE : View.GONE);
        if (mBinding.bottomNavBar.getVisibility() == View.VISIBLE) {
            mBinding.bottomNavBar
                .startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        }
    }
}