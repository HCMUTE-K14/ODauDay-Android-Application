package com.odauday.ui.savedsearch;

import android.os.Bundle;
import com.odauday.R;
import com.odauday.databinding.FragmentSavedSearchTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by infamouSs on 3/31/18.
 */

public class SavedSearchTabMainFragment extends
                                        BaseMVVMFragment<FragmentSavedSearchTabMainBinding> {

    //====================== Variable ======================//

    public static final String TAG = NavigationTab.SAVED_SEARCH_TAB.getNameTab();

    //====================== Constructor =========================//
    public static SavedSearchTabMainFragment newInstance() {

        Bundle args = new Bundle();

        SavedSearchTabMainFragment fragment = new SavedSearchTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //====================== Override Base Method ======================//

    @Override
    public int getLayoutId() {
        return R.layout.fragment_saved_search_tab_main;
    }


    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }

    @Override
    protected void processingTaskFromViewModel() {

    }

    //====================== ViewBinding Method ======================//

    //====================== Contract Method =========================//

    //====================== Helper Method =========================//
}
