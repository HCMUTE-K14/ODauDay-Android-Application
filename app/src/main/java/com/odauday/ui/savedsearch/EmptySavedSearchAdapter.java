package com.odauday.ui.savedsearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.ui.savedsearch.EmptySavedSearchAdapter.EmptyViewHolder;

/**
 * Created by kunsubin on 4/10/2018.
 */

public class EmptySavedSearchAdapter extends RecyclerView.Adapter<EmptyViewHolder> {
    
    @Override
    public EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_empty_saved_search, parent, false);
        
        return new EmptyViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(EmptyViewHolder holder, int position) {
    
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    
    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
