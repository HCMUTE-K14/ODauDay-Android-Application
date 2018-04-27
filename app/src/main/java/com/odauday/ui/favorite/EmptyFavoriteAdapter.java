package com.odauday.ui.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.ui.favorite.EmptyFavoriteAdapter.EmptyViewHolder;

/**
 * Created by kunsubin on 4/5/2018.
 */

public class EmptyFavoriteAdapter extends RecyclerView.Adapter<EmptyViewHolder> {
    
    
    @Override
    public EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_empty_favorite, parent, false);
        
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
