package com.odauday.ui.base;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.di.Injectable;
import com.odauday.ui.common.AutoClearedData;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/5/18.
 */

public abstract class BaseMVVMFragment<VM extends BaseViewModel,
          VB extends ViewDataBinding> extends BaseFragment implements Injectable {
    @Inject
    protected VM mViewModel;
    
    protected AutoClearedData<VB> mBinding;
    
    @Inject
    ViewModelProvider.Factory mFactory;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = buildViewModel();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
              @Nullable Bundle savedInstanceState) {
        VB binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        
        mBinding = new AutoClearedData<>(this, binding);
        
        return binding.getRoot();
    }
    
    protected abstract VM buildViewModel();
    
}
