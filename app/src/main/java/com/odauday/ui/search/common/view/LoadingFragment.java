package com.odauday.ui.search.common.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;

/**
 * Created by infamouSs on 4/19/18.
 */
public class LoadingFragment extends Fragment {
    
    public static final String TAG = LoadingFragment.class.getSimpleName();
    
    private LoadingFragmentListener mLoadingFragmentListener;
    
    public static LoadingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    public void setLoadingFragmentListener(
              LoadingFragmentListener loadingFragmentListener) {
        mLoadingFragmentListener = loadingFragmentListener;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
              @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_loading_search_tab, container, false);
        
        view.setOnClickListener(v -> {
            if (mLoadingFragmentListener == null) {
                return;
            }
            mLoadingFragmentListener.onClickRetry();
        });
        
        return view;
    }
    
    public interface LoadingFragmentListener {
        
        void onClickRetry();
    }
}
