package com.odauday.ui.addeditproperty.step4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.odauday.R;
import com.odauday.databinding.FragmentAddEditStep4Binding;
import com.odauday.model.MyProperty;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.addeditproperty.BaseStepFragment;
import com.odauday.ui.addeditproperty.step4.SelectedImageAdapter.RemoveImageSelectedListener;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/24/18.
 */
public class Step4Fragment extends BaseStepFragment<FragmentAddEditStep4Binding> implements
                                                                                 RemoveImageSelectedListener {
    
    
    public static final String TAG = Step4Fragment.class.getSimpleName();
    
    public static final int STEP = 4;
    
    private static final int LIMIT_IMAGE = 9;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    
    private SelectedImageAdapter mSelectedImageAdapter;
    
    private List<com.odauday.model.Image> mSelectedImages = new ArrayList<>();
    private List<Image> mSelectedImagesInImagePiker = new ArrayList<>();
    
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
                .origin((ArrayList<Image>) mSelectedImagesInImagePiker)
                .theme(R.style.AppTheme)
                .limit(LIMIT_IMAGE)
                .start(REQUEST_CODE_CHOOSE_IMAGE);
        });
        mSelectedImageAdapter = new SelectedImageAdapter(this);
        mBinding.get().recycleView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && data != null) {
            List<Image> images = ImagePicker.getImages(data);
            if (images != null && !images.isEmpty()) {
                ViewUtils.showHideView(mBinding.get().recycleView, true);
                ViewUtils.showHideView(mBinding.get().noImage, false);
                mSelectedImagesInImagePiker = images;
                mSelectedImages.clear();
                mSelectedImages.addAll(convertToImageModel(mSelectedImagesInImagePiker));
                mSelectedImageAdapter.setData(mSelectedImages);
                
                mBinding.get().recycleView.setAdapter(mSelectedImageAdapter);
            } else {
                ViewUtils.showHideView(mBinding.get().recycleView, false);
                ViewUtils.showHideView(mBinding.get().noImage, true);
            }
        }
    }
    
    @Override
    public void onNextStep(View view) {
        if (!mSelectedImages.isEmpty()) {
            mProperty.setImages(mSelectedImages);
            mBus.post(new OnCompleteStep4Event(mProperty));
            mNavigationStepListener.navigate(getStep(), getNextStep());
            return;
        }
        Toast.makeText(getContext(), R.string.message_choose_image, Toast.LENGTH_SHORT).show();
        
    }
    
    private List<com.odauday.model.Image> convertToImageModel(List<Image> images) {
        List<com.odauday.model.Image> _images = new ArrayList<>();
        for (Image image : images) {
            _images.add(new com.odauday.model.Image(TextUtils.generatorUUID(), image.getPath()));
        }
        return _images;
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
    
    @Override
    public void onRemoveImage(com.odauday.model.Image image, int position) {
        mSelectedImages.remove(image);
        mSelectedImageAdapter.remove(image, position);
        mSelectedImagesInImagePiker.remove(new Image(0, "", image.getUrl()));
        if (mSelectedImages.isEmpty()) {
            ViewUtils.showHideView(mBinding.get().recycleView, false);
            ViewUtils.showHideView(mBinding.get().noImage, true);
        }
    }
}
