package com.odauday.ui.propertydetail.common;

import com.odauday.model.MyEmail;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by infamouSs on 5/17/18.
 */
public class SelectEmailDialog extends SelectItemDialog<MyEmail> {
    
    @Override
    public List<ItemSelectModel<MyEmail>> parseDataToDataForAdapter(List<MyEmail> data) {
        List<ItemSelectModel<MyEmail>> list = new ArrayList<>();
        
        for (MyEmail email : data) {
            list.add(new ItemSelectModel<>(email.getEmail(), ItemSelectModel.EMAIL, email));
        }
        
        return list;
    }
    
    @Override
    public void onClickItem(ItemSelectModel<MyEmail> value) {
        super.onClickItem(value);
        Timber.d("CLICK EMAIL" + value.getText());
    }
}
