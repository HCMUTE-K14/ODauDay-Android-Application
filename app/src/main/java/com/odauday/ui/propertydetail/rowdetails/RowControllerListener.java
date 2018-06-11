package com.odauday.ui.propertydetail.rowdetails;

import com.odauday.ui.propertydetail.StageRow;

/**
 * Created by infamouSs on 5/9/18.
 */
public interface RowControllerListener {
    
    void addRow(BaseRowDetail row);
    
    void removeRow(BaseRowDetail row);
    
    void scrollToItem(StageRow stageRow);
    
    void scrollToItem(int index);
    
    void scrollToBottom();
    
    void scrollToTop();
    
    void notifyItemChanged(StageRow stageRow);
    
    void notifyInserted(StageRow stageRow);
    
    void notifyRemoved(StageRow stageRow);
    
    void notifyDataChanged();
}