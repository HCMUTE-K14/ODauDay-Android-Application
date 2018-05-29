package com.odauday.ui.user.profile;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.view.SavedSearchDialog;

/**
 * Created by infamouSs on 5/29/18.
 */
public class ChangeDisplayNameDialog extends BaseDialogFragment {
    
    public static final int REQUEST_CODE = 123;
    
    private ChangeDisplayNameListener mChangeDisplayNameListener;
    
    public static ChangeDisplayNameDialog newInstance() {
        ChangeDisplayNameDialog dialog = new ChangeDisplayNameDialog();
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
        setTitle(getString(R.string.txt_update_display_name));
        View v = View.inflate(getActivity(), R.layout.layout_dialog_save_search, null);
        EditText editText = v.findViewById(R.id.txt_saved_name);
        TextView textView = v.findViewById(R.id.message);
        textView.setVisibility(View.VISIBLE);
        textView.setText(R.string.txt_update_display_name_hint);
        setContent(v);
        setNegativeAlertDialogButton(getString(R.string.txt_cancel), (dialog, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        setPositiveAlertDialogButton(getString(R.string.txt_save),
            (dialog, which) -> mChangeDisplayNameListener
                .onChangeDisplayName(editText.getText().toString()));
        
        return create();
    }
    
    public void setChangeDisplayNameListener(
        ChangeDisplayNameListener changeDisplayNameListener) {
        mChangeDisplayNameListener = changeDisplayNameListener;
    }
    
    public interface ChangeDisplayNameListener {
        
        void onChangeDisplayName(String newDisplayName);
    }
}
