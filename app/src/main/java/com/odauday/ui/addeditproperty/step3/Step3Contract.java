package com.odauday.ui.addeditproperty.step3;

import com.odauday.model.Tag;
import java.util.List;

/**
 * Created by infamouSs on 4/27/18.
 */
public interface Step3Contract {
    
    void onSuccessGetRecentTag(List<Tag> recentTags);
    
    void onErrorGetRecentTag(List<Tag> recentTags);
}
