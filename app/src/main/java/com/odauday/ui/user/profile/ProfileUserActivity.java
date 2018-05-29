package com.odauday.ui.user.profile;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import com.odauday.config.Constants.Task;
import com.odauday.data.local.cache.UserPreferenceHelper;
import com.odauday.databinding.ActivityProfileBinding;
import com.odauday.model.User;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.search.common.view.SavedSearchDialog;
import com.odauday.ui.user.profile.history.ClearHistoryCallBack;
import com.odauday.utils.ImageLoader;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/28/18.
 */
public class ProfileUserActivity extends BaseMVVMActivity<ActivityProfileBinding> implements
                                                                                  HasSupportFragmentInjector,
                                                                                  ProfileUserContract {
    
    public static final String TAG = ProfileUserActivity.class.getSimpleName();
    
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    
    @Inject
    UserPreferenceHelper mUserPreferenceHelper;
    
    @Inject
    ProfileUserViewModel mProfileUserViewModel;
    
    private ProfileUserTabAdapter mAdapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setupToolBar();
        setupTabs();
        
        User user = mUserPreferenceHelper.getCurrentUser();
        if (user != null) {
            ImageLoader.load(mBinding.profileCard.profilePicture, user.getAvatar(),
                new RequestOptions().error(R.drawable.ic_profile_picture_default));
            
            mBinding.profileCard.profileName.setText(user.getDisplayName());
            mBinding.profileCard.profileEmail.setText(user.getEmail());
            
            mBinding.profileCard.container.setOnClickListener(view -> {
                showDialogChangeDisplayName();
            });
        }
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mProfileUserViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        if (resource.task.equals(Task.TASK_CLEAR_HISTORY)) {
                            loading(false);
                            onFailureClearHistory();
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(Task.TASK_CLEAR_HISTORY)) {
                            loading(false);
                            onSuccessClearHistory();
                        }
                        break;
                    case LOADING:
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_change_display_name:
                showDialogChangeDisplayName();
                return true;
            case R.id.action_clear_history:
                showDialogConfirmClearHistory();
                return true;
            case R.id.action_logout:
                showDialogConfirmLogout();
                return true;
            default:
                return false;
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
    
    private void setupTabs() {
        
        mAdapter = new ProfileUserTabAdapter(getSupportFragmentManager());
        
        mAdapter.add(ProfileUserTab.DETAILS.getInstanceFragment(),
            getString(ProfileUserTab.DETAILS.getTitle()));
        
        mAdapter.add(ProfileUserTab.HISTORY.getInstanceFragment(),
            getString(ProfileUserTab.HISTORY.getTitle()));
        
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }
    
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }
    
    @Override
    public void onSuccessClearHistory() {
        if (mAdapter.getItem(1) instanceof ClearHistoryCallBack) {
            ClearHistoryCallBack clearHistoryCallBack = (ClearHistoryCallBack) mAdapter.getItem(1);
            clearHistoryCallBack.onSuccessClearHistory();
        }
        Toast.makeText(this, R.string.message_clear_history_successfully, Toast.LENGTH_SHORT)
            .show();
    }
    
    @Override
    public void onFailureClearHistory() {
        if (mAdapter.getItem(1) instanceof ClearHistoryCallBack) {
            ClearHistoryCallBack clearHistoryCallBack = (ClearHistoryCallBack) mAdapter.getItem(1);
            clearHistoryCallBack.onFailureClearHistory();
        }
        Toast.makeText(this, R.string.message_clear_history_failure, Toast.LENGTH_SHORT)
            .show();
    }
    
    @Override
    public void loading(boolean isLoading) {
    
    }
    
    private void showDialogConfirmClearHistory() {
        AlertDialog.Builder dialog = new Builder(this);
        dialog.setTitle(R.string.txt_wait_a_second)
            .setMessage(R.string.message_are_u_sure_to_clear_history)
            .setPositiveButton(R.string.txt_no, (dialog12, which) -> {
                dialog12.cancel();
                dialog12.dismiss();
                
            })
            .setNegativeButton(R.string.txt_yes,
                (dialog1, which) -> mProfileUserViewModel.clearHistory());
        
        dialog.create().show();
    }
    
    private void showDialogConfirmLogout() {
        AlertDialog.Builder dialog = new Builder(this);
        dialog.setTitle(R.string.txt_wait_a_second)
            .setMessage(R.string.message_are_u_want_to_log_out)
            .setPositiveButton(R.string.txt_no, (dialog12, which) -> {
                dialog12.cancel();
                dialog12.dismiss();
            })
            .setNegativeButton(R.string.txt_yes,
                (dialog1, which) -> {
                    logOut();
                });
        
        dialog.create().show();
    }
    
    private void showDialogChangeDisplayName() {
        ChangeDisplayNameDialog changeDisplayNameDialog = ChangeDisplayNameDialog.newInstance();
        changeDisplayNameDialog.setChangeDisplayNameListener(newDisplayName -> {
        
        });
        changeDisplayNameDialog.setTargetFragment(null,
            SavedSearchDialog.REQUEST_CODE);
        if (getFragmentManager() != null) {
            changeDisplayNameDialog.show(getSupportFragmentManager(), TAG);
        }
    }
    
    private void logOut() {
        mUserPreferenceHelper.logOut();
        Intent i = getBaseContext().getPackageManager()
            .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
