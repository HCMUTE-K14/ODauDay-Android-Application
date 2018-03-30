package com.odauday.ui.view.bottomnav;

import android.content.Context;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.odauday.R;

/**
 * Created by infamouSs on 3/30/18.
 */

public enum NavigationTab {
    
    SEARCH_TAB(R.drawable.ic_nav_bar_search_selected, R.drawable.ic_nav_bar_search,
              R.string.flat_nav_bar_title_search),
    ALERT_TAB(R.drawable.ic_nav_bar_alert_selected, R.drawable.ic_nav_bar_alert,
              R.string.flat_nav_bar_title_alert),
    FAVORITE_TAB(R.drawable.ic_nav_bar_favorite_selected, R.drawable.ic_nav_bar_favortie,
              R.string.flat_nav_bar_title_favorite),
    SAVED_SEARCH_TAB(R.drawable.ic_nav_bar_savedsearch_selected, R.drawable.ic_nav_bar_savedsearch,
              R.string.flat_nav_bar_title_saved_search),
    MORE_TAB(R.drawable.ic_nav_bar_more_selected, R.drawable.ic_nav_bar_more,
              R.string.flat_nav_bar_title_more);
    
    private int mIconIdActive;
    private int mIconIdInactive;
    private int mTitleId;
    
    private NavigationTab(int activeIconId, int inactiveIconId, int titleId) {
        this.mIconIdActive = activeIconId;
        this.mIconIdInactive = inactiveIconId;
        this.mTitleId = titleId;
    }
    
    BottomNavigationItem build(Context context) {
        return new BottomNavigationItem(this.mIconIdActive, this.mTitleId)
                  .setInactiveIcon(context.getResources().getDrawable(this.mIconIdInactive));
        
    }
}