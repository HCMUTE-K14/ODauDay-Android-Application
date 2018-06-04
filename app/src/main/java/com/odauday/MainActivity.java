package com.odauday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;
import com.google.android.gms.maps.MapsInitializer;
import com.odauday.data.UserRepository;
import com.odauday.databinding.ActivityMainBinding;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.common.NavigationController;
import com.odauday.ui.savedsearch.OnClickSavedSearch;
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
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
        MapsInitializer.initialize(getApplicationContext());
        if (mUserRepository.isNeedLogin()) {
            ViewUtils.startActivity(this, LoginActivity.class);
            finish();
            return;
        }
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
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickSavedSearch(OnClickSavedSearch onClickSavedSearch) {
        mBinding.bottomNavBar.select(SearchTabMainFragment.TAG, false);
        
        mNavigationController.navigateTo(SearchTabMainFragment.TAG);
        ViewUtils.delay(() -> {
            SearchTabMainFragment searchTabMainFragment = (SearchTabMainFragment) getSupportFragmentManager()
                .findFragmentByTag(SearchTabMainFragment.TAG);
            
            if (searchTabMainFragment != null) {
                searchTabMainFragment.loadSavedSearch(onClickSavedSearch.getSearch());
            }
        }, 500);
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            mNavigationController.navigateTo(mTabStack.peek());
            //            getSupportFragmentManager()
            //                .popBackStack(mTabStack.peek(), 0);
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