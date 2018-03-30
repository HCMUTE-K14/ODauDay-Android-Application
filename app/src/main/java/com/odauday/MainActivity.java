package com.odauday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.odauday.ui.base.BaseActivity;
import com.odauday.utils.SnackBarUtils;

public class MainActivity extends BaseActivity {
    
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        
        SnackBarUtils.showSnackBar(findViewById(android.R.id.content), "SHOW");
    }
}
