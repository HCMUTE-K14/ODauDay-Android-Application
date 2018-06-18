package com.odauday.ui.welcome;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.odauday.MainActivity;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.ui.base.BaseActivity;
import com.odauday.ui.settings.ChooseLanguageHelper;
import com.odauday.ui.user.login.LoginActivity;
import com.odauday.utils.MapUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/30/18.
 */

public class WelcomeActivity extends BaseActivity {
    
    
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    @Inject
    ChooseLanguageHelper mChooseLanguageHelper;
    
    private final PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            runMainActivity();
        }
        
        @Override
        public void onPermissionDenied() {
            runMainActivity();
        }
    };
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        new Handler().postDelayed(() -> {
            String currentLanguage = mPreferencesHelper.get(PrefKey.PREF_LANGUAGE, "en");
            mChooseLanguageHelper.changeConfig(WelcomeActivity.this, currentLanguage);
            if (!MapUtils.isHasLocationPermission(WelcomeActivity.this)) {
                MapUtils.requireLocationPermission(WelcomeActivity.this, mPermissionCallBack);
            } else {
                runMainActivity();
            }
        }, 500);
        
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    private void runMainActivity() {
        ViewUtils.startActivity(this, MainActivity.class);
        finish();
    }
    
    private void runLoginActivity() {
        ViewUtils.startActivity(this, LoginActivity.class);
        finish();
    }
    
    @Override
    protected int getLayoutId() {
        return 0;
    }
}
