package com.odauday.ui.alert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.odauday.R;

public class Demo extends AppCompatActivity {
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        mTextView=findViewById(R.id.txt_bom);
        Intent intent_o = getIntent();
        String body = intent_o.getStringExtra("body");
        mTextView.setText(body);
    }
}
