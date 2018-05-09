package com.odauday.ui.propertydetail.rowdetails.description;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 5/8/18.
 */
public class DescriptionDetailViewHolder extends BaseRowViewHolder<DescriptionDetailRow> implements
                                                                                         OnClickListener {
    
    private final int mCollapsedHeight = this.itemView.getResources()
        .getDimensionPixelSize(R.dimen.description_collapsed_height);
    
    TextView mDescription;
    Button mReadMore;
    ImageView mOverlay;
    
    private final int mDescriptionWidth;
    private int mMeasuredHeight;
    
    boolean mMoreClicked;
    
    
    public DescriptionDetailViewHolder(View view) {
        super(view);
        mDescription = itemView.findViewById(R.id.description);
        mReadMore = itemView.findViewById(R.id.read_more);
        mOverlay = itemView.findViewById(R.id.more_overlay);
        mDescriptionWidth = view.getContext().getResources().getDisplayMetrics().widthPixels;
        
        mReadMore.setOnClickListener(this);
    }
    
    @Override
    protected void update(DescriptionDetailRow descriptionDetailRow) {
        PropertyDetail propertyDetail = descriptionDetailRow.getData();
        
        if (propertyDetail == null) {
            return;
        }
        
        String description = propertyDetail.getDescription();
        LayoutParams descriptionLayoutParams = this.mDescription.getLayoutParams();
        descriptionLayoutParams.width = -1;
        
        if (TextUtils.isEmpty(description)) {
            mReadMore.setVisibility(View.INVISIBLE);
            mOverlay.setVisibility(View.INVISIBLE);
            mDescription.setText(null);
            descriptionLayoutParams.height = this.mCollapsedHeight;
        } else {
            setTextDescription(description);
            mDescription
                .measure(MeasureSpec.makeMeasureSpec(mDescriptionWidth, MeasureSpec.EXACTLY), 0);
            mMeasuredHeight = this.mDescription.getMeasuredHeight();
            
            boolean showMore;
            if (mMoreClicked || this.mMeasuredHeight <= this.mCollapsedHeight) {
                showMore = false;
            } else {
                showMore = true;
            }
            if (showMore) {
                descriptionLayoutParams.height = this.mCollapsedHeight;
                this.mReadMore.setVisibility(View.VISIBLE);
                this.mOverlay.setVisibility(View.VISIBLE);
            } else {
                descriptionLayoutParams.height = -2;
                this.mReadMore.setVisibility(View.GONE);
                this.mOverlay.setVisibility(View.GONE);
            }
        }
        this.mDescription.setLayoutParams(descriptionLayoutParams);
    }
    
    
    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.read_more) {
            return;
        }
        ValueAnimator anim = ValueAnimator.ofInt(this.mDescription.getMeasuredHeight(),
            this.mMeasuredHeight);
        final int duration = 200;
        anim.addUpdateListener(animation -> {
            int val = (Integer) animation.getAnimatedValue();
            LayoutParams layoutParams = mDescription.getLayoutParams();
            layoutParams.height = val;
            mDescription.setLayoutParams(layoutParams);
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mDescription.setLayoutParams(
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                mDescription.requestLayout();
            }
            
            @Override
            public void onAnimationStart(Animator animation) {
                mOverlay.animate()
                    .alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((Runnable) () -> mOverlay.setVisibility(View.GONE)).run();
                    }
                })
                    .setDuration((long) duration).start();
            }
        });
        anim.setDuration((long) duration);
        anim.start();
        mReadMore.setVisibility(View.GONE);
        mMoreClicked = true;
    }
    
    
    @Override
    public void unbind() {
        mDescription = null;
        mReadMore = null;
        mOverlay = null;
    }
    
    private void setTextDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            mDescription.setVisibility(View.GONE);
        } else {
            mDescription.setText(description);
        }
    }
    
}
