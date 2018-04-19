package com.odauday.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import com.odauday.R;
import com.odauday.databinding.FragmentMoreTabMainBinding;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.propertymanager.ActivityPropertyManager;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class MoreTabMainFragment extends BaseMVVMFragment<FragmentMoreTabMainBinding> {
    public static final String TAG = NavigationTab.MORE_TAB.getNameTab();
    private List<MenuItemMore> mMenuItemMores;
    private MoreAdapter mMoreAdapter;
    
    private MoreAdapter.OnClickMenuMoreListener mOnClickMenuMoreListener=item -> {
        switch (item.getId()){
            case ItemType.PROPERTY_MANAGER:
                Timber.tag(TAG).d("Click: "+item.getName());
                Intent intent=new Intent(getActivity(), ActivityPropertyManager.class);
                startActivity(intent);
                break;
            case ItemType.POST_NEWS:
                Timber.tag(TAG).d("Click: "+item.getName());
                break;
            case ItemType.CONFIRM_PROPERTY:
                Timber.tag(TAG).d("Click: "+item.getName());
                break;
            case ItemType.SETTINGS:
                Timber.tag(TAG).d("Click: "+item.getName());
                break;
            case ItemType.FEEDBACK:
                Timber.tag(TAG).d("Click: "+item.getName());
                break;
            case ItemType.SHARE_THIS_APP:
                Timber.tag(TAG).d("Click: "+item.getName());
                break;
                default:
                    break;
        }
    };
    
    public static MoreTabMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MoreTabMainFragment fragment = new MoreTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }
    
    private void init() {
        mMenuItemMores=new ArrayList<>();
        mMoreAdapter=new MoreAdapter();
        mBinding.get().recycleViewMore.setLayoutManager(new GridLayoutManager(getContext(),1));
        mBinding.get().recycleViewMore.setNestedScrollingEnabled(false);
        mMoreAdapter.setOnClickMenuMoreListener(mOnClickMenuMoreListener);
        mBinding.get().recycleViewMore.setAdapter(mMoreAdapter);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_more_tab_main;
    }
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    @Override
    public void onStart() {
        super.onStart();
        getMenu();
        showMenu();
    }
    private void getMenu() {
        mMenuItemMores=MenuItemMore.getListMenuMore(getActivity(),"admin");
    }
    private void showMenu() {
        if(mMenuItemMores!=null&&mMenuItemMores.size()>0){
            mMoreAdapter.setData(mMenuItemMores);
        }
    }
    @Override
    protected void processingTaskFromViewModel() {

    }
}
