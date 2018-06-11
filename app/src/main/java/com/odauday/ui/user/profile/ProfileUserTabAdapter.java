package com.odauday.ui.user.profile;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/28/18.
 */
public class ProfileUserTabAdapter extends FragmentStatePagerAdapter {
    
    private List<Fragment> mTabs;
    private List<String> mTitles;
    
    
    public void add(Fragment fragment, String title) {
        if (mTabs == null) {
            mTabs = new ArrayList<>();
        }
        
        if (mTitles == null) {
            mTitles = new ArrayList<>();
        }
        
        mTabs.add(fragment);
        mTitles.add(title);
    }
    
    public ProfileUserTabAdapter(FragmentManager fm) {
        super(fm);
    }
    
    @Override
    public Fragment getItem(int position) {
        return mTabs.get(position);
    }
    
    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.size();
    }
    
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
    
    public void destroy() {
        mTabs.clear();
        mTitles.clear();
        
        mTabs = null;
        mTitles = null;
    }
}
