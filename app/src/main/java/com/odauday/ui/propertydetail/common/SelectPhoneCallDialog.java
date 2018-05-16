package com.odauday.ui.propertydetail.common;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.model.MyPhone;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.propertydetail.common.SelectPhoneCallAdapter.OnSelectPhoneNumberListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/16/18.
 */
public class SelectPhoneCallDialog extends BaseDialogFragment implements
                                                              OnSelectPhoneNumberListener {
    
    private SelectPhoneCallAdapter mAdapter;
    private String mPhoneNumberSelected;
    
    
    public static SelectPhoneCallDialog newInstance(List<MyPhone> phones) {
        
        Bundle args = new Bundle();
        
        SelectPhoneCallDialog fragment = new SelectPhoneCallDialog();
        args.putParcelableArrayList(Constants.INTENT_EXTRA_PHONES_NUMBER,
            (ArrayList<? extends Parcelable>) phones);
        fragment.setArguments(args);
        return fragment;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_phone_number));
        View view = View.inflate(getActivity(), R.layout.layout_select_phone_number_dialog, null);
        setContent(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        if (getArguments() != null) {
            List<MyPhone> phones = getArguments()
                .getParcelableArrayList(Constants.INTENT_EXTRA_PHONES_NUMBER);
            mAdapter = new SelectPhoneCallAdapter(this);
            recyclerView.setAdapter(mAdapter);
            mAdapter.setData(phones);
        }
        
        return create();
    }
    
    
    private void openCallActivity() {
        
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mPhoneNumberSelected));
        
        startActivity(intent);
    }
    
    
    @Override
    public void onSelectPhoneNumber(String phoneNumber) {
        mPhoneNumberSelected = phoneNumber;
        openCallActivity();
    }
}
