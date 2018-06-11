package com.odauday.ui.propertydetail.common;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.propertydetail.common.SelectItemAdapter.OnClickItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/17/18.
 */
@SuppressWarnings("unchecked")
public abstract class SelectItemDialog<T extends Parcelable> extends BaseDialogFragment implements
                                                                                        OnClickItemListener<ItemSelectModel<T>> {
    
    private String mDataSelected;
    private List<ItemSelectModel<T>> mDataForAdapter;
    
    public static <T extends Parcelable> SelectPhoneCallDialog newInstance(List<T> data) {
        
        Bundle args = new Bundle();
        
        SelectPhoneCallDialog fragment = new SelectPhoneCallDialog();
        args.putParcelableArrayList(Constants.INTENT_EXTRA_SELECT_ITEM_DATA,
            (ArrayList<? extends Parcelable>) data);
        fragment.setArguments(args);
        return fragment;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_phone_number));
        View view = View
            .inflate(getActivity(), R.layout.layout_dialog_with_only_recycler_view, null);
        setContent(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        if (getArguments() != null) {
            List<T> listData = getArguments()
                .getParcelableArrayList(Constants.INTENT_EXTRA_SELECT_ITEM_DATA);
            SelectItemAdapter adapter = new SelectItemAdapter(this);
            mDataForAdapter = parseDataToDataForAdapter(listData);
            recyclerView.setAdapter(adapter);
            adapter.setData(mDataForAdapter);
        }
        setNegativeAlertDialogButton(getString(R.string.txt_close), null);
        return create();
    }
    
    
    public abstract List<ItemSelectModel<T>> parseDataToDataForAdapter(List<T> data);
    
    @Override
    public void onClickItem(ItemSelectModel<T> value) {
        mDataSelected = value.getText();
    }
    
    
    public String getDataSelected() {
        return mDataSelected;
    }
}
