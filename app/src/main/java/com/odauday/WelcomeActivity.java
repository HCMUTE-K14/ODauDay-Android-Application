package com.odauday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.odauday.utils.ViewUtils;

/**
 * Created by infamouSs on 3/30/18.
 */

public class WelcomeActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ViewUtils.startActivity(this, MainActivity.class);
        
        finish();
    }
}
