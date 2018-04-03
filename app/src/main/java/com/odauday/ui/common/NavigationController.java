package com.odauday.ui.common;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.ui.alert.AlertTabMainFragment;
import com.odauday.ui.favorite.FavoriteTabMainFragment;
import com.odauday.ui.more.MoreTabMainFragment;
import com.odauday.ui.savedsearch.SavedSearchTabMainFragment;
import com.odauday.ui.search.SearchTabMainFragment;
import com.odauday.ui.view.bottomnav.NavigationTab;
import javax.inject.Inject;

/**
 * Created by infamouSs on 3/30/18.
 */

public class NavigationController {

    public static final long DELAY_ATTACH_FRAGMENT = 100;

    private final int mContainerId;
    private final FragmentManager mFragmentManager;
    private final Handler mHandler;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.mContainerId = R.id.view_container;
        this.mFragmentManager = mainActivity.getSupportFragmentManager();
        this.mHandler = new Handler();
    }

    public void navigateTo(String tag) {
        if (tag.equals(NavigationTab.SEARCH_TAB.getNameTab())) {
            navigateToSearchTab();
        } else if (tag.equals(NavigationTab.ALERT_TAB.getNameTab())) {
            navigateToAlertTab();
        } else if (tag.equals(NavigationTab.FAVORITE_TAB.getNameTab())) {
            navigateToFavoriteTab();
        } else if (tag.equals(NavigationTab.SAVED_SEARCH_TAB.getNameTab())) {
            navigateToSavedSearchTab();
        } else if (tag.equals(NavigationTab.MORE_TAB.getNameTab())) {
            navigateToMoreTab();
        } else {
            throw new IllegalArgumentException("Not found Navigation Fragment with tag");
        }
    }

    private void navigateToSearchTab() {
        Runnable runnableAttachSearchTab = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(mFragmentManager)
            .setContainerId(mContainerId)
            .setFragment(SearchTabMainFragment.newInstance())
            .setTagFragment(SearchTabMainFragment.TAG)
            .build();

        mHandler.postDelayed(runnableAttachSearchTab, DELAY_ATTACH_FRAGMENT);
    }

    private void navigateToAlertTab() {
        Runnable runnableAttachAlertTab = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(mFragmentManager)
            .setContainerId(mContainerId)
            .setFragment(AlertTabMainFragment.newInstance())
            .setTagFragment(AlertTabMainFragment.TAG)
            .build();

        mHandler.postDelayed(runnableAttachAlertTab, DELAY_ATTACH_FRAGMENT);
    }

    private void navigateToFavoriteTab() {
        Runnable runnableAttachFavoriteTab = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(mFragmentManager)
            .setContainerId(mContainerId)
            .setFragment(FavoriteTabMainFragment.newInstance())
            .setTagFragment(FavoriteTabMainFragment.TAG)
            .build();

        mHandler.postDelayed(runnableAttachFavoriteTab, DELAY_ATTACH_FRAGMENT);
    }

    private void navigateToSavedSearchTab() {
        Runnable runnableAttachSavedSearchTab = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(mFragmentManager)
            .setContainerId(mContainerId)
            .setFragment(SavedSearchTabMainFragment.newInstance())
            .setTagFragment(SavedSearchTabMainFragment.TAG)
            .build();

        mHandler.postDelayed(runnableAttachSavedSearchTab, DELAY_ATTACH_FRAGMENT);
    }

    private void navigateToMoreTab() {
        Runnable runnableAttachMoreTab = new AttachFragmentRunnable
            .AttachFragmentBuilder()
            .setTypeAttach(AttachFragmentRunnable.TYPE_REPLACE)
            .setFragmentManager(mFragmentManager)
            .setContainerId(mContainerId)
            .setFragment(MoreTabMainFragment.newInstance())
            .setTagFragment(MoreTabMainFragment.TAG)
            .build();

        mHandler.postDelayed(runnableAttachMoreTab, DELAY_ATTACH_FRAGMENT);
    }
}
