package com.odauday.ui.admin.usermanager.userdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.databinding.FragmentProfileDetailBinding;
import com.odauday.model.User;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.propertymanager.ActivityPropertyManager;
import com.odauday.utils.TextUtils;
import com.odauday.viewmodel.BaseViewModel;

/**
 * Created by kunsubin on 6/11/2018.
 */

public class FragmentUserDetail extends BaseMVVMFragment<FragmentProfileDetailBinding> {
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_detail;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    public static FragmentUserDetail newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentUserDetail fragment = new FragmentUserDetail();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        showDetailUser();
        setListener();
    }
    
    private void initView() {
        mBinding.get().changePassword.setVisibility(View.GONE);
        mBinding.get().logOut.setVisibility(View.GONE);
        mBinding.get().subscriber.setVisibility(View.GONE);
    }
    private void showDetailUser(){
        User user=((ActivityUserDetail)getActivity()).getUser();
        if(user==null){
            return;
        }
        mBinding.get().phone.setValue(user.getPhone());
        String facebookProfileUrl;
        if (TextUtils.isEmpty(user.getFacebookId())) {
            facebookProfileUrl = getString(R.string.n_a);
        } else {
            facebookProfileUrl = getString(R.string.facebook_url, user.getFacebookId());
        }
        mBinding.get().facebook.setValue(facebookProfileUrl);
        mBinding.get().role.setValue(TextUtils.capitalize(user.getRole()));
        if (user.getStatus().equals(User.STATUS_PENDING)) {
            mBinding.get().status.getSubText().setVisibility(View.VISIBLE);
            mBinding.get().status.setSubText(R.string.message_you_should_active_account);
        } else if (user.getStatus().equals(User.STATUS_DISABLED)) {
            mBinding.get().status.getSubText().setVisibility(View.VISIBLE);
            mBinding.get().status.setSubText(R.string.message_your_account_is_disabled);
        }
        mBinding.get().status.setValue(TextUtils.capitalize(user.getStatus()));
        mBinding.get().amount.setValue(String.valueOf(user.getAmount()));
        
    }
    private void setListener(){
        mBinding.get().listProperty.setListener(this::onClickListProperty);
    }
    
    private void onClickListProperty() {
        User user=((ActivityUserDetail)getActivity()).getUser();
        if(user==null){
            return;
        }
        Intent intent=new Intent(getActivity(), ActivityPropertyManager.class);
        intent.putExtra("user_id",user.getId());
        startActivity(intent);
    }
    @Override
    protected void injectDI() {
    
    }
}
