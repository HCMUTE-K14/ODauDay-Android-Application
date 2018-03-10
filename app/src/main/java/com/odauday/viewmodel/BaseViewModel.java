package com.odauday.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by infamouSs on 2/28/18.
 */

public abstract class BaseViewModel extends ViewModel {
    
    protected final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    
    protected CompositeDisposable mCompositeDisposable;
    
    protected final MutableLiveData<Resource> response = new MutableLiveData<>();
    
    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }
    
    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }
    
    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }
    
    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
    }
    
    public MutableLiveData<Resource> response() {
        return response;
    }
    
}
