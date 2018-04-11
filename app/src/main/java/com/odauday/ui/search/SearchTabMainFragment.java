package com.odauday.ui.search;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.data.remote.search.SearchService;
import com.odauday.data.remote.search.model.SearchRequest;
import com.odauday.databinding.FragmentSearchTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.common.AttachFragmentRunnable;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.mapview.MapViewFragment;
import com.odauday.ui.search.navigation.FilterNavigationFragment;
import com.odauday.ui.search.navigation.FilterNavigationFragment.OnCompleteRefineFilter;
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
                                                                                          HasSupportFragmentInjector,
                                                                                          OnCompleteRefineFilter {
    
    
    //====================== Variable =========================//
    public static final String TAG = NavigationTab.SEARCH_TAB.getNameTab();
    
    @Inject
    SearchTabViewModel mSearchTabViewModel;
    
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;
    
    @Inject
    SearchService mSearchService;
    
    private FilterNavigationFragment mFilterNavigationFragment;
    private MapViewFragment mMapViewFragment;
    
    //====================== Override Base Method =========================//
    
    //====================== Constructor =========================//
    public static SearchTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchTabMainFragment fragment = new SearchTabMainFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_tab_main;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initBinding();
        setupToolBar(view);
        if (savedInstanceState == null) {
            Timber.d("saveInstance null");
        }
        setupFilterNavigation();
        setupMapView();
    }
    
    @Override
    public void onResume() {
        if (getFilterNavigation() != null) {
            getFilterNavigation().setOnCompleteRefineFilter(this);
        }
        super.onResume();
    }
    
    @Override
    public void onPause() {
        if (getFilterNavigation() != null) {
            getFilterNavigation().setOnCompleteRefineFilter(null);
        }
        super.onPause();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mSearchTabViewModel;
    }
    
    
    @Override
    protected void processingTaskFromViewModel() {

    }
    
    @Override
    public void onCompleteRefineFilter(SearchCriteria searchCriteria) {
        Timber.tag(TAG).i(searchCriteria.toString());
        mSearchService.setCurrentSearchRequest(new SearchRequest(searchCriteria));
        closeDrawer();
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
    }
    
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
        if (getActivity().getSupportFragmentManager() == null) {
            throw new NullPointerException("Fragment manager is null");
        }
        
        if (mFilterNavigationFragment == null) {
            mFilterNavigationFragment = FilterNavigationFragment
                      .newInstance();
        }
        
        Runnable attachRunnableFilterNav = new AttachFragmentRunnable
                  .AttachFragmentBuilder()
                  .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
                  .setFragmentManager(getActivity().getSupportFragmentManager())
                  .setContainerId(R.id.filter_nav)
                  .setFragment(mFilterNavigationFragment)
                  .setTagFragment(FilterNavigationFragment.TAG)
                  .setAddToBackTrack(true)
                  .build();
        
        new Handler().postDelayed(attachRunnableFilterNav, 10);
    }
    
    private void setupMapView() {
        if (getActivity().getSupportFragmentManager() == null) {
            throw new NullPointerException("Fragment manager is null");
        }
        
        if (mMapViewFragment == null) {
            mMapViewFragment = MapViewFragment.newInstance();
        }
        Runnable attachRunnableMapView = new AttachFragmentRunnable
                  .AttachFragmentBuilder()
                  .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
                  .setFragmentManager(getActivity().getSupportFragmentManager())
                  .setContainerId(R.id.fragment_map_view)
                  .setFragment(mMapViewFragment)
                  .setTagFragment(MapViewFragment.TAG)
                  .setAddToBackTrack(true)
                  .build();
        
        new Handler().postDelayed(attachRunnableMapView, 50);
    }
    
    private void bindViewOnToolBar(View view) {
        mBinding.get().toolbar.searchBar.setOnClickListener(
                  viewSearchBar -> Toast.makeText(getContext(), "Search bar", Toast.LENGTH_SHORT)
                            .show());
        mBinding.get().toolbar.btnFilter.setOnClickListener(viewSearchBar -> openDrawer());
    }
    
    public boolean isDrawerOpening() {
        return mBinding.get().drawerLayout.isDrawerOpen(Gravity.END);
    }
    
    private FilterNavigationFragment getFilterNavigation() {
        return mFilterNavigationFragment;
    }
    
    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }
}