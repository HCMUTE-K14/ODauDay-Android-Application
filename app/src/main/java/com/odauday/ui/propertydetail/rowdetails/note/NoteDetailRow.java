package com.odauday.ui.propertydetail.rowdetails.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.data.NoteRepository;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.StageRow;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;

/**
 * Created by infamouSs on 5/16/18.
 */
public class NoteDetailRow extends BaseRowDetail<PropertyDetail, NoteDetailViewHolder> {
    
    private PropertyDetail mPropertyDetail;
    private NoteRepository mNoteRepository;
    
    @Override
    public PropertyDetail getData() {
        return mPropertyDetail;
    }
    
    @Override
    public void setData(PropertyDetail data) {
        mPropertyDetail = data;
    }
    
    @Override
    public NoteDetailViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_note_property_detail, parent, false);
        return new NoteDetailViewHolder(view);
    }
    
    @Override
    public StageRow getStageRow() {
        return StageRow.NOTE_ROW;
    }
    
    public NoteRepository getNoteRepository() {
        return mNoteRepository;
    }
    
    public void setNoteRepository(NoteRepository noteRepository) {
        mNoteRepository = noteRepository;
    }
}
