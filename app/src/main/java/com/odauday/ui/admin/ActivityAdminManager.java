package com.odauday.ui.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.odauday.R;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.databinding.ActivityAdminManagerBinding;
import com.odauday.model.User;
import com.odauday.ui.admin.propertymanager.FragmentConfirmProperty;
import com.odauday.ui.admin.giftmanager.FragmentGiftManager;
import com.odauday.ui.admin.usermanager.FragmentUserManager;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.view.CircleImageView;
import com.odauday.utils.ImageLoader;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/26/2018.
 */

public class ActivityAdminManager extends BaseMVVMActivity<ActivityAdminManagerBinding> implements
                                                                                        HasSupportFragmentInjector {
    private static final String TAG=ActivityAdminManager.class.getSimpleName();
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    private CircleImageView mCircleImageView;
    private TextView mTextViewDisplayName;
    private TextView mTextViewEmail;
    
    private OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=item -> {
        switch (item.getItemId()){
            case R.id.item_confirm_property:
                Timber.tag(TAG).d("Click: "+item.getTitle());
                mBinding.txtTitle.setText(item.getTitle());
                replaceFragment(FragmentConfirmProperty.newInstance(),FragmentConfirmProperty.TAG);
                
                break;
            case R.id.item_user_manager:
                Timber.tag(TAG).d("Click: "+item.getTitle());
                mBinding.txtTitle.setText(item.getTitle());
                replaceFragment(FragmentUserManager.newInstance(),FragmentUserManager.TAG);
                
                break;
            case R.id.item_gift_manager:
                Timber.tag(TAG).d("Click: "+item.getTitle());
                mBinding.txtTitle.setText(item.getTitle());
                replaceFragment(FragmentGiftManager.newInstance(),FragmentGiftManager.TAG);
                
                break;
            default:
                break;
        }
    
        item.setChecked(true);
        mBinding.drawerLayout.closeDrawers();
    
        return true;
    };
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin_manager;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       init();
       setProfile();
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    private void init(){
        mBinding.navigationMenu.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View view=mBinding.navigationMenu.getHeaderView(0);
        
        mCircleImageView=view.findViewById(R.id.avatar);
        mTextViewDisplayName=view.findViewById(R.id.txt_display_name);
        mTextViewEmail=view.findViewById(R.id.txt_email);
        
        mBinding.navigationMenu.getMenu().getItem(0).setChecked(true);
        replaceFragment(FragmentConfirmProperty.newInstance(),FragmentConfirmProperty.TAG);
    }
    private void setProfile(){
        String s_user=mPreferencesHelper.get(PrefKey.CURRENT_USER,"");
        User user=new Gson().fromJson(s_user, User.class);
        if(user!=null){
            mTextViewDisplayName.setText(user.getDisplayName());
            mTextViewEmail.setText(user.getEmail());
            ImageLoader.load(mCircleImageView,user.getAvatar());
        }
    }
    public void onClickMore(View view){
        if(mBinding.drawerLayout.isDrawerOpen(Gravity.END)){
            mBinding.drawerLayout.closeDrawer(Gravity.END);
        }else {
            mBinding.drawerLayout.openDrawer(Gravity.END);
        }
    }
    public void onClickBack(View view){
        this.finish();
    }
    private void replaceFragment(Fragment fragment, String fragmentTag) {
        for (Fragment fragment1:getSupportFragmentManager().getFragments()){
            getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
        }
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.frame_layout_main, fragment, fragmentTag)
            .commit();
    }
    
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }
}
