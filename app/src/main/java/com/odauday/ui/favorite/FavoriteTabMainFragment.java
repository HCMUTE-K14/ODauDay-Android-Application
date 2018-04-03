package com.odauday.ui.favorite;

import android.os.Bundle;
import com.odauday.R;
import com.odauday.databinding.FragmentFavoriteTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by infamouSs on 3/31/18.
 */

public class FavoriteTabMainFragment extends BaseMVVMFragment<FragmentFavoriteTabMainBinding> {

    //====================== Variable Method =========================//

    public static final String TAG = NavigationTab.FAVORITE_TAB.getNameTab();

    //====================== Constructor =========================//

    public static FavoriteTabMainFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteTabMainFragment fragment = new FavoriteTabMainFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //====================== Override Base Method =========================//

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite_tab_main;
    }


    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }

    @Override
    protected void processingTaskFromViewModel() {

    }

    //====================== ViewBinding Method =========================//
    //====================== Contract Method =========================//
    //====================== Helper Method =========================//
}
