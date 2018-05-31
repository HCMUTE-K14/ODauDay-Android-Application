package com.odauday.ui.user.buypoint;

import com.odauday.R;

/**
 * Created by infamouSs on 5/31/18.
 */
public enum BuyPointDescription {
    
    ACCESS_TO_ALL_PROPERTY(R.string.message_access_to_all_property),
    USING_POINT_TO_CREATE_NEW_PROPERTY(R.string.message_using_point_to_create_new_property),
    USING_POINT_TO_EXTEND_EXPIRATION_PROPERTY(R.string.messae_using_point_to_extend_expiration_property);
    
    private int text;
    
    BuyPointDescription(int text) {
        this.text = text;
    }
    
    public int getText() {
        return text;
    }
}
