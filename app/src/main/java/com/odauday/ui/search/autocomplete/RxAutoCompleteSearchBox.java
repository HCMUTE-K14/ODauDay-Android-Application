package com.odauday.ui.search.autocomplete;

import android.widget.EditText;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.odauday.utils.TextUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/23/18.
 */
public class RxAutoCompleteSearchBox {
    
    private static final int DEBOUNCE_TIME = 500;
    
    private final OnSearchQuery mOnSearchQuery;
    
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Disposable mDisposable;
    
    public RxAutoCompleteSearchBox(EditText editText, OnSearchQuery onSearchQuery) {
        this.mOnSearchQuery = onSearchQuery;
        mDisposable = createDisposable(editText);
    }
    
    private Disposable createDisposable(EditText editText) {
        return RxTextView.afterTextChangeEvents(editText)
                  .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(success -> {
                      if (mOnSearchQuery != null) {
                          mOnSearchQuery.onSearchQuery(
                                    TextUtils.isEmpty(success.editable().toString()) ? ""
                                              : success.editable().toString());
                      }
                  }, throwable -> Timber.d(throwable.getMessage()));
    }
    
    public void start() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.add(mDisposable);
        }
    }
    
    public void stop() {
        if (mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mDisposable = null;
    }
    
    public interface OnSearchQuery {
        
        void onSearchQuery(String keyword);
    }
}
