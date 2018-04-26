package com.odauday.ui.addeditproperty.step1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.databinding.FragmentAddEditStep1Binding;
import com.odauday.model.MyEmail;
import com.odauday.model.MyPhone;
import com.odauday.model.MyProperty;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.addeditproperty.BaseStepFragment;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.view.OnCompletePickedType;
import com.odauday.ui.search.common.view.propertydialog.PropertyTypeDialog;
import com.odauday.ui.search.navigation.FilterOption;
import com.odauday.ui.search.navigation.PropertyType;
import com.odauday.ui.selectlocation.AddressAndLocationObject;
import com.odauday.ui.selectlocation.OnCompleteSelectLocationEvent;
import com.odauday.ui.selectlocation.SelectLocationActivity;
import com.odauday.ui.view.RowItemView.RowAddedCallBack;
import com.odauday.utils.TextUtils;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 4/24/18.
 */
public class Step1Fragment extends BaseStepFragment<FragmentAddEditStep1Binding> implements
                                                                                 RowAddedCallBack,
                                                                                 OnCompletePickedType {
    
    public static final String TAG = Step1Fragment.class.getSimpleName();
    
    public static final int STEP = 1;
    
    public static Step1Fragment newInstance(MyProperty myProperty) {
        
        Bundle args = new Bundle();
        
        Step1Fragment fragment = new Step1Fragment();
        args.putParcelable(AddEditPropertyActivity.EXTRA_PROPERTY, myProperty);
        fragment.setArguments(args);
        return fragment;
    }
    
    private MyProperty mProperty;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_edit_step_1;
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
        
        initListener();
        
        mBackButton.setVisibility(View.GONE);
        mBackButton = null;
        
        addRow(PhoneAndEmailEnum.PHONE);
        
        addRow(PhoneAndEmailEnum.EMAIL);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void initNextBackListener() {
        if (getNextButton() != null) {
            getNextButton().setOnClickListener(this::onNextStep);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void onNextStep(View view) {
        boolean isValidate = validate();
        
        if (!isValidate) {
            return;
        }
        
        String typeId = null;
        if (mBinding.get().radioBtnBuy.isChecked()) {
            typeId = "BUY";
        } else if (mBinding.get().radioBtnRent.isChecked()) {
            typeId = "RENT";
        }
        
        mProperty.setEmails((List<MyEmail>) mBinding.get().emailContainer
                  .getRawValue(PhoneAndEmailEnum.EMAIL.getId()));
        mProperty.setPhones((List<MyPhone>) mBinding.get().phoneContainer
                  .getRawValue(PhoneAndEmailEnum.PHONE.getId()));
        
        mProperty.setType_id(typeId);
        
        EventBus.getDefault().post(new OnCompleteStep1Event(mProperty));
        
        mNavigationStepListener.navigate(getStep(), getNextStep());
    }
    
    @SuppressWarnings("unchecked")
    private boolean validate() {
        if (!isSelectedLocation()) {
            mBinding.get().selectLocation
                      .setError(getString(R.string.message_location_is_required));
            mBinding.get().selectLocation.requestFocus();
            Toast.makeText(this.getContext(), R.string.message_location_is_required,
                      Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isSelectedCategory()) {
            mBinding.get().selectCategory
                      .setError(getString(R.string.message_category_is_required));
            mBinding.get().selectCategory.requestFocus();
            Toast.makeText(this.getContext(), R.string.message_category_is_required,
                      Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isSelectedPrice()) {
            mBinding.get().txtPrice.setError(getString(R.string.message_price_is_reuiqred));
            mBinding.get().selectCategory.requestFocus();
            return false;
        }
        
        List<MyEmail> listEmail = mBinding.get().emailContainer
                  .getRawValue(PhoneAndEmailEnum.EMAIL.getId());
        
        if (listEmail.isEmpty()) {
            Toast.makeText(this.getContext(), R.string.message_email_is_required,
                      Toast.LENGTH_SHORT).show();
            return false;
        }
        
        List<MyPhone> listPhones = mBinding.get().phoneContainer
                  .getRawValue(PhoneAndEmailEnum.PHONE.getId());
        
        if (listPhones.isEmpty()) {
            Toast.makeText(this.getContext(), R.string.message_phone_is_required,
                      Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private boolean isSelectedLocation() {
        return !TextUtils.isEmpty(mBinding.get().selectLocation.getText().toString()) &&
               mProperty.getLocation() != null;
    }
    
    private boolean isSelectedCategory() {
        return !TextUtils.isEmpty(mBinding.get().selectCategory.getText().toString()) &&
               mProperty.getCategories() != null;
    }
    
    private boolean isSelectedPrice() {
        return mBinding.get().txtPrice.getRawValue().doubleValue() > 0;
    }
    
    private void initListener() {
        mBinding.get().selectLocation.setOnClickListener(this::openActivitySelectAddress);
        
        mBinding.get().selectCategory.setOnClickListener(this::openChoosePropertyTypeDialog);
    }
    
    public void openActivitySelectAddress(View view) {
        Intent intent = new Intent(this.getContext(), SelectLocationActivity.class);
        if (mProperty.getLocation() != null) {
            intent.putExtra(SelectLocationActivity.EXTRA_LAST_LOCATION,
                      new AddressAndLocationObject(mProperty.getAddress(),
                                mProperty.getLocation().toLatLng()));
        }
        this.startActivityForResult(intent, OnCompleteSelectLocationEvent.REQUEST_CODE);
        
        //
        //        ViewUtils.startActivityForResult(getActivity(), SelectLocationActivity.class,
        //                  OnCompleteSelectLocationEvent.REQUEST_CODE);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OnCompleteSelectLocationEvent.REQUEST_CODE && resultCode ==
                                                                         Activity.RESULT_OK &&
            data != null) {
            
            AddressAndLocationObject addressAndLocationObject = data
                      .getParcelableExtra(SelectLocationActivity.EXTRA_ADDRESS_AND_LOCATION);
            
            updateSelectLocation(addressAndLocationObject);
        }
    }
    
    private void updateSelectLocation(AddressAndLocationObject object) {
        mProperty.setAddress(object.getAddress());
        mProperty.setLocation(GeoLocation.fromLatLng(object.getLocation()));
        mBinding.get().selectLocation.setText(object.getAddress());
    }
    
    public void openChoosePropertyTypeDialog(View view) {
        if (getFragmentManager() == null) {
            return;
        }
        List<Integer> selectedPropertyType = PropertyType
                  .convertToArrayInt(mProperty.getCategories());
        BaseDialogFragment dialog = PropertyTypeDialog
                  .newInstance(selectedPropertyType);
        
        if (dialog == null) {
            return;
        }
        
        dialog.setTargetFragment(this, FilterOption.PROPERTY_TYPE.getRequestCode());
        dialog.show(getFragmentManager(), FilterOption.PROPERTY_TYPE.getTag());
    }
    
    @Override
    public int getStep() {
        return STEP;
    }
    
    @Override
    public int getBackStep() {
        return -1;
    }
    
    private void addRow(PhoneAndEmailEnum type) {
        switch (type) {
            case EMAIL:
                this.mBinding.get().emailContainer
                          .addRow(getContext(), type.mTypeInput, type.mText, type.mImage, this);
                break;
            case PHONE:
                this.mBinding.get().phoneContainer
                          .addRow(getContext(), type.mTypeInput, type.mText, type.mImage, this);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void rowItemAddedCallBack() {
        mBinding.get().scrollView
                  .postDelayed(() -> mBinding.get().scrollView.fullScroll(View.FOCUS_DOWN), 100);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void onCompletePickedType(int requestCode, Object value) {
        if (requestCode == FilterOption.PROPERTY_TYPE.getRequestCode()) {
            List<Integer> integerList = (List<Integer>) value;
            mProperty.setCategoriesByIntegerList(integerList);
            String text = buildStringCategory(integerList);
            mBinding.get().selectCategory.setText(text);
        }
    }
    
    private String buildStringCategory(List<Integer> list) {
        StringBuilder builder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                builder.append(getString(PropertyType.getById(i).getDisplayStringResource()));
                break;
            }
            builder.append(getString(PropertyType.getById(i).getDisplayStringResource()))
                      .append(", ");
        }
        
        return builder.toString();
    }
}
