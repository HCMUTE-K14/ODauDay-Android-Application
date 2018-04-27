package com.odauday.ui.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;
import com.odauday.ui.favorite.ServiceUnavailableAdapter.ServiceUnavailableViewHolder;

/**
 * Created by kunsubin on 4/14/2018.
 */

public class ServiceUnavailableAdapter extends RecyclerView.Adapter<ServiceUnavailableViewHolder> {
    
    @Override
    public ServiceUnavailableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_service_unvailable, parent, false);
        
        return new ServiceUnavailableViewHolder(view);
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    
    @Override
    public void onBindViewHolder(ServiceUnavailableViewHolder holder, int position) {
    
    }
    
    public class ServiceUnavailableViewHolder extends RecyclerView.ViewHolder {
        
        public ServiceUnavailableViewHolder(View itemView) {
            super(itemView);
        }
    }
}
