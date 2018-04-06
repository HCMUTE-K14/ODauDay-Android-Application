package com.odauday.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.odauday.R;
import com.odauday.model.Property;

/**
 * Created by kunsubin on 4/5/2018.
 */

public class StarView extends RelativeLayout {
    
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;
    private OnClickStarListener mOnClickStarListener;
    private Animation mAnimationRight;
    private Animation mAnimationLeft;
    enum STATUS {
        CHECK, UN_CHECK
    }
    
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
        mAnimationRight = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_right);
        mAnimationLeft = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_left);
        mSTATUS = STATUS.UN_CHECK;
    }
    
    private void changeStatus(Context context) {
        switch (mSTATUS) {
            case CHECK:
                mImageView.startAnimation(mAnimationLeft);
                mImageView.setColorFilter(ContextCompat.getColor(context, R.color.white));
                mSTATUS = STATUS.UN_CHECK;
                break;
            case UN_CHECK:
                mImageView.startAnimation(mAnimationRight);
                mImageView.setColorFilter(ContextCompat.getColor(context, R.color.yellow));
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
    public void addOnClick(Property property) {
        if (mSTATUS == STATUS.CHECK) {
            mOnClickStarListener.onUnCheckStar(property);
            changeStatus(getContext());
            return;
        }
        if (mSTATUS == STATUS.UN_CHECK) {
            mOnClickStarListener.onCheckStar(property);
            changeStatus(getContext());
            return;
        }
    }
    public void setOnClickStarListener(OnClickStarListener onClickStarListener) {
        mOnClickStarListener = onClickStarListener;
    }
    
    public interface OnClickStarListener {
        
        void onCheckStar(Property property);
        
        void onUnCheckStar(Property property);
    }
}
