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

public abstract class BaseMVVMActivity<VB extends ViewDataBinding> extends BaseActivity implements
                                                                                        Injectable {
    
    //====================== Variable =============================//
    
    protected VB mBinding;
    
    //====================== Override Method ======================//
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDI();
        super.onCreate(savedInstanceState);
        
        mBinding = buildBinding();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        processingTaskFromViewModel();
    }
    
    @Override
    protected void onDestroy() {
        mBinding = null;
        super.onDestroy();
    }
    
    //====================== BaseMethod ==========================//
    
    protected abstract BaseViewModel getViewModel(String tag);
    
    protected void injectDI() {
        AndroidInjection.inject(this);
    }
    
    protected abstract void processingTaskFromViewModel();
    
    protected VB buildBinding() {
        return DataBindingUtil.setContentView(this, getLayoutId());
    }
    
    public VB getBinding() {
        return mBinding;
    }
    
    public void setBinding(VB binding) {
        mBinding = binding;
    }
}
