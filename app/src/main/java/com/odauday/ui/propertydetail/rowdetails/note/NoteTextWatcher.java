package com.odauday.ui.propertydetail.rowdetails.note;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import com.odauday.R;

/**
 * Created by infamouSs on 5/16/18.
 */
public class NoteTextWatcher implements TextWatcher {
    
    private static final int MAX_CHAR = 2000;
    
    private NoteDetailViewHolder mNoteDetailViewHolder;
    
    public NoteTextWatcher(NoteDetailViewHolder viewHolder) {
        this.mNoteDetailViewHolder = viewHolder;
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    
    }
    
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int currentLength = 0;
        if (TextUtils.isEmpty(s.toString())) {
            currentLength = 0;
        } else {
            currentLength = s.length();
        }
        int remainingLength = MAX_CHAR - currentLength;
        String remainingText = mNoteDetailViewHolder.itemView.getContext()
            .getString(R.string.text_property_notes_char_count, remainingLength);
        mNoteDetailViewHolder.getCharCount().setText(remainingText);
        mNoteDetailViewHolder.setNonSavedNote(s.toString());
    }
    
    @Override
    public void afterTextChanged(Editable s) {
        if (!mNoteDetailViewHolder.getSavedNote().equals(mNoteDetailViewHolder.getNonSavedNote())) {
            this.mNoteDetailViewHolder.getButtonSave().setVisibility(View.VISIBLE);
        }
    }
}
