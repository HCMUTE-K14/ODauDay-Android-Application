package com.odauday.ui.search.common;

import com.google.android.gms.maps.GoogleMap;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/**
 * Created by infamouSs on 4/20/18.
 */
@SuppressWarnings("CheckResult")
public class RxCameraIdleListener {
    
    private static final int DEBOUNCE_TIME = 250;
    
    private final GoogleMap mMap;

    private final TriggerCameraIdle mTriggerCameraIdle;
    
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private Disposable mDisposable;
    
    public RxCameraIdleListener(GoogleMap map, TriggerCameraIdle cameraIdle) {
        this.mMap = map;
        this.mTriggerCameraIdle = cameraIdle;
    }
    
    private Disposable createCameraIdleDisposable() {
        
        return Observable.<String>create(
                  subscriber -> mMap.setOnCameraIdleListener(() -> subscriber.onNext("OK")))
                  .throttleLast(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                  .subscribeOn(AndroidSchedulers.mainThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(trigger -> mTriggerCameraIdle.onCameraIdle());
    }
    
    public void start() {
        if (mDisposable == null) {
            mDisposable = createCameraIdleDisposable();
        }
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.add(mDisposable);
        }
    }
    
    public void stop() {
        if (mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
    
    
    public interface TriggerCameraIdle {
        
        void onCameraIdle();
    }
}
