package com.odauday.ui.addeditproperty.step2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.databinding.FragmentAddEditStep2Binding;
import com.odauday.model.MyProperty;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.addeditproperty.BaseStepFragment;
import com.odauday.utils.TextUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/24/18.
 */
public class Step2Fragment extends BaseStepFragment<FragmentAddEditStep2Binding> {
    
    public static final String TAG = Step2Fragment.class.getSimpleName();
    
    public static final int STEP = 2;
    
    private MyProperty mProperty;
    
    
    public static Step2Fragment newInstance(MyProperty myProperty) {
        
        Bundle args = new Bundle();
        
        Step2Fragment fragment = new Step2Fragment();
        args.putParcelable(AddEditPropertyActivity.EXTRA_PROPERTY, myProperty);
        
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_edit_step_2;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(AddEditPropertyActivity.EXTRA_PROPERTY);
        }
        if (mProperty == null) {
            mProperty = new MyProperty();
        }
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mNextButton = mBinding.get().buttonNav.btnNextStep;
        mBackButton = mBinding.get().buttonNav.btnBackStep;
        super.onViewCreated(view, savedInstanceState);
    }
    
    @Override
    public void initNextBackListener() {
        if (getBackButton() != null) {
            getBackButton().setOnClickListener(backButton -> {
                mNavigationStepListener.navigate(getStep(), getBackStep());
            });
        }
        if (getNextButton() != null) {
            getNextButton().setOnClickListener(this::onNextStep);
        }
    }
    
    public void onNextStep(View view) {
        
        if (!isSelectLandSize()) {
            mBinding.get().txtLandSize
                      .setError(getString(R.string.message_land_size_is_required));
            mBinding.get().txtLandSize.requestFocus();
            
            return;
        }
        if (!isSelectBedroom()) {
            mBinding.get().txtBedroom
                      .setError(getString(R.string.message_bedroom_is_required));
            mBinding.get().txtBedroom.requestFocus();
            
            return;
        }
        
        if (!isSelectBathroom()) {
            mBinding.get().txtBathroom
                      .setError(getString(R.string.message_bathroom_is_required));
            mBinding.get().txtBathroom.requestFocus();
            return;
        }
        
        if (!isSelectParking()) {
            mBinding.get().txtParking
                      .setError(getString(R.string.message_parking_is_required));
            mBinding.get().txtParking.requestFocus();
            return;
        }
        
        mProperty.setSize(Double.valueOf(mBinding.get().txtLandSize.getText().toString()));
        mProperty.setNumOfBedRoom(Integer.valueOf(mBinding.get().txtBedroom.getText().toString()));
        mProperty.setNumOfBathRoom(
                  Integer.valueOf(mBinding.get().txtBathroom.getText().toString()));
        mProperty.setNumOfParking(Integer.valueOf(mBinding.get().txtParking.getText().toString()));
        
        EventBus.getDefault().post(new OnCompleteStep2Event(mProperty));
        
        mNavigationStepListener.navigate(getStep(), getNextStep());
        
    }
    
    private boolean isSelectLandSize() {
        return !TextUtils.isEmpty(mBinding.get().txtLandSize.getText().toString());
    }
    
    private boolean isSelectBedroom() {
        return !TextUtils.isEmpty(mBinding.get().txtBedroom.getText().toString());
    }
    
    private boolean isSelectBathroom() {
        return !TextUtils.isEmpty(mBinding.get().txtBathroom.getText().toString());
    }
    
    private boolean isSelectParking() {
        return !TextUtils.isEmpty(mBinding.get().txtParking.getText().toString());
    }
    
    
    @Override
    public int getStep() {
        return STEP;
    }
}
