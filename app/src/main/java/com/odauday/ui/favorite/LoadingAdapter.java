package com.odauday.ui.favorite;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.ui.favorite.LoadingAdapter.LoadingViewHolder;

/**
 * Created by kunsubin on 5/4/2018.
 */

public class LoadingAdapter extends RecyclerView.Adapter<LoadingViewHolder> {
    
    @Override
    public LoadingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_adapter_loading, parent, false);
    
        return new LoadingViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(LoadingViewHolder holder, int position) {
    
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    public class LoadingViewHolder extends ViewHolder{
    
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
