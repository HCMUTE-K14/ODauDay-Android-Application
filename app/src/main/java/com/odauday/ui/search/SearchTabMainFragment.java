package com.odauday.ui.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.data.SearchPropertyRepository;
import com.odauday.data.SearchPropertyState;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.data.remote.property.model.SearchResult;
import com.odauday.databinding.FragmentSearchTabMainBinding;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.common.AttachFragmentRunnable;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceActivity;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.event.NeedCloseVitalPropertyEvent;
import com.odauday.ui.search.common.event.OnCompleteDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnErrorDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnFavouriteEvent;
import com.odauday.ui.search.common.event.ReInitMapFragmentEvent;
import com.odauday.ui.search.common.event.ReloadSearchEvent;
import com.odauday.ui.search.common.view.InformationBar.InformationBarListener;
import com.odauday.ui.search.common.view.LoadingFragment;
import com.odauday.ui.search.common.view.LoadingFragment.LoadingFragmentListener;
import com.odauday.ui.search.common.view.SavedSearchDialog;
import com.odauday.ui.search.common.view.SavedSearchDialog.SaveSearchListener;
import com.odauday.ui.search.common.view.VitalPropertyView;
import com.odauday.ui.search.mapview.MapViewFragment;
import com.odauday.ui.search.mapview.MapViewFragment.MapFragmentClickCallBack;
import com.odauday.ui.search.navigation.FilterNavigationFragment;
import com.odauday.ui.search.navigation.FilterNavigationFragment.OnCompleteRefineFilter;
import com.odauday.ui.view.StarView.OnClickStarListener;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.ui.view.maplistbutton.MapListToggleButton.OnClickMapListListener;
import com.odauday.utils.NetworkUtils;
import com.odauday.utils.TextUtils;
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
                                                                                          MapFragmentClickCallBack,
                                                                                          SaveSearchListener,
                                                                                          LoadingFragmentListener,
                                                                                          OnClickStarListener<PropertyResultEntry> {
    
    
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
    
    private VitalPropertyView mVitalPropertyView;
    private FilterNavigationFragment mFilterNavigationFragment;
    private MapViewFragment mMapViewFragment;
    
    //====================== Override Base Method =========================//
    
    //====================== Constructor =========================//
    public static SearchTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchTabMainFragment fragment = new SearchTabMainFragment();
        Timber.d("new instance search tab");
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
        mBus.register(this);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchTabViewModel.setSearchTabMainFragment(this);
        initBinding();
        setupToolBar(view);
        new Handler().postDelayed(() -> {
            setupFilterNavigation();
            getFilterNavigation().setOnCompleteRefineFilter(this);
        }, 10);
        
        initMainLayout();
        
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
    public void onDestroy() {
        mBus.unregister(this);
        System.gc();
        
        Timber.d("On destroy search tab");
        super.onDestroy();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeStateWhenSearchProperty(SearchPropertyState state) {
        String displayLocation = mSearchPropertyRepository
            .getCurrentSearchRequest()
            .getCriteria()
            .getDisplay()
            .getDisplayLocation();
        
        if (TextUtils.isEmpty(displayLocation)) {
            updateTextSearchBar(getString(R.string.txt_map_area));
        } else {
            updateTextSearchBar(displayLocation);
        }
        
        if (state.isShouldShowProgressBar()) {
            mBinding.get().inforBar.showProgressBar();
            return;
        }
        mBinding.get().inforBar.hideProgressBar();
        if (mBinding.get().inforBar.isShowErrorContainer()) {
            mBinding.get().inforBar.showHideErrorContainer(false);
        }
        
        if (mMapViewFragment != null && mMapViewFragment.isVisible()) {
            mBinding.get().inforBar.showHideButtonSort(false);
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorSearchProperty(OnErrorDownloadPropertyEvent errorDownloadProperty) {
        mBinding.get().inforBar.setTextError(getString(R.string.message_cannot_connect_to_service));
        mBinding.get().inforBar.showHideErrorContainer(true);
    }
    
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteDownloadProperty(
        OnCompleteDownloadPropertyEvent onCompleteDownloadPropertyEvent) {
        SearchRequest searchRequest = mSearchPropertyRepository.getCurrentSearchRequest();
        SearchResult searchResult = onCompleteDownloadPropertyEvent.getSearchResult();
        if (mBinding.get().inforBar != null) {
            mBinding.get().inforBar
                .updateWithSearchRequestAndSearchResult(searchRequest, searchResult);
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReInitMapFragment(ReInitMapFragmentEvent reInitMapFragmentEvent) {
        initMainLayout();
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
    public void onNeedCloseVitalProperty(NeedCloseVitalPropertyEvent needCloseVitalPropertyEvent) {
        View vitalPropertyContainer = mSearchTabViewModel.getVitalPropertyContainer();
        
        if (vitalPropertyContainer != null &&
            vitalPropertyContainer.getVisibility() == View.VISIBLE) {
            mSearchTabViewModel.showVitalProperty(false);
            mSearchTabViewModel.showBottomBar(true);
        }
    }
    
    
    @Override
    public void onMapPropertyClick(PropertyResultEntry entry) {
        View vitalPropertyContainer = mSearchTabViewModel.getVitalPropertyContainer();
        if (vitalPropertyContainer != null) {
            if (vitalPropertyContainer.getVisibility() != View.VISIBLE) {
                mSearchTabViewModel.showBottomBar(false);
                mSearchTabViewModel.showVitalProperty(true);
            }
            mVitalPropertyView.setProperty(entry);
        }
    }
    
    @Override
    public void onClickRetry() {
        initMainLayout();
    }
    
    
    @Override
    public void onSaveSearch(String savedSearchName) {
    
    }
    
    private void updateTextSearchBar(String text) {
        if (mBinding != null && mBinding.get().toolbar != null &&
            mBinding.get().toolbar.searchBar != null) {
            mBinding.get().toolbar.searchBar.setText(text);
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mVitalPropertyView = null;
        mMapViewFragment = null;
        mFilterNavigationFragment = null;
    }
    
    private void initBinding() {
        
        mBinding.get().mainContent.addProperty.setOnClickListener(addProperty -> {
            ViewUtils.startActivity(getActivity(), AddEditPropertyActivity.class);
        });
        
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
                SavedSearchDialog searchDialog = SavedSearchDialog.newInstance();
                searchDialog.setSaveSearchListener(SearchTabMainFragment.this);
                searchDialog.setTargetFragment(SearchTabMainFragment.this,
                    SavedSearchDialog.REQUEST_CODE);
                if (getFragmentManager() != null) {
                    searchDialog.show(getFragmentManager(), TAG);
                }
            }
            
            @Override
            public void onCLickSort() {
            
            }
            
            @Override
            public void onClickReload() {
                if (mMapViewFragment != null && mMapViewFragment.isVisible()) {
                    mBus.post(new ReloadSearchEvent());
                    return;
                }
                mBus.post(new ReInitMapFragmentEvent());
            }
        });
    }
    
    private void initMainLayout() {
        if (NetworkUtils.isNetworkAvailable(this.getContext())) {
            setupMapView();
            getMapViewFragment().setMapFragmentClickCallBack(this);
            mBinding.get().inforBar.showHideButtonSort(false);
        } else {
            setupLoadingFragment();
            mBinding.get().inforBar.showHideErrorContainer(true);
            mBinding.get().inforBar.showHideButtonSort(false);
        }
    }
    
    private void initVitalProperty() {
        if (getActivity() != null) {
            mVitalPropertyView = new VitalPropertyView(this.getActivity());
            
            ((MainActivity) getActivity()).getBinding().vitalPropertyContainer
                .addView(mVitalPropertyView);
            mVitalPropertyView.setOnClickStarListener(this);
            ViewUtils.showHideView(
                ((MainActivity) getActivity()).getBinding().vitalPropertyContainer,
                false);
        }
    }
    
    
    public void openDrawer() {
        mBinding.get().drawerLayout.openDrawer(Gravity.END);
    }
    
    public void closeDrawer() {
        mBinding.get().drawerLayout.closeDrawer(Gravity.END);
    }
    
    private void setupToolBar(View view) {
        bindViewOnToolBar(view);
    }
    
    private void setupFilterNavigation() {
        if (getActivity().getSupportFragmentManager() == null) {
            throw new NullPointerException("Fragment manager is null");
        }
        
        mFilterNavigationFragment = FilterNavigationFragment
            .newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.filter_nav, mFilterNavigationFragment, FilterNavigationFragment.TAG)
            .commit();
    }
    
    private void setupMapView() {
        if (getActivity().getSupportFragmentManager() == null) {
            throw new NullPointerException("Fragment manager is null");
        }
        
        mMapViewFragment = MapViewFragment.newInstance();
        
        Runnable attachRunnableMapView = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(getActivity().getSupportFragmentManager())
            .setContainerId(R.id.fragment_map_view)
            .setFragment(mMapViewFragment)
            .setAnimationIn(android.R.anim.fade_in)
            .setAnimationOut(android.R.anim.fade_out)
            .setAddToBackTrack(false)
            .build();
        
        new Handler().postDelayed(attachRunnableMapView, 50);
    }
    
    private void setupLoadingFragment() {
        if (getActivity().getSupportFragmentManager() == null) {
            throw new NullPointerException("Fragment manager is null");
        }
        LoadingFragment loadingFragment = LoadingFragment.newInstance();
        loadingFragment.setLoadingFragmentListener(this);
        
        Runnable attachRunnableLoadingFragment = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(getActivity().getSupportFragmentManager())
            .setContainerId(R.id.fragment_map_view)
            .setFragment(loadingFragment)
            .setAddToBackTrack(false)
            .build();
        
        new Handler().postDelayed(attachRunnableLoadingFragment, 50);
    }
    
    private void bindViewOnToolBar(View view) {
        mBinding.get().toolbar.searchBar.setTypeface(null, Typeface.NORMAL);
        mBinding.get().toolbar.searchBar.setOnClickListener(
            viewSearchBar -> ViewUtils.startActivity(getActivity(),
                AutoCompletePlaceActivity.class));
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
    
    @Override
    public void onCheckStar(PropertyResultEntry item) {
        Timber.d("Favorite");
        item.setFavorite(true);
        mBus.post(new OnFavouriteEvent(item));
    }
    
    @Override
    public void onUnCheckStar(PropertyResultEntry item) {
        Timber.d("Un-Favorite");
    
        item.setFavorite(false);
        mBus.post(new OnFavouriteEvent(item));
    }
}