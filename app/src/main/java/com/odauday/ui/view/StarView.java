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

public class StarView extends RelativeLayout {
    
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;
    private OnClickStarListener mOnClickStarListener;
    enum STATUS{
        CHECK,UN_CHECK
    }
    private STATUS mSTATUS=STATUS.UN_CHECK;
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
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_star, this, true);
        bindingView(rootView);
        chaneStatus(context);
        addOnClick(context);
    }
    
   
    private void bindingView(View rootView) {
        if(rootView==null){
            return;
        }
        mRelativeLayout=rootView.findViewById(R.id.relative_layout_star);
        mImageView=rootView.findViewById(R.id.image_star);
        mSTATUS=STATUS.UN_CHECK;
    }
    private void chaneStatus(Context context){
        switch (mSTATUS){
            case CHECK:
                mImageView.setColorFilter(ContextCompat.getColor(context,R.color.yellow));
                break;
            case UN_CHECK:
                mImageView.setColorFilter(ContextCompat.getColor(context,R.color.white));
                break;
            default:
                break;
        }
    }
    private void addOnClick(Context context) {
        mRelativeLayout.setOnClickListener(view -> {
            if(mSTATUS==STATUS.CHECK){
                mOnClickStarListener.onUnCheckStar();
                mSTATUS=STATUS.UN_CHECK;
                chaneStatus(context);
                return;
            }
            if(mSTATUS==STATUS.UN_CHECK){
                mOnClickStarListener.onCheckStar();
                mSTATUS=STATUS.CHECK;
                chaneStatus(context);
                return;
            }
        });
    }
    
    public void setOnClickStarListener(OnClickStarListener onClickStarListener) {
        mOnClickStarListener = onClickStarListener;
    }
    
    public interface OnClickStarListener{
        void onCheckStar();
        void onUnCheckStar();
    }
}
