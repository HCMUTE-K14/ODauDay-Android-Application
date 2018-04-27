package com.odauday.ui.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.di.Injectable;
import com.odauday.ui.common.AutoClearedData;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.support.AndroidSupportInjection;


/**
 * Created by infamouSs on 3/5/18.
 */


public abstract class BaseMVVMFragment<VB extends ViewDataBinding> extends BaseFragment implements
                                                                                        Injectable {
    
    //====================== Variable Method =========================//
    protected AutoClearedData<VB> mBinding;
    
    
    //====================== Override Method =========================//
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        VB binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        
        mBinding = new AutoClearedData<>(this, binding);
        
        return binding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        processingTaskFromViewModel();
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            injectDI();
        }
        super.onAttach(activity);
    }
    
    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            injectDI();
        }
        super.onAttach(context);
    }
    
    //====================== Base Method =========================//
    
    protected void injectDI() {
        AndroidSupportInjection.inject(this);
    }
    
    protected abstract BaseViewModel getViewModel(String tag);
    
    protected abstract void processingTaskFromViewModel();
    
    public AutoClearedData<VB> getBinding() {
        return mBinding;
    }
}
