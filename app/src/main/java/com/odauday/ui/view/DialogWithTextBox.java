package com.odauday.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.ui.base.BaseDialogFragment;

/**
 * Created by infamouSs on 5/30/18.
 */
public class DialogWithTextBox extends BaseDialogFragment {
    
    public static final int REQUEST_CODE = 123;
    
    private DialogWithTextBoxListener mListener;
    
    public static DialogWithTextBox newInstance(int title, int hint, int message) {
        DialogWithTextBox dialog = new DialogWithTextBox();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.INTENT_EXTRA_TITLE, title);
        bundle.putInt(Constants.INTENT_EXTRA_HINT, hint);
        bundle.putInt(Constants.INTENT_EXTRA_MESSAGE, message);
        dialog.setArguments(bundle);
        return dialog;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Need argument");
        }
        int title = getArguments().getInt(Constants.INTENT_EXTRA_TITLE);
        int hint = getArguments().getInt(Constants.INTENT_EXTRA_HINT);
        int message = getArguments().getInt(Constants.INTENT_EXTRA_MESSAGE);
        
        setTitle(getString(title));
        View v = View.inflate(getActivity(), R.layout.layout_dialog_with_text_box, null);
        EditText editText = v.findViewById(R.id.txt_saved_name);
        editText.setHint(hint);
        
        if (message > 0) {
            TextView textView = v.findViewById(R.id.message);
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
        }
        
        setContent(v);
        setNegativeAlertDialogButton(getString(R.string.txt_cancel), (dialog, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        setPositiveAlertDialogButton(getString(R.string.txt_save),
            (dialog, which) -> mListener
                .onDone(editText.getText().toString()));
        
        return create();
    }
    
    public void setListener(DialogWithTextBoxListener listener) {
        mListener = listener;
    }
    
    public interface DialogWithTextBoxListener {
        
        void onDone(String obj);
    }
}
