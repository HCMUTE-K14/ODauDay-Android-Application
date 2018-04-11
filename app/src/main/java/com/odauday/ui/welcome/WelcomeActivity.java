package com.odauday.ui.welcome;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.odauday.MainActivity;
import com.odauday.ui.search.mapview.MapUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;

/**
 * Created by infamouSs on 3/30/18.
 */

public class WelcomeActivity extends AppCompatActivity {
    
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
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
        
        if (!MapUtils.isHasLocationPermission(this)) {
            MapUtils.requireLocationPermission(this, mPermissionCallBack);
        } else {
            runMainActivity();
        }
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
}
