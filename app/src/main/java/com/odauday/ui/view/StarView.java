package com.odauday.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.odauday.R;

/**
 * Created by kunsubin on 4/5/2018.
 */

public class StarView<T> extends RelativeLayout {
    
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;
    private OnClickStarListener<T> mOnClickStarListener;
    private STATUS mSTATUS = STATUS.UN_CHECK;
    
    public StarView(Context context) {
        super(context);
        init(context);
    }
    
    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_star, this, true);
        bindingView(rootView);
        changeStatus(context);
    }
    
    private void bindingView(View rootView) {
        if (rootView == null) {
            return;
        }
        mRelativeLayout = rootView.findViewById(R.id.relative_layout_star);
        mImageView = rootView.findViewById(R.id.image_star);
        
        mSTATUS = STATUS.UN_CHECK;
    }
    
    public void setTintColor(int color) {
        mImageView.setColorFilter(ContextCompat.getColor(getContext(), color),
            android.graphics.PorterDuff.Mode.MULTIPLY);
    }
    
    public void setBackGroundColor(int color) {
        mRelativeLayout.setBackgroundColor(color);
    }
    
    private void changeStatus(Context context) {
        switch (mSTATUS) {
            case CHECK:
                mImageView.setImageDrawable(
                    context.getResources().getDrawable(R.drawable.ic_listing_shortlist_no_padding));
                mSTATUS = STATUS.UN_CHECK;
                break;
            case UN_CHECK:
                mImageView.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.ic_listing_shortlist_selected_no_padding));
                mSTATUS = STATUS.CHECK;
                break;
            default:
                break;
        }
    }
    
    public void setSTATUS(STATUS STATUS) {
        this.mSTATUS = STATUS;
        changeStatus(getContext());
    }
    
    public void addOnClick(T item) {
        if (mSTATUS == STATUS.CHECK) {
            mOnClickStarListener.onUnCheckStar(item);
            changeStatus(getContext());
            return;
        }
        
        mOnClickStarListener.onCheckStar(item);
        changeStatus(getContext());
    }
    
    public void setOnClickStarListener(OnClickStarListener<T> onClickStarListener) {
        mOnClickStarListener = onClickStarListener;
    }
    
    public enum STATUS {
        CHECK, UN_CHECK
    }
    
    public interface OnClickStarListener<T> {
        
        void onCheckStar(T item);
        
        void onUnCheckStar(T item);
    }
}
