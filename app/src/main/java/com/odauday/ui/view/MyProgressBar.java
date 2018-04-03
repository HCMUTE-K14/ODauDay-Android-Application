package com.odauday.ui.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.odauday.R;

/**
 * Created by infamouSs on 3/25/18.
 */

public class MyProgressBar extends RelativeLayout {

    private MyProgressBarListener mListener;

    public MyProgressBar(Context context) {
        super(context);
        init(context);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_progress_bar, this, true);

        ProgressBar mProgressBar = rootView.findViewById(R.id.progressbar);

        if (mProgressBar == null) {
            return;
        }
        mProgressBar.getIndeterminateDrawable().setColorFilter(
            ContextCompat.getColor(context, R.color.colorPrimary),
            PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setVisibility(int visibility) {
        if (mListener != null) {
            if (visibility == View.VISIBLE) {
                mListener.onShow();
            } else if (visibility == View.GONE) {
                mListener.onHide();
            }
        }
        super.setVisibility(visibility);
    }

    public void show() {
        this.setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.setVisibility(View.GONE);
    }

    public MyProgressBarListener getListener() {
        return mListener;
    }

    public void setListener(MyProgressBarListener listener) {
        mListener = listener;
    }

    public interface MyProgressBarListener {

        void onShow();

        void onHide();
    }
}
              

