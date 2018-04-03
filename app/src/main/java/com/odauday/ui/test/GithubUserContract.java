package com.odauday.ui.test;

import com.odauday.model.User;
import com.odauday.ui.base.Contract;
import java.util.List;

/**
 * Created by infamouSs on 2/28/18.
 */

public interface GithubUserContract extends Contract {

    void showData(List<User> users);

    void handlerError(Exception ex);
}
