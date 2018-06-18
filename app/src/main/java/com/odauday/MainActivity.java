package com.odauday;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.firebase.iid.FirebaseInstanceId;
import com.odauday.data.NotificationManagerRepository;
import com.odauday.data.NotificationRepository;
import com.odauday.data.UserRepository;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.databinding.ActivityMainBinding;
import com.odauday.model.RefreshTokenDevice;
import com.odauday.ui.alert.service.NotificationEvent;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.common.NavigationController;
import com.odauday.ui.savedsearch.OnClickSavedSearch;
import com.odauday.ui.search.SearchTabMainFragment;
import com.odauday.ui.user.login.LoginActivity;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.TextUtils;
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
import timber.log.Timber;

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
    
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    @Inject
    NotificationManagerRepository mNotificationManagerRepository;
    
    @Inject
    NotificationRepository mNotificationRepository;
    
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
        saveRefreshTokenDeviceForUser();
        init();
        Timber.tag("TOKEN").d(FirebaseInstanceId.getInstance().getToken());
        setNumberNotification();
    }

    
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        
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
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationEvent(NotificationEvent notificationEvent) {
        Timber.tag(MainActivity.class.getSimpleName()).d("NotificationEvent");
        if (notificationEvent.getCode() == 1) {
            Long number = (Long) notificationEvent.getData();
            if (number > 0) {
                mBinding.bottomNavBar.setNumberNotification(number.intValue());
            } else {
                mBinding.bottomNavBar.setNumberNotification(0);
            }
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
    
    public void saveRefreshTokenDeviceForUser() {
        Timber.tag("RefreshToken").d("RUN CHECK");
        String tokenFirebase = FirebaseInstanceId.getInstance().getToken();
        Timber.tag("RefreshToken").d(tokenFirebase);
        String tokenSave = mPreferencesHelper.get(PrefKey.REFRESH_TOKEN_DEVICE, "");
        if (!TextUtils.isEmpty(tokenFirebase) && !tokenFirebase.equals(tokenSave)) {
            String userId = mPreferencesHelper.get(PrefKey.USER_ID, "");
            RefreshTokenDevice refreshTokenDevice = new RefreshTokenDevice(userId, tokenFirebase);
            mNotificationManagerRepository.saveRefreshTokemDevice(refreshTokenDevice)
                .doOnSubscribe(onSubscribe -> {
                    Timber.tag("RefreshToken").d("onLoading");
                })
                .subscribe(success -> {
                    mPreferencesHelper.put(PrefKey.REFRESH_TOKEN_DEVICE, tokenFirebase);
                    MessageResponse messageResponse = (MessageResponse) success;
                    Timber.tag("RefreshToken").d("onSuccess: " + messageResponse.getMessage());
                }, error -> {
                    Timber.tag("RefreshToken").d("onError: " + error.getMessage());
                });
        }
    }
    
    public void setNumberNotification() {
        String user_id = mPreferencesHelper.get(PrefKey.USER_ID, "");
        mNotificationRepository.getNumberNotification(user_id)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(MainActivity.class.getSimpleName()).d("onSubscribe");
            })
            .subscribe(success -> {
                Timber.tag(MainActivity.class.getSimpleName()).d("onSuccess");
                int number = success.intValue();
                mBinding.bottomNavBar.setNumberNotification(Integer.valueOf(number));
            }, error -> {
                Timber.tag(MainActivity.class.getSimpleName()).d("onError");
                mBinding.bottomNavBar.setNumberNotification(0);
            });
    }
}