package com.odauday.ui.propertydetail;

/**
 * Created by infamouSs on 5/11/18.
 */
public enum StageRow {
    GALLERY_ROW(-1),
    VITAL_ROW(0),
    BED_BATH_PARKING_ROW(1),
    DESCRIPTION_ROW(2),
    TAGS_ROW(3),
    MAP_ROW(4),
    DIRECTION_ROW(5),
    NOTE_ROW(6);
    
    int pos;
    
    StageRow(int pos) {
        this.pos = pos;
    }
    
    public int getPos() {
        return pos;
    }
}
