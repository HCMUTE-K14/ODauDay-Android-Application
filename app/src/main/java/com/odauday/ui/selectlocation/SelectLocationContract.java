package com.odauday.ui.selectlocation;

/**
 * Created by infamouSs on 4/25/18.
 */
public interface SelectLocationContract {
    
    void onSuccessGetInfoLocation(AddressAndLocationObject object);
    
    void onErrorGetInfoLocation(Exception ex);
}
