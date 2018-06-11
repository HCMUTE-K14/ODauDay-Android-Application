package com.odauday.ui.admin.usermanager;

import com.odauday.ui.base.BaseContract;

/**
 * Created by kunsubin on 5/10/2018.
 */

public interface UserManagerContract extends BaseContract {
    void onChangeStatusSuccess(Object object);
    void onChangeStatusFailure(Object object);
}
