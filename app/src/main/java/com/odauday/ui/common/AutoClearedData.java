package com.odauday.ui.common;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by infamouSs on 3/5/18.
 */

public class AutoClearedData<T> {

    private T value;

    public AutoClearedData(Fragment fragment, T value) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.registerFragmentLifecycleCallbacks(
                new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                        if (f == fragment) {
                            AutoClearedData.this.value = null;
                            fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                        }
                    }
                }, false);
        }
        this.value = value;
    }

    public T get() {
        return value;
    }

}
