package com.odauday;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.google.android.gms.maps.MapsInitializer;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.odauday.data.NotificationManagerRepository;
import com.odauday.data.NotificationRepository;
import com.odauday.data.UserRepository;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.databinding.ActivityMainBinding;
import com.odauday.model.RefreshTokenDevice;
import com.odauday.model.Tag;
import com.odauday.ui.alert.service.FirebaseMessaging;
import com.odauday.ui.alert.service.Notification;
import com.odauday.ui.alert.service.NotificationEvent;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.common.NavigationController;
import com.odauday.ui.search.SearchTabMainFragment;
import com.odauday.ui.user.login.LoginActivity;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import java.sql.Time;
import java.util.Stack;
import java.util.Timer;
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
        MapsInitializer.initialize(getApplicationContext());
        if (mUserRepository.isNeedLogin()) {
            ViewUtils.startActivity(this, LoginActivity.class);
            finish();
            return;
        }
        saveRefreshTokenDeviceForUser();
        init();
        Timber.tag("TOKEN").d(FirebaseInstanceId.getInstance().getToken());
        showNotifi();
    }
    
    private void showNotifi() {
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = "01";
            String channelName = "Channel 1";
            NotificationManager notificationManager =
                getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW));
        }
        
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(MainActivity.class.getSimpleName(), "Key: " + key + " Value: " + value);
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        setNumberNotification();
        //mBinding.bottomNavBar.setNumberNotification(12);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationEvent(NotificationEvent notificationEvent){
        Timber.tag(MainActivity.class.getSimpleName()).d("NotificationEvent");
        if(notificationEvent.getCode()==1){
            Long number=(Long)notificationEvent.getData();
            if(number>0){
                mBinding.bottomNavBar.setNumberNotification(number.intValue());
            }else {
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
    public void saveRefreshTokenDeviceForUser(){
        Timber.tag("RefreshToken").d("RUN CHECK");
        String tokenFirebase=FirebaseInstanceId.getInstance().getToken();
        Timber.tag("RefreshToken").d(tokenFirebase);
        String tokenSave=mPreferencesHelper.get(PrefKey.REFRESH_TOKEN_DEVICE,"");
        if(!tokenFirebase.equals(tokenSave)){
            String userId=mPreferencesHelper.get(PrefKey.USER_ID,"");
            RefreshTokenDevice refreshTokenDevice=new RefreshTokenDevice(userId,tokenFirebase);
            mNotificationManagerRepository.saveRefreshTokemDevice(refreshTokenDevice)
                .doOnSubscribe(onSubscribe -> {
                    Timber.tag("RefreshToken").d("onLoading");
                })
                .subscribe(success -> {
                    mPreferencesHelper.put(PrefKey.REFRESH_TOKEN_DEVICE,tokenFirebase);
                    MessageResponse messageResponse=(MessageResponse) success;
                    Timber.tag("RefreshToken").d("onSuccess: "+messageResponse.getMessage());
                }, error -> {
                    Timber.tag("RefreshToken").d("onError: "+error.getMessage());
                });
        }
    }
    public void setNumberNotification(){
        String user_id=mPreferencesHelper.get(PrefKey.USER_ID,"");
        mNotificationRepository.getNumberNotification(user_id)
            .doOnSubscribe(onSubscribe -> {
                Timber.tag(MainActivity.class.getSimpleName()).d("onSubscribe");
            })
            .subscribe(success -> {
                Timber.tag(MainActivity.class.getSimpleName()).d("onSuccess");
                int number=success.intValue();
                mBinding.bottomNavBar.setNumberNotification(Integer.valueOf(number));
            }, error -> {
                Timber.tag(MainActivity.class.getSimpleName()).d("onError");
                mBinding.bottomNavBar.setNumberNotification(0);
            });
    }
}