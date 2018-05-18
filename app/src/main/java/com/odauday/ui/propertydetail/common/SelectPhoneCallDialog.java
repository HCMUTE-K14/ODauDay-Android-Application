package com.odauday.ui.propertydetail.common;

import android.content.Intent;
import android.net.Uri;
import com.odauday.model.MyPhone;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/16/18.
 */
public class SelectPhoneCallDialog extends SelectItemDialog<MyPhone> {
    
    private void openDialActivity() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        
        intent.setData(Uri.parse("tel:" + getDataSelected()));
        if (getContext() == null) {
            return;
        }
        getContext().startActivity(intent);
    }
    
    @Override
    public List<ItemSelectModel<MyPhone>> parseDataToDataForAdapter(List<MyPhone> data) {
        List<ItemSelectModel<MyPhone>> list = new ArrayList<>();
        
        for (MyPhone phone : data) {
            list.add(new ItemSelectModel<>(phone.getPhoneNumber(), ItemSelectModel.PHONE, phone));
        }
        
        return list;
    }
    
    @Override
    public void onClickItem(ItemSelectModel<MyPhone> value) {
        super.onClickItem(value);
        openDialActivity();
    }
}
