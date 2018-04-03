package com.odauday.ui.view.bottomnav;

import android.content.Context;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.odauday.R;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 3/30/18.
 */

public enum NavigationTab {

    SEARCH_TAB(R.drawable.ic_nav_bar_search_selected, R.drawable.ic_nav_bar_search,
        R.string.flat_nav_bar_title_search, "SEARCH_TAB"),
    ALERT_TAB(R.drawable.ic_nav_bar_alert_selected, R.drawable.ic_nav_bar_alert,
        R.string.flat_nav_bar_title_alert, "ALERT_TAB"),
    FAVORITE_TAB(R.drawable.ic_nav_bar_favorite_selected, R.drawable.ic_nav_bar_favortie,
        R.string.flat_nav_bar_title_favorite, "FAVORITE_TAB"),
    SAVED_SEARCH_TAB(R.drawable.ic_nav_bar_savedsearch_selected, R.drawable.ic_nav_bar_savedsearch,
        R.string.flat_nav_bar_title_saved_search, "SAVED_SEARCH_TAB"),
    MORE_TAB(R.drawable.ic_nav_bar_more_selected, R.drawable.ic_nav_bar_more,
        R.string.flat_nav_bar_title_more, "MORE_TAB");

    private final int mIconIdActive;
    private final int mIconIdInactive;
    private final int mTitleId;
    private final String mNameTab;


    NavigationTab(int activeIconId, int inactiveIconId, int titleId, String nameTab) {
        this.mIconIdActive = activeIconId;
        this.mIconIdInactive = inactiveIconId;
        this.mTitleId = titleId;
        this.mNameTab = nameTab;
    }

    public static boolean isNavigationTab(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return false;
        }
        switch (tag) {
            case "SEARCH_TAB":
            case "ALERT_TAB":
            case "FAVORITE_TAB":
            case "SAVED_SEARCH_TAB":
            case "MORE_TAB":
                return true;
            default:
                return false;
        }
    }

    BottomNavigationItem build(Context context) {
        return new BottomNavigationItem(this.mIconIdActive, this.mTitleId)
            .setInactiveIcon(context.getResources().getDrawable(this.mIconIdInactive));
    }

    public String getNameTab() {
        return mNameTab;
    }
}