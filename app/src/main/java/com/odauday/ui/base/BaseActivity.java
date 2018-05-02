package com.odauday.ui.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import com.odauday.R;

/**
 * Created by infamouSs on 2/28/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    
    @LayoutRes
    protected abstract int getLayoutId();
    
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fade_out);
    }
}