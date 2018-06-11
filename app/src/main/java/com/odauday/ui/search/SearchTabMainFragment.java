package com.odauday.ui.search;

import android.graphics.Typeface;
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
import com.odauday.config.Constants.Task;
import com.odauday.data.SearchPropertyRepository;
import com.odauday.data.SearchPropertyState;
import com.odauday.data.local.cache.MapPreferenceHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.property.model.CoreSearchRequest;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.data.remote.property.model.SearchRequest;
import com.odauday.data.remote.property.model.SearchResult;
import com.odauday.databinding.FragmentSearchTabMainBinding;
import com.odauday.model.Search;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.common.AttachFragmentRunnable;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceActivity;
import com.odauday.ui.search.common.SearchCriteria;
import com.odauday.ui.search.common.event.NeedCloseVitalPropertyEvent;
import com.odauday.ui.search.common.event.OnCompleteDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnErrorDownloadPropertyEvent;
import com.odauday.ui.search.common.event.OnFavouriteEvent;
import com.odauday.ui.search.common.event.OnNeedLoadWithSavedSearch;
import com.odauday.ui.search.common.event.OnShowSortDialogEvent;
import com.odauday.ui.search.common.event.ReInitMapFragmentEvent;
import com.odauday.ui.search.common.event.ReloadSearchEvent;
import com.odauday.ui.search.common.view.InformationBar.InformationBarListener;
import com.odauday.ui.search.common.view.LoadingFragment;
import com.odauday.ui.search.common.view.LoadingFragment.LoadingFragmentListener;
import com.odauday.ui.search.common.view.VitalPropertyView;
import com.odauday.ui.search.listview.ListViewFragment;
import com.odauday.ui.search.mapview.MapViewFragment;
import com.odauday.ui.search.mapview.MapViewFragment.MapFragmentClickCallBack;
import com.odauday.ui.search.navigation.FilterNavigationFragment;
import com.odauday.ui.search.navigation.FilterNavigationFragment.OnCompleteRefineFilter;
import com.odauday.ui.view.DialogWithTextBox;
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
                                                                                          LoadingFragmentListener,
                                                                                          OnClickStarListener<PropertyResultEntry>,
                                                                                          SearchTabContract {
    
    
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
    private ListViewFragment mListViewFragment;
    private boolean mIsShowingVital = false;
    
    
    private Runnable mRunnableHiddenMap = () -> mBinding.get().mainContent.fragmentMapView
        .setVisibility(View.GONE);
    
    private PropertyResultEntry mVitalPropertyEntry;
    
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
        //
        //        if (mMapViewFragment != null && mMapViewFragment.isVisible()) {
        //            mBinding.get().inforBar.showHideButtonSort(false);
        //        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorSearchProperty(OnErrorDownloadPropertyEvent errorDownloadProperty) {
        mBinding.get().inforBar.setTextError(getString(R.string.message_cannot_connect_to_service));
        mBinding.get().inforBar.showHideButtonSort(false);
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
        mSearchTabViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        if (resource.task.equals(Task.TASK_CREATE_SAVED_SEARCH)) {
                            onFailSavedSearch((Throwable) resource.data);
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(Task.TASK_CREATE_SAVED_SEARCH)) {
                            onSuccessSavedSearch((MessageResponse) resource.data);
                        }
                        break;
                    case LOADING:
                        break;
                    default:
                        break;
                }
            }
        });
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
        mIsShowingVital = false;
    }
    
    
    @Override
    public void onMapPropertyClick(PropertyResultEntry entry) {
        View vitalPropertyContainer = mSearchTabViewModel.getVitalPropertyContainer();
        if (vitalPropertyContainer != null) {
            if (vitalPropertyContainer.getVisibility() != View.VISIBLE) {
                mSearchTabViewModel.showBottomBar(false);
                mSearchTabViewModel.showVitalProperty(true);
            }
            if (!entry.equals(mVitalPropertyEntry)) {
                mVitalPropertyEntry = entry;
                mVitalPropertyView.setProperty(mVitalPropertyEntry);
            }
            mIsShowingVital = true;
        }
    }
    
    @Override
    public void onClickRetry() {
        initMainLayout();
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
                showList();
                hideMap();
            }
            
            @Override
            public void onShowMapView() {
                hideList();
                showMap();
            }
        });
        mBinding.get().inforBar.setListener(new InformationBarListener() {
            @Override
            public void onClickSaveSearch() {
                DialogWithTextBox searchDialog = DialogWithTextBox
                    .newInstance(R.string.txt_save_search_heading, R.string.txt_name, 0);
                searchDialog.setListener(searchName -> saveSearch(searchName));
                searchDialog.setTargetFragment(SearchTabMainFragment.this,
                    DialogWithTextBox.REQUEST_CODE);
                if (getFragmentManager() != null) {
                    searchDialog.show(getFragmentManager(), TAG);
                }
            }
            
            @Override
            public void onCLickSort() {
                mBus.post(new OnShowSortDialogEvent());
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
    
    private void updateTextSearchBar(String text) {
        if (mBinding != null && mBinding.get().toolbar != null &&
            mBinding.get().toolbar.searchBar != null) {
            mBinding.get().toolbar.searchBar.setText(text);
        }
    }
    
    
    private void showList() {
        mBinding.get().mainContent.fragmentListView.setVisibility(View.VISIBLE);
        mBinding.get().mainContent.fragmentListView.startAnimation(
            AnimationUtils.loadAnimation(getContext(), R.anim.anim_slide_in_from_bottom));
        
        View vitalPropertyContainer = mSearchTabViewModel.getVitalPropertyContainer();
        
        if (vitalPropertyContainer.getVisibility() == View.VISIBLE) {
            mIsShowingVital = true;
            mSearchTabViewModel.showVitalProperty(false);
            mSearchTabViewModel.showBottomBar(true);
            
        }
        if (mBinding.get().inforBar.getContainerError().getVisibility() != View.VISIBLE) {
            mBinding.get().inforBar.showHideButtonSort(true);
        }
    }
    
    private void showMap() {
        mBinding.get().mainContent.fragmentMapView.removeCallbacks(mRunnableHiddenMap);
        mBinding.get().mainContent.fragmentMapView.setVisibility(View.VISIBLE);
        View vitalPropertyContainer = mSearchTabViewModel.getVitalPropertyContainer();
        
        if (mIsShowingVital && vitalPropertyContainer.getVisibility() != View.VISIBLE) {
            mSearchTabViewModel.showVitalProperty(true);
            mSearchTabViewModel.showBottomBar(false);
        }
        mBinding.get().inforBar.showHideButtonSort(false);
        
    }
    
    private void hideMap() {
        mBinding.get().mainContent.fragmentMapView.postDelayed(mRunnableHiddenMap, 150);
    }
    
    private void hideList() {
        mBinding.get().mainContent.fragmentListView.setVisibility(View.GONE);
        mBinding.get().mainContent.fragmentListView.startAnimation(
            AnimationUtils.loadAnimation(getContext(), R.anim.anim_slide_out_to_bottom));
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
        
        setupListView();
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
    
    private void setupListView() {
        if (getActivity().getSupportFragmentManager() == null) {
            throw new NullPointerException("Fragment manager is null");
        }
        
        mListViewFragment = ListViewFragment.newInstance();
        
        Runnable attachRunnableMapView = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(getActivity().getSupportFragmentManager())
            .setContainerId(R.id.fragment_list_view)
            .setFragment(mListViewFragment)
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
        mSearchTabViewModel.checkFavorite(item.getId());
        
        item.setFavorite(true);
        mBus.post(new OnFavouriteEvent(item));
    }
    
    @Override
    public void onUnCheckStar(PropertyResultEntry item) {
        mSearchTabViewModel.unCheckFavorite(item.getId());
        
        item.setFavorite(false);
        mBus.post(new OnFavouriteEvent(item));
    }
    
    @Override
    public void onSuccessSavedSearch(MessageResponse response) {
        Toast.makeText(getContext(), R.string.message_search_is_saved_successfully,
            Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFailSavedSearch(Throwable throwable) {
    
    }
    
    
    public void loadSavedSearch(Search search) {
        EventBus.getDefault().post(new OnNeedLoadWithSavedSearch(search));
    }
    
    private void saveSearch(String savedSearchName) {
        Search search = new Search();
        search.setId(TextUtils.generatorUUID());
        SearchRequest searchRequest = mSearchPropertyRepository.getCurrentSearchRequest();
        CoreSearchRequest core = searchRequest.getCore();
        search.setLatitude(core.getCenter().getLatitude());
        search.setLongitude(core.getCenter().getLongitude());
        
        search.setLatitude_sw(core.getBounds()[0].getLatitude());
        search.setLongitude_sw(core.getBounds()[0].getLongitude());
        
        search.setLatitude_ns(core.getBounds()[1].getLatitude());
        search.setLongitude_ns(core.getBounds()[1].getLongitude());
        
        search.setName(savedSearchName);
        
        mSearchTabViewModel.createSearch(search);
        
    }
    
}