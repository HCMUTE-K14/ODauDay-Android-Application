package com.odauday.ui;

import com.odauday.utils.ObjectUtils;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class ItemSelection {
    
    private int id;
    private String displayString;
    private boolean isSelected;
    
    
    public ItemSelection(int id, String displayString, boolean isSelected) {
        this.id = id;
        this.displayString = displayString;
        this.isSelected = isSelected;
    }
    
    public String getDisplayString() {
        return displayString;
    }
    
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    
    public int getId() {
        
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemSelection that = (ItemSelection) o;
        return id == that.id &&
               isSelected == that.isSelected &&
               ObjectUtils.equals(displayString, that.displayString);
    }
    
    @Override
    public int hashCode() {
        
        return ObjectUtils.hash(id, displayString, isSelected);
    }
    
    @Override
    public String toString() {
        return "ItemSelection{" +
               "id=" + id +
               ", displayString='" + displayString + '\'' +
               ", isSelected=" + isSelected +
               '}';
    }
}
