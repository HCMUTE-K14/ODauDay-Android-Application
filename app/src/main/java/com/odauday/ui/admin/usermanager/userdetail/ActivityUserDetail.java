package com.odauday.ui.admin.usermanager.userdetail;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import com.odauday.databinding.ActivityProfileBinding;
import com.odauday.model.User;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.user.profile.ProfileUserTabAdapter;
import com.odauday.utils.ImageLoader;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

/**
 * Created by kunsubin on 6/11/2018.
 */

public class ActivityUserDetail extends BaseMVVMActivity<ActivityProfileBinding> implements
                                                                                 HasSupportFragmentInjector {
    private ProfileUserTabAdapter mAdapter;
    private User mUser;
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();
        initData();
        initTab();
        showInfo();
        
    }
    
    private void initData() {
        Intent intent = getIntent();
        mUser = (User) intent.getParcelableExtra("user");
    }
    private void initTab() {
        mAdapter = new ProfileUserTabAdapter(getSupportFragmentManager());
        
        mAdapter.add(FragmentUserDetail.newInstance(),
            getString(R.string.txt_details));
    
        mAdapter.add(FragmentUserHistory.newInstance(),
            getString(R.string.txt_history));
        
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }
    private void showInfo(){
        if (mUser != null) {
            ImageLoader.load(mBinding.profileCard.profilePicture, mUser.getAvatar(),
                new RequestOptions().error(R.drawable.ic_profile_picture_default));
        
            mBinding.profileCard.profileName.setText(mUser.getDisplayName());
            mBinding.profileCard.profileEmail.setText(mUser.getEmail());
        }
    }
    private void setupToolBar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.txt_profile);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public User getUser(){
        return mUser;
    }
    
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }
}
