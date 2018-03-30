package com.odauday.ui.view.bottomnav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.odauday.R;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/30/18.
 */

public class FlatNavigationView extends FrameLayout {
    
    
    private BottomNavigationBar mBottomNavigationBar;
    
    public FlatNavigationView(@NonNull Context context) {
        super(context);
        init(context);
    }
    
    public FlatNavigationView(@NonNull Context context,
              @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public FlatNavigationView(@NonNull Context context, @Nullable AttributeSet attrs,
              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_bottom_navigation_view, this, true);
        
        mBottomNavigationBar = (BottomNavigationBar) rootView.findViewById(R.id.nav_bar);
        
        initBottomNav();
    }
    
    private void initBottomNav() {
        initStyleBottomNavBar();
        addNavButton();
    }
    
    private void initStyleBottomNavBar() {
        this.mBottomNavigationBar
                  .setMode(BottomNavigationBar.MODE_FIXED)
                  .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                  .setAnimationDuration(0)
                  .setActiveColor(R.color.nav_bar_text_active)
                  .setInActiveColor(R.color.nav_bar_text_inactive);
    }
    
    private NavigationTab[] getTabs() {
        return new NavigationTab[]{NavigationTab.SEARCH_TAB, NavigationTab.ALERT_TAB,
                  NavigationTab.FAVORITE_TAB, NavigationTab.SAVED_SEARCH_TAB,
                  NavigationTab.MORE_TAB};
    }
    
    private void addNavButton() {
        NavigationTab[] tabs = getTabs();
        
        for (NavigationTab tab : tabs) {
            Timber.d("Init tab");
            mBottomNavigationBar.addItem(tab.build(this.getContext()));
        }
        
        mBottomNavigationBar.initialise();
    }
}
