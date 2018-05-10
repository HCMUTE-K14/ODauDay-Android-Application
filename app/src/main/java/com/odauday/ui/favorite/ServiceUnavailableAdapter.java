package com.odauday.ui.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.odauday.R;
import com.odauday.ui.favorite.ServiceUnavailableAdapter.ServiceUnavailableViewHolder;

/**
 * Created by kunsubin on 4/14/2018.
 */

public class ServiceUnavailableAdapter extends RecyclerView.Adapter<ServiceUnavailableViewHolder> implements OnClickListener {
    private Button mButtonTryAgain;
    private OnClickTryAgain mOnClickTryAgain;
    @Override
    public ServiceUnavailableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.layout_service_unvailable, parent, false);
        mButtonTryAgain=view.findViewById(R.id.btn_try_again);
        mButtonTryAgain.setOnClickListener(this);
        return new ServiceUnavailableViewHolder(view);
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    
    @Override
    public void onBindViewHolder(ServiceUnavailableViewHolder holder, int position) {
    
    }
    
    @Override
    public void onClick(View view) {
        if(mOnClickTryAgain!=null){
            mOnClickTryAgain.clickTryAgain();
        }
    }
    
    public void setOnClickTryAgain(
        OnClickTryAgain onClickTryAgain) {
        mOnClickTryAgain = onClickTryAgain;
    }
    
    public class ServiceUnavailableViewHolder extends RecyclerView.ViewHolder {
        
        public ServiceUnavailableViewHolder(View itemView) {
            super(itemView);
        }
    }
    public interface OnClickTryAgain{
        void clickTryAgain();
    }
}
