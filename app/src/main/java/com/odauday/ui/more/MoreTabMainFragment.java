package com.odauday.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import com.google.gson.Gson;
import com.odauday.R;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.databinding.FragmentMoreTabMainBinding;
import com.odauday.model.User;
import com.odauday.ui.ClearMemory;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.admin.ActivityAdminManager;
import com.odauday.ui.propertymanager.ActivityPropertyManager;
import com.odauday.ui.settings.ActivitySettings;
import com.odauday.ui.view.bottomnav.NavigationTab;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by infamouSs on 3/31/18.
 */

public class MoreTabMainFragment extends BaseMVVMFragment<FragmentMoreTabMainBinding> implements
                                                                                      ClearMemory{
    public static final String TAG = NavigationTab.MORE_TAB.getNameTab();
    @Inject
    PreferencesHelper mPreferencesHelper;
    private List<MenuItemMore> mMenuItemMores;
    private MoreAdapter mMoreAdapter;
    
    private MoreAdapter.OnClickMenuMoreListener mOnClickMenuMoreListener = item -> {
        switch (item.getId()) {
            case ItemType.PROPERTY_MANAGER:
                Timber.tag(TAG).d("Click: " + item.getName());
                ViewUtils.startActivity(getActivity(), ActivityPropertyManager.class);
                break;
            case ItemType.POST_NEWS:
                Timber.tag(TAG).d("Click: " + item.getName());
                break;
            case ItemType.ADMIN_MANAGER:
                ViewUtils.startActivity(getActivity(), ActivityAdminManager.class);
                Timber.tag(TAG).d("Click: " + item.getName());
                break;
            case ItemType.SETTINGS:
                Timber.tag(TAG).d("Click: " + item.getName());
                Intent intentSettings = new Intent(getActivity(), ActivitySettings.class);
                startActivity(intentSettings);
                break;
            case ItemType.FEEDBACK:
                Timber.tag(TAG).d("Click: " + item.getName());
                break;
            case ItemType.SHARE_THIS_APP:
                Timber.tag(TAG).d("Click: " + item.getName());
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
        showInfo();
        getMenu();
        showMenu();
    }
    
    private void init() {
        mMenuItemMores = new ArrayList<>();
        mMoreAdapter = new MoreAdapter();
        mBinding.get().recycleViewMore.setLayoutManager(new GridLayoutManager(getContext(), 1));
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

    private void showInfo() {
        String string = mPreferencesHelper.get(PrefKey.CURRENT_USER, "");
        User user = new Gson().fromJson(string, User.class);
        if (user != null) {
            mBinding.get().txtUserName.setText(user.getDisplayName());
            mBinding.get().txtEmail.setText(user.getEmail());
        }
    }

    private void getMenu() {
        mMenuItemMores = MenuItemMore.getListMenuMore(getActivity(), "admin");
    }

    private void showMenu() {
        if (mMenuItemMores != null && mMenuItemMores.size() > 0) {
            mMoreAdapter.setData(mMenuItemMores);
        }
    }

    @Override
    protected void processingTaskFromViewModel() {

    }
    
    @Override
    public void onStop() {
        clearMemory();
        super.onStop();
    }
    
    @Override
    public void clearMemory() {
        mMenuItemMores=null;
        mMoreAdapter=null;
    }
}
