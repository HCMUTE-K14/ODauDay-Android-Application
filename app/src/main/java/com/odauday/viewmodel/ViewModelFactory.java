package com.odauday.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by infamouSs on 2/28/18.
 */

@SuppressWarnings("unchecked")
public class ViewModelFactory<V> implements ViewModelProvider.Factory {
    
    
    private final V viewModel;
    
    public ViewModelFactory(V viewModel) {
        this.viewModel = viewModel;
    }
    
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.getClass())) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
