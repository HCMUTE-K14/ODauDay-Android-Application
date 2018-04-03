package com.odauday;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by infamouSs on 2/28/18.
 */
public class SchedulersExecutor {

    @Inject
    public SchedulersExecutor() {
    }

    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler computation() {
        return Schedulers.computation();
    }


    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
