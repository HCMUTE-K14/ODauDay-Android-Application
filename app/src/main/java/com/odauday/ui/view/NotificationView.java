package com.odauday.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.odauday.R;

/**
 * Created by kunsubin on 4/10/2018.
 */

public class NotificationView<T> extends RelativeLayout {
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;
    private OnClickNotificationListener mOnClickNotificationListener;
    enum STATUS {
        CHECK, UN_CHECK
    }
    private STATUS mSTATUS = STATUS.UN_CHECK;
    
    public NotificationView(Context context) {
        super(context);
        init(context);
    }
    
    public NotificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public NotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_notification, this, true);
        bindingView(rootView);
        changeStatus(context);
    }
    
    private void bindingView(View rootView) {
        if (rootView == null) {
            return;
        }
        mRelativeLayout = rootView.findViewById(R.id.relative_layout_notification);
        mImageView = rootView.findViewById(R.id.image_notification);
        mSTATUS=STATUS.UN_CHECK;
    }
    private void changeStatus(Context context) {
        switch (mSTATUS) {
            case CHECK:
                mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notification_alert));
                mSTATUS = STATUS.UN_CHECK;
                break;
            case UN_CHECK:
                mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notification_alert_active));
                mSTATUS = STATUS.CHECK;
                break;
            default:
                break;
        }
    }
    public void addOnClick(T item) {
        if (mSTATUS == STATUS.CHECK) {
            mOnClickNotificationListener.offNotification(item);
            changeStatus(getContext());
            return;
        }
        if (mSTATUS == STATUS.UN_CHECK) {
            mOnClickNotificationListener.onNotification(item);
            changeStatus(getContext());
            return;
        }
    }
    
    public void setOnClickNotificationListener(
        OnClickNotificationListener onClickNotificationListener) {
        mOnClickNotificationListener = onClickNotificationListener;
    }
    
    public void setSTATUS(STATUS STATUS) {
        this.mSTATUS = STATUS;
        changeStatus(getContext());
    }
    public interface OnClickNotificationListener<T> {
        void onNotification(T item);
        void offNotification(T item);
    }
}
