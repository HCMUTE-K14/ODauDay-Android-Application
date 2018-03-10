package com.odauday;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.odauday.data.UserRepository;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    
    @Inject
    UserRepository mUserRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
                  view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show());
        
        fab.setOnClickListener(l -> {
        
        });
        
//        mUserRepository.test()
//                  .subscribeOn(Schedulers.io())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribe(
//                            data -> Logger.d(data.toString()),
//                            throwable -> Logger.d(throwable.toString())
//                  );
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
