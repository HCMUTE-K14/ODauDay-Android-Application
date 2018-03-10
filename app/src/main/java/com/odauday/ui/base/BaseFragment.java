package com.odauday.ui.base;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

/**
 * Created by infamouSs on 2/28/18.
 */

public abstract class BaseFragment extends Fragment {
    
    @LayoutRes
    public abstract int getLayoutId();
}
