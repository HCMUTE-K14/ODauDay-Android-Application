package com.odauday.ui.propertymanager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.ui.propertymanager.EmptyPropertyAdapter.EmptyPropertyViewHolder;

/**
 * Created by kunsubin on 4/18/2018.
 */

public class EmptyPropertyAdapter extends RecyclerView.Adapter<EmptyPropertyViewHolder> {
    
    
    @Override
    public EmptyPropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_empty_property, parent, false);
    
        return new EmptyPropertyViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(EmptyPropertyViewHolder holder, int position) {
    
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    
    public class EmptyPropertyViewHolder extends ViewHolder{
    
        public EmptyPropertyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
