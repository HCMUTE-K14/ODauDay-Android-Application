package com.odauday.ui.favorite;

import com.odauday.ui.base.BaseContract;

/**
 * Created by kunsubin on 4/4/2018.
 */

public interface FavoriteContract extends BaseContract {
    
    void shareFavoriteSuccess(Object object);
    
    void shareFavoriteError(Object object);
}
