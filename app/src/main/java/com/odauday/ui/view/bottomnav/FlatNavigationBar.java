package com.odauday.ui.view.bottomnav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.odauday.R;

/**
 * Created by infamouSs on 3/30/18.
 */

public class FlatNavigationBar extends FrameLayout {

    private static final int MAX_NOTIFICATIONS_SHOWN = 99;


    private BottomNavigationBar mBottomNavigationBar;
    private TextBadgeItem mAlertBubbleItem;

    public FlatNavigationBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FlatNavigationBar(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlatNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //====================== Init =========================//
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_bottom_navigation_view, this, true);

        mBottomNavigationBar = (BottomNavigationBar) rootView.findViewById(R.id.nav_bar);

        initBottomNav();
    }

    private void initBottomNav() {
        initStyleBottomNavBar();

        initAlertBubbleItem();

        addNavButton();

        updateAlertsBubble();
    }

    private void initAlertBubbleItem() {
        mAlertBubbleItem = new TextBadgeItem();
        mAlertBubbleItem
            .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.alert_red));
        mAlertBubbleItem.setAnimationDuration(0);
    }

    private void initStyleBottomNavBar() {
        this.mBottomNavigationBar
            .setMode(BottomNavigationBar.MODE_FIXED)
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            .setAnimationDuration(0)
            .setActiveColor(R.color.nav_bar_text_active)
            .setInActiveColor(R.color.nav_bar_text_inactive);
    }

    private NavigationTab[] getTabs() {
        return new NavigationTab[]{NavigationTab.SEARCH_TAB, NavigationTab.ALERT_TAB,
            NavigationTab.FAVORITE_TAB, NavigationTab.SAVED_SEARCH_TAB,
            NavigationTab.MORE_TAB};
    }

    private void addNavButton() {
        NavigationTab[] tabs = getTabs();

        for (NavigationTab tab : tabs) {
            if (tab == NavigationTab.ALERT_TAB) {
                BottomNavigationItem alertItem = tab
                    .build(this.getContext())
                    .setBadgeItem(mAlertBubbleItem);

                mBottomNavigationBar
                    .addItem(alertItem);
            } else {
                mBottomNavigationBar.addItem(tab.build(this.getContext()));

            }
        }
        mBottomNavigationBar.initialise();
    }

    //====================== Helper Method =========================//

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mBottomNavigationBar.setTabSelectedListener(onTabSelectedListener);
    }

    public void select(int position, boolean callListener) {
        mBottomNavigationBar.selectTab(position, callListener);
    }

    public void select(String tag, boolean callListener) {
        if (tag.equals(NavigationTab.SEARCH_TAB.getNameTab())) {
            select(0, callListener);
        } else if (tag.equals(NavigationTab.ALERT_TAB.getNameTab())) {
            select(1, callListener);
        } else if (tag.equals(NavigationTab.FAVORITE_TAB.getNameTab())) {
            select(2, callListener);
        } else if (tag.equals(NavigationTab.SAVED_SEARCH_TAB.getNameTab())) {
            select(3, callListener);
        } else if (tag.equals(NavigationTab.MORE_TAB.getNameTab())) {
            select(4, callListener);
        } else {
            // throw new IllegalArgumentException("Not found Fragment with tag");
        }
    }

    public String getNameTab(int position) {
        switch (position) {
            case 0:
                return NavigationTab.SEARCH_TAB.getNameTab();
            case 1:
                return NavigationTab.ALERT_TAB.getNameTab();
            case 2:
                return NavigationTab.FAVORITE_TAB.getNameTab();
            case 3:
                return NavigationTab.SAVED_SEARCH_TAB.getNameTab();
            case 4:
                return NavigationTab.MORE_TAB.getNameTab();
            default:
                throw new IllegalArgumentException("Not found tab");
        }
    }

    public void updateAlertsBubble() {
        if (this.mAlertBubbleItem != null) {
            int numOfNotifications = 100;
            if (numOfNotifications > 0) {
                setAlertBubbleText(numOfNotifications);
                this.mAlertBubbleItem.show();
                return;
            }
            this.mAlertBubbleItem.hide();
        }
    }

    public BottomNavigationBar getBottomNavigationBar() {
        return mBottomNavigationBar;
    }

    public void setBottomNavigationBar(
        BottomNavigationBar bottomNavigationBar) {
        mBottomNavigationBar = bottomNavigationBar;
    }

    public TextBadgeItem getAlertBubbleItem() {
        return mAlertBubbleItem;
    }

    public void setAlertBubbleItem(TextBadgeItem alertBubbleItem) {
        mAlertBubbleItem = alertBubbleItem;
    }

    private void setAlertBubbleText(int numberOfNotifications) {
        if (numberOfNotifications > MAX_NOTIFICATIONS_SHOWN) {
            this.mAlertBubbleItem.setText(String.valueOf(MAX_NOTIFICATIONS_SHOWN));
        } else if (numberOfNotifications < MAX_NOTIFICATIONS_SHOWN) {
            this.mAlertBubbleItem.setText(" " + String.valueOf(numberOfNotifications) + " ");
        } else {
            this.mAlertBubbleItem.setText(String.valueOf(numberOfNotifications));
        }
    }
}
