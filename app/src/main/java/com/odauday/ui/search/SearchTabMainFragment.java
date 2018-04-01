package com.odauday.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.databinding.FragmentSearchTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.search.navigation.FilterNavigationFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.ui.view.maplistbutton.MapListToggleButton.OnClickMapListListener;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/30/18.
 */

public class SearchTabMainFragment extends BaseMVVMFragment<FragmentSearchTabMainBinding> implements
                                                                                          HasSupportFragmentInjector {
    
    
    //====================== Variable =========================//
    public static final String TAG = NavigationTab.SEARCH_TAB.getNameTab();
    
    @Inject
    SearchTabViewModel mSearchTabViewModel;
    
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;
    
    private Toolbar mToolbar;
    private LinearLayout mToolBarView;
    private Button mSearchBar;
    private Button mButtonFilter;
    
    //====================== Constructor =========================//
    public static SearchTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchTabMainFragment fragment = new SearchTabMainFragment();
        fragment.setArguments(args);
        
        Timber.tag(TAG).i("New Instance");
        return fragment;
    }
    
    //====================== Override Base Method =========================//
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_tab_main;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        if (mChildFragmentInjector == null) {
            Timber.tag(TAG).d("njector null");
        } else {
            Timber.tag(TAG).d("not null");
        }
        initBinding();
        setupToolBar(view);
        setupFilterNavigation();
        
        
    }
    
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mSearchTabViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    //====================== ViewBinding Method =========================//
    
    //====================== Contract Method =========================//
    
    //====================== Helper Method =========================//
    
    private void initBinding() {
        mBinding.get().btnMapList.setOnClickMapListListener(new OnClickMapListListener() {
            @Override
            public void onShowListView() {
                Timber.tag(TAG).i("SHOW LIST");
            }
            
            @Override
            public void onHideListView() {
                Timber.tag(TAG).i("HIDE LIST");
            }
            
            @Override
            public void onShowMapView() {
                Timber.tag(TAG).i("SHOW MAP");
            }
            
            @Override
            public void onHideMapView() {
                Timber.tag(TAG).i("HIDE MAP");
            }
        });
        mBinding.get().drawerLayout.addDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            
            }
            
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            
            }
            
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            
            }
            
            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    if (((MainActivity) getActivity()) != null) {
                        boolean isDrawerOpen = mBinding.get().drawerLayout.isDrawerVisible(Gravity.END);
                        if (!isDrawerOpen) {
                            ((MainActivity) getActivity()).toggleBottomBar(false);
                        } else {
                            ((MainActivity) getActivity()).toggleBottomBar(true);
                        }
                    }
                    
                }
            }
        });
    }
    
    boolean isNeedShowBottomBar = true;
    
    private void openDrawer() {
        mBinding.get().drawerLayout.openDrawer(Gravity.END);
    }
    
    private void closeDrawer() {
        mBinding.get().drawerLayout.closeDrawer(Gravity.END);
    }
    
    private void setupToolBar(View view) {
        bindViewOnToolBar(view);
    }
    
    private void setupFilterNavigation() {
        //        Runnable runnableAttachFilterFragment = new AttachFragmentRunnable.AttachFragmentBuilder()
        //                  .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
        //                  .setFragmentManager(getActivity().getSupportFragmentManager())
        //                  .setFragment(FilterNavigationFragment.newInstance())
        //                  .setTagFragment(FilterNavigationFragment.TAG)
        //                  .setContainerId(R.id.filter_nav)
        //                  .build();
        //
        //        new Handler().postDelayed(runnableAttachFilterFragment,
        //                  NavigationController.DELAY_ATTACH_FRAGMENT);
        
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.filter_nav,
                  FilterNavigationFragment.newInstance()).commit();
    }
    
    private void bindViewOnToolBar(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolBarView = mToolbar.findViewById(R.id.toolbar_view);
        mToolBarView.setVisibility(View.VISIBLE);
        mSearchBar = mToolbar.findViewById(R.id.search_bar);
        mButtonFilter = mToolbar.findViewById(R.id.btn_filter);
        
        mSearchBar.setOnClickListener(searchbar -> {
            
            Toast.makeText(getContext(), "Search bar", Toast.LENGTH_SHORT).show();
        });
        mButtonFilter.setOnClickListener(filter -> {
            openDrawer();
        });
    }
    
    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }
}
