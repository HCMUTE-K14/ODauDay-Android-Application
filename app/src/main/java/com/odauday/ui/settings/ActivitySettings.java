package com.odauday.ui.settings;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import com.odauday.R;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.databinding.ActivitySettingsBinding;
import com.odauday.exception.RetrofitException;
import com.odauday.ui.base.BaseContract;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.utils.SnackBarUtils;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/26/2018.
 */

public class ActivitySettings extends BaseMVVMActivity<ActivitySettingsBinding> implements
                                                                                BaseContract {
    
    private static final String TAG = ActivitySettings.class.getSimpleName();
    @Inject
    ChooseLanguageHelper mChooseLanguageHelper;
    
    @Inject
    SettingsViewModel mSettingsViewModel;
    
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mSettingsViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        onFailure((Exception) resource.data);
                        loading(false);
                        break;
                    case SUCCESS:
                        onSuccess(resource.data);
                        loading(false);
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
    public void loading(boolean isLoading) {
    
    }
    
    @Override
    public void onSuccess(Object object) {
        MessageResponse messageResponse = (MessageResponse) object;
        if (messageResponse != null) {
            SnackBarUtils.showSnackBar(mBinding.setting, messageResponse.getMessage());
        }
    }
    
    @Override
    public void onFailure(Exception ex) {
        String message;
        if (ex instanceof RetrofitException) {
            message = getString(R.string.message_service_unavailable);
        } else {
            message = getString(R.string.cannot_clear_history);
        }
        SnackBarUtils.showSnackBar(mBinding.setting, message);
    }
    
    public void onClickBack(View view) {
        this.finish();
    }
    
    public void onClickSelectLanguage(View view) {
        Timber.tag(TAG).d("click select language");
        mChooseLanguageHelper.change(this);
    }
    
    public void onClickClearHistory(View view) {
        Timber.tag(TAG).d("click clear history");
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage(getString(R.string.message_clear_history));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.txt_ok), (dialogInterface, i) -> {
            mSettingsViewModel.clearHistory(mPreferencesHelper.get(PrefKey.USER_ID, ""));
        });
        builder.setNegativeButton(getString(R.string.txt_cancel), (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
    
    public void onClickLogout(View view) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage(getString(R.string.logout_user));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.txt_ok), (dialogInterface, i) -> {
            Timber.tag(TAG).d("click logout");
            dialogInterface.dismiss();
            mPreferencesHelper.clear();
            AlertDialog.Builder builder1 = new Builder(this);
            builder1.setMessage(getString(R.string.logout_user_success));
            builder1.setCancelable(true);
            builder1.setPositiveButton(getString(R.string.txt_ok), (dialog, i1) -> {
                Intent intent = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
            
            AlertDialog alert11 = builder1.create();
            alert11.show();
            
        });
        builder.setNegativeButton(getString(R.string.txt_cancel), (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.show();
        
    }
    
    public void onClickPrivacyPolicy(View view) {
        Timber.tag(TAG).d("click privacy policy");
    }
    
    public void onClickConditionsOfUse(View view) {
        Timber.tag(TAG).d("click conditions of use");
    }
    
    public void onClickInformation(View view) {
        Timber.tag(TAG).d("click information");
    }
}
