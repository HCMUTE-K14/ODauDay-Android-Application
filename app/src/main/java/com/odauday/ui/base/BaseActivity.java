package com.odauday.ui.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by infamouSs on 2/28/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    
    @LayoutRes
    public abstract int getLayoutId();
}