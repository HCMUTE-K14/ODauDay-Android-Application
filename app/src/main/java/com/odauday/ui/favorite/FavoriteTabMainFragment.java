package com.odauday.ui.favorite;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Type;
import com.odauday.data.remote.model.FavoriteResponse;
import com.odauday.databinding.FragmentFavoriteTabMainBinding;
import com.odauday.model.Property;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.view.HeaderFavoriteView;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.SnackBarUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class FavoriteTabMainFragment extends BaseMVVMFragment<FragmentFavoriteTabMainBinding> implements FavoriteContract {
    
    
    public static final String TAG = NavigationTab.FAVORITE_TAB.getNameTab();
    
    @Inject
    FavoriteViewModel mFavoriteViewModel;
    private FavoriteAdapter mFavoriteAdapter;
    private EmptyFavoriteAdapter mEmptyFavoriteAdapter;
    private List<Property> mProperties;
    HeaderFavoriteView mHeaderFavoriteView;
    private ProgressDialog mProgressDialog;
    enum SortType{
        LAST_ADDED,LOWEST_PRICE,HIGHEST_PRICE
    }
    enum FilterType{
        ALL, BUY,RENT
    }
    private SortType SORT_TYPE=SortType.LAST_ADDED;
    private FilterType FILTER_TYPE=FilterType.ALL;
    
    
    public static FavoriteTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FavoriteTabMainFragment fragment = new FavoriteTabMainFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    //Event click share
    HeaderFavoriteView.OnClickShareListener mOnClickShareListener=view -> {
        Timber.tag(TAG).d("Share click");
    };
    //Event click open map
    HeaderFavoriteView.OnClickMapListener mOnClickMapListener=view -> {
        Timber.tag(TAG).d("Map click");
    };
    HeaderFavoriteView.OnItemClickMenuSort mOnItemClickMenuSort=item -> {
        Timber.tag(TAG).d(item.getTitle().toString());
        switch (item.getItemId()){
            case R.id.last_added:
                SORT_TYPE=SortType.LAST_ADDED;
                showDataView();
                break;
            case R.id.lowest_price:
                SORT_TYPE=SortType.LOWEST_PRICE;
                showDataView();
                break;
            case R.id.highest_price:
                SORT_TYPE=SortType.HIGHEST_PRICE;
                showDataView();
                break;
        }
        mHeaderFavoriteView.setTextViewSort(item.getTitle().toString());
    };
    HeaderFavoriteView.OnItemClickMenuFilter mOnItemClickMenuFilter=item -> {
        Timber.tag(TAG).d(item.getTitle().toString());
        switch (item.getItemId()){
            case R.id.all:
                FILTER_TYPE=FilterType.ALL;
                showDataView();
                break;
            case R.id.buy:
                FILTER_TYPE=FilterType.BUY;
                showDataView();
                break;
            case R.id.rent:
                FILTER_TYPE=FilterType.RENT;
                showDataView();
                break;
        }
        mHeaderFavoriteView.setTextViewFilter(item.getTitle().toString());
    };
    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite_tab_main;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initHeaderView();
    }
    private void initHeaderView() {
        mProperties=new ArrayList<>();
        mHeaderFavoriteView=mBinding.get().headerFavorite;
        
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getActivity().getString(R.string.txt_progress));
        mProgressDialog.show();
        
        mHeaderFavoriteView.setTitle(getResources().getString(R.string.txt_shortlist));
        mHeaderFavoriteView.setOnClickShareListener(mOnClickShareListener);
        mHeaderFavoriteView.setOnClickMapListener(mOnClickMapListener);
        mHeaderFavoriteView.setOnItemClickMenuSort(mOnItemClickMenuSort);
        mHeaderFavoriteView.setOnItemClickMenuFilter(mOnItemClickMenuFilter);
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mFavoriteViewModel;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        getFavorite();
    }
    private void getFavorite(){
        mFavoriteViewModel.getFavoritePropertyByUser("a88211ba-3077-40e2-9685-5ab450abb114");
    }
    @Override
    protected void processingTaskFromViewModel() {
        mFavoriteViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        loading(false);
                        onFailure((Exception) resource.data);
                        break;
                    case SUCCESS:
                        loading(false);
                        onSuccess(resource.data);
                        break;
                    case LOADING:
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });
        mBinding.get().recycleViewFavorite.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mFavoriteAdapter=new FavoriteAdapter();
        mEmptyFavoriteAdapter=new EmptyFavoriteAdapter();

    }
    private void showDataView(){
        if(mProperties==null||mProperties.isEmpty())
            return;
        List<Property> propertyList=getPropertyFilter();
        if(propertyList!=null&&propertyList.size()>0){
            
            if(mBinding.get().recycleViewFavorite.getAdapter() instanceof FavoriteAdapter){
            
            }else {
                mBinding.get().recycleViewFavorite.setAdapter(mFavoriteAdapter);
            }
            mFavoriteAdapter.setData(propertyList);
        }
       
    }
    private List<Property> getPropertyFilter(){
        List<Property> list=mProperties;
        switch (FILTER_TYPE){
            case ALL:
                break;
            case BUY:
                list=getList(list,Type.BUY);
                break;
            case RENT:
                list=getList(list,Type.RENT);
                break;
            default:
                    break;
        }
        /*End list null or empty*/
        if(list==null||list.isEmpty())
            return list;
        
        switch (SORT_TYPE){
            case LAST_ADDED:
                break;
            case LOWEST_PRICE:
                list=getListOrderByLowestPrice(list);
                break;
            case HIGHEST_PRICE:
                list=getListOrderByHighestPrice(list);
                break;
            default:
                    break;
        }
        return list;
    }
    
   
    private List<Property> getList(List<Property> list,String type) {
        List<Property> propertyList=new ArrayList<>();
        for (Property property: list) {
            if(property.getType_id().equals(type)){
                propertyList.add(property);
            }
        }
        return propertyList;
    }
    private List<Property> getListOrderByLowestPrice(List<Property> list) {
        List<Property> propertyList=list;
        Comparator<Property> comparator=(property1, property2) -> {
            if (property1.getPrice() < property2.getPrice()) return -1;
            if (property1.getPrice() > property2.getPrice()) return 1;
            return 0;
        };
        Collections.sort(propertyList,comparator);
        return propertyList;
    }
    private List<Property> getListOrderByHighestPrice(List<Property> list) {
        List<Property> propertyList=list;
        Comparator<Property> comparator=(property1, property2) -> {
            if (property1.getPrice() > property2.getPrice()) return -1;
            if (property1.getPrice() < property2.getPrice()) return 1;
            return 0;
        };
        Collections.sort(propertyList,comparator);
        return propertyList;
    }
    @Override
    public void loading(boolean isLoading) {
        if(isLoading){
            mProgressDialog.show();
        }else {
            mProgressDialog.dismiss();
        }
        Timber.tag(TAG).d("Loading");
    }
    @Override
    public void onSuccess(Object object) {
        FavoriteResponse favoriteResponse=(FavoriteResponse)object;
        if(favoriteResponse!=null){
            List<Property> list=favoriteResponse.getProperties();
            if(list!=null){
                mProperties=list;
                showDataView();
            }
            Timber.tag(TAG).d(list.get(0).getName());
        }else {
            Timber.tag(TAG).d("Null Favorite");
            mBinding.get().recycleViewFavorite.setAdapter(mEmptyFavoriteAdapter);
        }
    }
    @Override
    public void onFailure(Exception ex) {
        
        Timber.tag(TAG).d(ex.getMessage());
        
        mBinding.get().recycleViewFavorite.setAdapter(mEmptyFavoriteAdapter);
        
       // List<ErrorResponse> errors = ((BaseException) ex).getErrors();
        String message;
        //if (errors == null || errors.isEmpty()) {
            message = getActivity().getString(R.string.empty_favorite);
       // } else {
            //message = errors.get(0).getMessage();
        //}
        SnackBarUtils.showSnackBar(mBinding.get().shortlist, message);
    }
    
}
