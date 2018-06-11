package com.odauday.ui.alert;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.ui.alert.AlertEmptyAdapter.EmptyViewHolder;

/**
 * Created by kunsubin on 6/4/2018.
 */

public class AlertEmptyAdapter extends RecyclerView.Adapter<EmptyViewHolder> {
    @Override
    public EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_empty_notification, parent, false);
        
        return new EmptyViewHolder(view);
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    
    @Override
    public void onBindViewHolder(EmptyViewHolder holder, int position) {
    
    }
    
    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
