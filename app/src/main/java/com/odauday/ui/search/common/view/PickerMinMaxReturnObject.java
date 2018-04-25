package com.odauday.ui.search.common.view;

import com.odauday.ui.search.common.MinMaxObject;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 4/7/18.
 */

public class PickerMinMaxReturnObject {
    
    private MinMaxObject<String> display;
    private MinMaxObject<Integer> value;

    public PickerMinMaxReturnObject(MinMaxObject<String> display, MinMaxObject<Integer> value) {
        this.display = display;
        this.value = value;
    }
    
    public MinMaxObject<String> getDisplay() {
        return display;
    }
    
    public void setDisplay(MinMaxObject<String> display) {
        this.display = display;
    }
    
    public MinMaxObject<Integer> getValue() {
        return value;
    }
    
    public void setValue(MinMaxObject<Integer> value) {
        this.value = value;
    }
    
    
    public String displayToString() {
        return TextUtils.build(display.getMin(), " - ", display.getMax());
    }
}
