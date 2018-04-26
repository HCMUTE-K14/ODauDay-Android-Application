package com.odauday.ui.addeditproperty.step3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.databinding.FragmentAddEditStep3Binding;
import com.odauday.model.MyProperty;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.addeditproperty.BaseStepFragment;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/24/18.
 */
public class Step3Fragment extends BaseStepFragment<FragmentAddEditStep3Binding> {
    
    public static final String TAG = Step3Fragment.class.getSimpleName();
    
    public static final int STEP = 3;
    
    private MyProperty mProperty;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_edit_step_3;
    }
    
    public static Step3Fragment newInstance(MyProperty myProperty) {
        
        Bundle args = new Bundle();
        
        Step3Fragment fragment = new Step3Fragment();
        args.putParcelable(AddEditPropertyActivity.EXTRA_PROPERTY, myProperty);
        
        fragment.setArguments(args);
        return fragment;
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
        mProperty.setDescription(mBinding.get().txtDescription.getText().toString());
        mProperty.setTags(null);
        
        EventBus.getDefault().post(new OnCompleteStep3Event(mProperty));
        mNavigationStepListener.navigate(getStep(), getNextStep());
    }
    
    @Override
    public int getStep() {
        return STEP;
    }
}
