package com.odauday.ui.addeditproperty;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.common.AutoClearedData;

/**
 * Created by infamouSs on 4/25/18.
 */
public abstract class BaseStepFragment<VB extends ViewDataBinding> extends BaseMVVMFragment<VB> {
    
    protected AutoClearedData<VB> mBinding;
    
    protected Button mNextButton;
    protected Button mBackButton;
    
    protected NavigationStepListener mNavigationStepListener;
    
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
        
        initNextBackListener();
    }
    
    public void initNextBackListener() {
        if (getNextButton() != null) {
            getNextButton().setOnClickListener(this::onNextStep);
        }
        if (getBackButton() != null) {
            getBackButton().setOnClickListener(this::onBackStep);
        }
    }
    
    public AutoClearedData<VB> getBinding() {
        return mBinding;
    }
    
    public abstract int getStep();
    
    public int getBackStep() {
        return getStep() - 1;
    }
    
    public int getNextStep() {
        return getStep() + 1;
    }
    
    public Button getBackButton() {
        return mBackButton;
    }
    
    public Button getNextButton() {
        return mNextButton;
    }
    
    public abstract void onNextStep(View view);
    
    public void onBackStep(View view) {
        mNavigationStepListener.navigate(getStep(), getBackStep());
    }
    
    public void setNavigationStepListener(
              NavigationStepListener navigationStepListener) {
        mNavigationStepListener = navigationStepListener;
    }
    
    public interface NavigationStepListener {
        
        void navigate(int from, int to);
    }
}
