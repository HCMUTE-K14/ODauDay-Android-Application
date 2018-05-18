package com.odauday.ui.propertydetail.rowdetails.enquiry;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import com.odauday.R;
import com.odauday.model.MyEmail;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/18/18.
 */
public class ChoiceEmailDialog {
    
    private AlertDialog.Builder mBuilder;
    private boolean[] mCheckedItems;
    private String[] mEmails;
    private OnSelectedEmail mOnSelectedEmail;
    
    private List<String> mSelectedEmail = new ArrayList<>();
    
    public ChoiceEmailDialog(Context context,
        boolean[] checkedEmails,
        List<MyEmail> emails,
        OnSelectedEmail onSelectedEmail) {
        if (emails == null) {
            emails = new ArrayList<>();
        }
        mBuilder = new Builder(context);
        mBuilder.setTitle(R.string.txt_email_agent);
        
        mEmails = makeData(emails);
        mOnSelectedEmail = onSelectedEmail;
        mCheckedItems = checkedEmails;
        
        mBuilder.setMultiChoiceItems(mEmails, mCheckedItems,
            (dialog, which, isChecked) -> {
                if (isChecked) {
                    mSelectedEmail.add(mEmails[which]);
                } else {
                    mSelectedEmail.remove(mEmails[which]);
                }
            });
        mBuilder.setPositiveButton(R.string.txt_ok, (dialog, which) -> {
            if (mOnSelectedEmail != null) {
                mOnSelectedEmail.onSelectedEmail(mSelectedEmail);
            }
        });
        
        mBuilder.setNegativeButton(R.string.txt_cancel, (dialog, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        
        mBuilder.setNeutralButton(R.string.txt_clear_all, (dialog, which) -> {
            for (int i = 0; i < mCheckedItems.length; i++) {
                mCheckedItems[i] = false;
                mSelectedEmail.clear();
            }
            if (mOnSelectedEmail != null) {
                mOnSelectedEmail.onClearAll();
            }
        });
    }
    
    public void show() {
        mBuilder.create().show();
    }
    
    private String[] makeData(List<MyEmail> emails) {
        int sizeEmails = emails.size();
        
        String[] data = new String[sizeEmails];
        for (int i = 0; i < sizeEmails; i++) {
            data[i] = emails.get(i).getEmail();
        }
        
        return data;
    }
    
    
    public interface OnSelectedEmail {
        
        void onSelectedEmail(List<String> emails);
        
        void onClearAll();
    }
    
}
