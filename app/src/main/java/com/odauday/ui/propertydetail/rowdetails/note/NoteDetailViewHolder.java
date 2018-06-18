package com.odauday.ui.propertydetail.rowdetails.note;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.model.Note;
import com.odauday.ui.propertydetail.common.OnChangeNoteEvent;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.utils.TextUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 5/16/18.
 */
@SuppressLint("CheckResult")
public class NoteDetailViewHolder extends BaseRowViewHolder<NoteDetailRow> {
    
    EditText mNotes;
    TextView mCharCount;
    Button mButtonSave;
    
    String mSavedNote = "";
    
    String mNonSavedNote = "";
    
    public NoteDetailViewHolder(View itemView) {
        super(itemView);
        mNotes = itemView.findViewById(R.id.property_notes);
        mCharCount = itemView.findViewById(R.id.property_notes_char_count);
        
        mNotes.addTextChangedListener(new NoteTextWatcher(this));
        
        mButtonSave = itemView.findViewById(R.id.property_notes_done);
        
        mButtonSave.setOnClickListener(this::onSaveNote);
    }
    
    @Override
    public void unbind() {
        super.unbind();
        mNotes = null;
        mCharCount = null;
        mButtonSave = null;
    }
    
    
    @Override
    protected void update(NoteDetailRow noteDetailRow) {
        super.update(noteDetailRow);
        if (noteDetailRow.getData() != null && noteDetailRow.getData().getNote() != null) {
            Note note = noteDetailRow.getData().getNote();
            mSavedNote = note.getContent();
            mNonSavedNote = mSavedNote;
            
            if (!TextUtils.isEmpty(mSavedNote)) {
                mNotes.setText(mSavedNote);
            } else {
                mNotes.setText(mNonSavedNote);
            }
        }
    }
    
    public void onSaveNote(View view) {
        mSavedNote = mNonSavedNote;
        
        if (getRow().getData().getNote() != null) {
            Note note = getRow().getData().getNote();
            note.setContent(mSavedNote);
            updateNote(note);
        } else {
            Note note = new Note();
            note.setPropertyId(getRow().getData().getId());
            note.setContent(mSavedNote);
            createNote(note);
        }
        mButtonSave.setVisibility(View.GONE);
    }
    
    public EditText getNotes() {
        return mNotes;
    }
    
    public TextView getCharCount() {
        return mCharCount;
    }
    
    public Button getButtonSave() {
        return mButtonSave;
    }
    
    public String getSavedNote() {
        return mSavedNote;
    }
    
    public String getNonSavedNote() {
        return mNonSavedNote;
    }
    
    public void setNonSavedNote(String nonSavedNote) {
        mNonSavedNote = nonSavedNote;
    }
    
    private void createNote(Note note) {
        getRow().getNoteRepository().create(note)
            .subscribe(success -> {
                if (!getRow().getData().isFavorite()) {
                    String propertyId = getRow().getData().getId();
                    
                    getRow()
                        .getFavoriteRepository()
                        .checkFavorite(propertyId)
                        .subscribe(_success -> {
                            EventBus.getDefault().post(new OnChangeNoteEvent());
                        }, _throwable -> {
                        });
                }
            }, throwable -> {
            
            });
    }
    
    private void updateNote(Note note) {
        getRow().getNoteRepository().update(note)
            .subscribe(success -> {
                if (!getRow().getData().isFavorite()) {
                    String propertyId = getRow().getData().getId();
                    
                    getRow()
                        .getFavoriteRepository()
                        .checkFavorite(propertyId)
                        .subscribe(_success -> {
                            EventBus.getDefault().post(new OnChangeNoteEvent());
                        }, _throwable -> {
                        });
                }
            }, throwable -> {
            
            });
    }
}
