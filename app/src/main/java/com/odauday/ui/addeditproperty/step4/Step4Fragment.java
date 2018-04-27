package com.odauday.ui.addeditproperty.step4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.esafirm.imagepicker.features.ImagePicker;
import com.odauday.R;
import com.odauday.databinding.FragmentAddEditStep4Binding;
import com.odauday.model.MyProperty;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.addeditproperty.BaseStepFragment;
import com.odauday.viewmodel.BaseViewModel;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/24/18.
 */
public class Step4Fragment extends BaseStepFragment<FragmentAddEditStep4Binding> {
    
    
    public static final String TAG = Step4Fragment.class.getSimpleName();
    
    public static final int STEP = 4;
    
    private static final int LIMIT_IMAGE = 6;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    
    public static Step4Fragment newInstance(MyProperty myProperty) {
        
        Bundle args = new Bundle();
        
        Step4Fragment fragment = new Step4Fragment();
        args.putParcelable(AddEditPropertyActivity.EXTRA_PROPERTY, myProperty);
        
        fragment.setArguments(args);
        return fragment;
    }
    
    @Inject
    EventBus mBus;
    
    private MyProperty mProperty;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_edit_step_4;
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
        
        if (getContext() != null) {
            mNextButton.setTextColor(ContextCompat.getColor(getContext(), R.color.alert_red));
        }
        mNextButton.setText(R.string.txt_done);
        
        mBinding.get().btnChooseImage.setOnClickListener(chooseImage -> {
            ImagePicker.create(this)
                      .folderMode(true)
                      .theme(R.style.AppTheme)
                      .limit(LIMIT_IMAGE)
                      .start(REQUEST_CODE_CHOOSE_IMAGE);
        });
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && data != null) {
        
        }
    }
    
    @Override
    public void onNextStep(View view) {
        
        mBus.post(new OnCompleteStep4Event(mProperty));
        mNavigationStepListener.navigate(getStep(), getNextStep());
    }
    
    @Override
    public int getStep() {
        return STEP;
    }
    
    @Override
    public int getNextStep() {
        return -1;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
}
