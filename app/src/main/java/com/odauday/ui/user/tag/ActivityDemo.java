package com.odauday.ui.user.tag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import com.odauday.R;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.databinding.ActivityDemoBinding;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.viewmodel.BaseViewModel;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/1/2018.
 */

public class ActivityDemo extends BaseMVVMActivity<ActivityDemoBinding> implements TagContract{
    
    private static final String TAG=ActivityDemo.class.getSimpleName();
    
    @Inject
    TagViewModel mTagViewModel;
    
   /* @Inject
    PreferencesHelper mPreferencesHelper;*/
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mTagViewModel;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        BaseMVVMFragment fragment = new FragmentDemo();
    
        getSupportFragmentManager().beginTransaction()
                  .replace(R.id.frame_layout, fragment).commit();
        
        
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        /*mTagViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        loading(false);
                        onFailure((Exception) resource.data);
                        break;
                    case SUCCESS:
                        loading(false);
                        onSuccess(resource.data);
                        break;
                    case LOADING:
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });*/
    }
    public void onClickDemo(View view){
       /* mPreferencesHelper.put(PrefKey.ACCESS_TOKEN,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiYTg4MjExYmEtMzA3Ny00MGUyLTk2ODUtNWFiNDUwYWJiMTE0IiwiZW1haWwiOiJkYW9odXVsb2M5NDE5QGdtYWlsLmNvbSIsImRpc3BsYXlfbmFtZSI6ImluZmFtb3VTcyIsInBob25lIjpudWxsLCJhdmF0YXIiOm51bGwsInJvbGUiOiJ1c2VyIiwic3RhdHVzIjoicGVuZGluZyIsImZhY2Vib29rX2lkIjpudWxsLCJhbW91bnQiOm51bGx9LCJpYXQiOjE1MjIyMTM4NzN9.kbl64zvlPOFlc8NdFqlcbAc-I5I7D1WVC_BwUYearjs");
        mPreferencesHelper.put(PrefKey.USER_ID,"a88211ba-3077-40e2-9685-5ab450abb114");
    
        Timber.tag("AccessToken").e(mPreferencesHelper.get(PrefKey.ACCESS_TOKEN,""));
        Timber.tag("UserID").e(mPreferencesHelper.get(PrefKey.USER_ID,""));
        Tag tag=new Tag();
        tag.setName("chua te hac am chi tu");
        mTagViewModel.createTag(tag);*/
    }
    @Override
    public void loading(boolean isLoading) {
    
    }
    
    @Override
    public void onSuccess(Object object) {
       // List<Tag> list=(List<Tag>) object;
       // Timber.tag(TAG).e(((MessageResponse)object).getMessage());
    }
    
    @Override
    public void onFailure(Exception ex) {
       // Timber.tag("Lang thang:").e(ex.getMessage());
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo;
    }
}
