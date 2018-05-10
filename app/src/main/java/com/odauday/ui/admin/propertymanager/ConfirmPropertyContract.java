package com.odauday.ui.admin.propertymanager;

import com.odauday.ui.base.BaseContract;

/**
 * Created by kunsubin on 5/4/2018.
 */

public interface ConfirmPropertyContract extends BaseContract {
    void onSuccessAction(Object object);
    void onErrorAction(Object object);
    void onLoadingAction(boolean isLoading);
}
