package com.odauday.ui.search.common.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import com.odauday.R;
import com.odauday.ui.base.BaseDialogFragment;

/**
 * Created by infamouSs on 4/23/18.
 */
public class SavedSearchDialog extends BaseDialogFragment {
    
    public static final int REQUEST_CODE = 123;
    
    private SaveSearchListener mSaveSearchListener;
    
    public static SavedSearchDialog newInstance() {
        SavedSearchDialog dialog = new SavedSearchDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_save_search_heading));
        View v = View.inflate(getActivity(), R.layout.layout_dialog_save_search, null);
        EditText editText = v.findViewById(R.id.txt_saved_name);
        setContent(v);
        setNegativeAlertDialogButton(getString(R.string.txt_cancel), (dialog, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        setPositiveAlertDialogButton(getString(R.string.txt_save),
                  (dialog, which) -> mSaveSearchListener
                            .onSaveSearch(editText.getText().toString()));
        
        return create();
    }
    
    public void setSaveSearchListener(
              SaveSearchListener saveSearchListener) {
        mSaveSearchListener = saveSearchListener;
    }
    
    public interface SaveSearchListener {
        
        void onSaveSearch(String nameSavedSearch);
    }
}
