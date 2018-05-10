package com.odauday.ui.addeditproperty.step1;

import android.widget.EditText;
import com.odauday.R;
import com.odauday.model.MyProperty;
import com.odauday.ui.view.RowItemView;
import com.odauday.ui.view.currencyedittext.CurrencyEditText;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.TextUtils;
import java.util.List;

/**
 * Created by infamouSs on 4/27/18.
 */

@SuppressWarnings("unchecked")
public class Step1Helper {
    
    public Step1Helper() {
    
    }
    
    public boolean validate(EditText selectLocation, EditText selectCategory,
        CurrencyEditText txtPrice, RowItemView phoneContainer, RowItemView emailContainer,
        MyProperty myProperty) {
        if (!isSelectedLocation(selectLocation, myProperty)) {
            selectLocation
                .setError(selectLocation.getContext()
                    .getString(R.string.message_location_is_required));
            selectLocation.requestFocus();
            SnackBarUtils.showSnackBar(selectLocation, R.string.message_location_is_required);
            return false;
        }
        if (!isSelectedCategory(selectCategory, myProperty)) {
            selectCategory
                .setError(selectLocation.getContext()
                    .getString(R.string.message_category_is_required));
            selectCategory.requestFocus();
            SnackBarUtils.showSnackBar(selectCategory, R.string.message_category_is_required);
            return false;
        }
        
        if (!isSelectedPrice(txtPrice)) {
            txtPrice.setError(txtPrice.getContext().getString(R.string.message_price_is_reuiqred));
            txtPrice.requestFocus();
            return false;
        }
        
        return isValidList(phoneContainer, R.string.message_phone_is_required,
            PhoneAndEmailEnum.PHONE.getId()) &&
               isValidList(emailContainer, R.string.message_email_is_required,
                   PhoneAndEmailEnum.EMAIL.getId());
    }
    
    private boolean isSelectedLocation(EditText selectLocation, MyProperty property) {
        return !TextUtils.isEmpty(selectLocation.getText().toString()) &&
               property.getLocation() != null;
    }
    
    private boolean isSelectedCategory(EditText selectCategory, MyProperty property) {
        return !TextUtils.isEmpty(selectCategory.getText().toString()) &&
               property.getCategories() != null;
    }
    
    private boolean isSelectedPrice(CurrencyEditText txtPrice) {
        return txtPrice.getRawValue().doubleValue() > 0;
    }
    
    private boolean isValidList(RowItemView container, int resourceStr, int id) {
        if (container == null) {
            return false;
        }
        
        List list = container
            .getRawValue(id);
        
        if (list == null) {
            if (id != PhoneAndEmailEnum.EMAIL.getId()) {
                SnackBarUtils.showSnackBar(container, resourceStr);
                return false;
            }
            return true;
        }
        
        if (list.isEmpty()) {
            if (id != PhoneAndEmailEnum.EMAIL.getId()) {
                SnackBarUtils.showSnackBar(container, resourceStr);
                return false;
            }
            return true;
        }
        
        if (list.size() < container.getItemCount()) {
            if (id != PhoneAndEmailEnum.EMAIL.getId()) {
                return false;
            }
            return true;
        }
        
        return true;
    }
}
