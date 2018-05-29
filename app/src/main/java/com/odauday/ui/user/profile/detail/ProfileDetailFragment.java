package com.odauday.ui.user.profile.detail;

import android.os.Bundle;
import com.odauday.ui.base.BaseFragment;

/**
 * Created by infamouSs on 5/29/18.
 */
public class ProfileDetailFragment extends BaseFragment {
    
    @Override
    public int getLayoutId() {
        return 0;
    }
    
    public static ProfileDetailFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
