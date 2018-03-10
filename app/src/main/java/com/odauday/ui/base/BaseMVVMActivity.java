package com.odauday.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.odauday.di.Injectable;
import com.odauday.viewmodel.BaseViewModel;
import dagger.android.AndroidInjection;

/**
 * Created by infamouSs on 3/5/18.
 */

public abstract class BaseMVVMActivity<VM extends BaseViewModel,
          VB extends ViewDataBinding> extends BaseActivity implements Injectable {
    
    protected VM mViewModel;
    protected VB mBinding;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDI();
        super.onCreate(savedInstanceState);
        
        mViewModel = buildViewModel();
        mBinding = buildBinding();
    }
    
    protected void injectDI() {
        AndroidInjection.inject(this);
    }
    
    protected abstract VM buildViewModel();
    
    
    protected VB buildBinding() {
        return DataBindingUtil.setContentView(this, getLayoutId());
    }
    
    public VM getViewModel() {
        return mViewModel;
    }
    
    public void setViewModel(VM viewModel) {
        mViewModel = viewModel;
    }
    
    public VB getBinding() {
        return mBinding;
    }
    
    public void setBinding(VB binding) {
        mBinding = binding;
    }
    
    @Override
    protected void onDestroy() {
        mBinding = null;
        super.onDestroy();
    }
}
