package com.odauday.ui.user.profile;

import android.support.v4.app.Fragment;
import com.odauday.R;
import com.odauday.ui.user.profile.detail.ProfileDetailFragment;
import com.odauday.ui.user.profile.history.HistoryPropertyFragment;

/**
 * Created by infamouSs on 5/28/18.
 */
public enum ProfileUserTab {
    DETAILS(R.string.txt_details) {
        @Override
        Fragment getInstanceFragment() {
            return ProfileDetailFragment.newInstance();
        }
    },
    HISTORY(R.string.txt_history) {
        @Override
        Fragment getInstanceFragment() {
            return HistoryPropertyFragment.newInstance();
        }
    };
    
    
    private int title;
    
    abstract Fragment getInstanceFragment();
    
    ProfileUserTab(int title) {
        this.title = title;
    }
    
    public int getTitle() {
        return title;
    }
}
