package com.odauday.ui.search;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.data.SearchPropertyRepository;
import com.odauday.data.SearchPropertyState;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.databinding.FragmentSearchTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.common.AttachFragmentRunnable;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.event.NeedCloseVitalProperty;
import com.odauday.ui.search.common.event.OnCompleteDownloadProperty;
import com.odauday.ui.search.common.event.OnErrorDownloadProperty;
import com.odauday.ui.search.common.event.ReloadSearchEvent;
import com.odauday.ui.search.common.view.InformationBar.InformationBarListener;
import com.odauday.ui.search.common.view.VitalPropertyView;
import com.odauday.ui.search.mapview.MapViewFragment;
import com.odauday.ui.search.mapview.MapViewFragment.MapFragmentClickCallBack;
import com.odauday.ui.search.navigation.FilterNavigationFragment;
import com.odauday.ui.search.navigation.FilterNavigationFragment.OnCompleteRefineFilter;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.ui.view.maplistbutton.MapListToggleButton.OnClickMapListListener;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/30/18.
 */

public class SearchTabMainFragment extends BaseMVVMFragment<FragmentSearchTabMainBinding> implements
                                                                                          HasSupportFragmentInjector,
                                                                                          OnCompleteRefineFilter,
                                                                                          MapFragmentClickCallBack {
    
    
    //====================== Variable =========================//
    public static final String TAG = NavigationTab.SEARCH_TAB.getNameTab();
    
    @Inject
    SearchTabViewModel mSearchTabViewModel;
    
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;
    
    @Inject
    SearchPropertyRepository mSearchPropertyRepository;
    
    @Inject
    MapPreferenceHelper mMapPreferenceHelper;
    
    @Inject
    EventBus mBus;
    
    VitalPropertyView mVitalPropertyView;
    
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
        new Handler().postDelayed(() -> {
            setupFilterNavigation();
            getFilterNavigation().setOnCompleteRefineFilter(this);
        }, 10);
        setupMapView();
        getMapViewFragment().setMapFragmentClickCallBack(this);
        initVitalProperty();
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
    public void onStart() {
        super.onStart();
        mBus.register(this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        mBus.unregister(this);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("ON Destroy search tab");
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeStateWhenSearchProperty(SearchPropertyState state) {
        if (state.isShouldShowProgressBar()) {
            mBinding.get().inforBar.showProgressBar();
            return;
        }
        mBinding.get().inforBar.hideProgressBar();
        if (mBinding.get().inforBar.isShowErrorContainer()) {
            mBinding.get().inforBar.showHideErrorContainer(false);
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorSearchProperty(OnErrorDownloadProperty errorDownloadProperty) {
        mBinding.get().inforBar.showHideErrorContainer(true);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteDownloadProperty(OnCompleteDownloadProperty onCompleteDownloadProperty) {
        String textStatus = String.valueOf(onCompleteDownloadProperty.getResult().size());
        mBinding.get().inforBar.setTextStatus(textStatus);
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
        mMapPreferenceHelper.putRecentSearchCriteria(searchCriteria);
        mSearchPropertyRepository.getCurrentSearchRequest().setCriteria(searchCriteria);
        closeDrawer();
        mBus.post(new ReloadSearchEvent());
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNeedCloseVitalProperty(NeedCloseVitalProperty needCloseVitalProperty) {
        View vitalPropertyContainer = getVitalPropertyContainer();
        
        if (vitalPropertyContainer != null &&
            vitalPropertyContainer.getVisibility() == View.VISIBLE) {
            showVitalProperty(false);
            showBottomBar(true);
        }
    }
    
    @Override
    public void onMapPropertyClick(PropertyResultEntry entry) {
        View vitalPropertyContainer = getVitalPropertyContainer();
        if (vitalPropertyContainer != null) {
            if (vitalPropertyContainer.getVisibility() != View.VISIBLE) {
                showBottomBar(false);
                showVitalProperty(true);
            }
            mVitalPropertyView.setProperty(entry);
        }
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
        mBinding.get().inforBar.setListener(new InformationBarListener() {
            @Override
            public void onClickSaveSearch() {

            }
            
            @Override
            public void onCLickSort() {

            }
            
            @Override
            public void onClickReload() {
                mBus.post(new ReloadSearchEvent());
            }
        });
    }
    
    private void initVitalProperty() {
        if ((MainActivity) getActivity() != null) {
            mVitalPropertyView = new VitalPropertyView(this.getActivity());
            
            ((MainActivity) getActivity()).getBinding().vitalPropertyContainer
                      .addView(mVitalPropertyView);
            
            ViewUtils.showHideView(
                      ((MainActivity) getActivity()).getBinding().vitalPropertyContainer,
                      false);
        }
    }
    
    private View getVitalPropertyContainer() {
        if (((MainActivity) getActivity()) != null) {
            return ((MainActivity) getActivity()).getBinding().vitalPropertyContainer;
        }
        return null;
    }
    
    private View getBottomBar() {
        if (((MainActivity) getActivity()) != null) {
            return ((MainActivity) getActivity()).getBinding().bottomNavBar;
        }
        return null;
    }
    
    private void showVitalProperty(boolean show) {
        if ((MainActivity) getActivity() != null) {
            View view = getVitalPropertyContainer();
            if (view == null) {
                return;
            }
            if (show) {
                view.startAnimation(
                          AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_up));
                view.setVisibility(View.VISIBLE);
            } else {
                view.startAnimation(
                          AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_down));
                view.setVisibility(View.GONE);
            }
        }
    }
    
    private void showBottomBar(boolean show) {
        if ((MainActivity) getActivity() != null) {
            View view = getBottomBar();
            if (view == null) {
                return;
            }
            if (show) {
                view.startAnimation(
                          AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_up));
                view.setVisibility(View.VISIBLE);
            } else {
                view.startAnimation(
                          AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_down));
                view.setVisibility(View.GONE);
            }
        }
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
        
        getActivity().getSupportFragmentManager().beginTransaction()
                  .replace(R.id.filter_nav, mFilterNavigationFragment, FilterNavigationFragment.TAG)
                  .commit();
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
                  .setAddToBackTrack(false)
                  .setAnimationIn(android.R.anim.fade_in)
                  .setAnimationOut(android.R.anim.fade_out)
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
    
    public FilterNavigationFragment getFilterNavigation() {
        return mFilterNavigationFragment;
    }
    
    public MapViewFragment getMapViewFragment() {
        return mMapViewFragment;
    }
    
    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }
    
    
}