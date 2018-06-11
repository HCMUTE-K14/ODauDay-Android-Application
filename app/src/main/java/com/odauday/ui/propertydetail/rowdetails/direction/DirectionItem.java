package com.odauday.ui.propertydetail.rowdetails.direction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog.Builder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.ui.propertydetail.common.DirectionLocation;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.NumberUtils;
import com.odauday.utils.TextUtils;
import java.util.Calendar;

/**
 * Created by infamouSs on 5/12/18.
 */
public class DirectionItem extends LinearLayout implements
                                                DirectionItemListener {
    
    ImageView mImageViewMode;
    TextView mLabel;
    TextView mAddress;
    TextView mDuration;
    TextView mDistance;
    ContentLoadingProgressBar mProgressBar;
    RelativeLayout mContainer;
    
    DirectionLocation mDirectionLocation;
    
    public DirectionItem(Context context) {
        super(context);
        init(context);
    }
    
    public DirectionItem(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public DirectionItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.item_direction, this, true);
        
        mImageViewMode = rootView.findViewById(R.id.directions_mode);
        mLabel = rootView.findViewById(R.id.label);
        mAddress = rootView.findViewById(R.id.address);
        mDuration = rootView.findViewById(R.id.duration);
        mDistance = rootView.findViewById(R.id.distance);
        mProgressBar = rootView.findViewById(R.id.progress);
        
        mContainer = rootView.findViewById(R.id.container_item);
        
        mContainer.setOnClickListener(this::onClickDirection);
    }
    
    private void onClickDirection(View view) {
        GeoLocation fromGeoLocation = mDirectionLocation.getFromLocation();
        String latLngFrom = "";
        if (fromGeoLocation != null) {
            latLngFrom = TextUtils.formatGeoLocationForRequest(fromGeoLocation);
        }
        
        String latLngTo = "";
        
        GeoLocation toGeoLocation = mDirectionLocation.getToLocation();
        
        if (toGeoLocation != null) {
            latLngTo = TextUtils.formatGeoLocationForRequest(toGeoLocation);
        }
        
        Intent intent = createMapDirectionsIntent(
            "https://maps.google.com/maps?saddr=" + latLngFrom + " (" +
            mDirectionLocation.getFullAddress() + ")" + "&daddr=" +
            latLngTo + "&dirflg=" + mDirectionLocation.getMode().getMapsApiFlag() +
            "&depature_time=" + Calendar.getInstance().getTimeInMillis());
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(intent);
        } else {
            new Builder(getContext())
                .setTitle(this.getContext().getString(R.string.message_google_maps_missing_title))
                .setMessage(
                    this.getContext().getString(R.string.message_google_maps_missing_message))
                .setPositiveButton(this.getContext().getString(R.string.txt_ok), null).show();
        }
    }
    
    public Intent createMapDirectionsIntent(String url) {
        return new Intent("android.intent.action.VIEW", Uri.parse(url));
    }
    
    public void unbind() {
        mImageViewMode = null;
        mLabel = null;
        mAddress = null;
        mDuration = null;
        mDistance = null;
        mProgressBar = null;
        mContainer = null;
    }
    
    
    @Override
    public void onSuccess(DirectionLocation directionLocation) {
        mDirectionLocation = directionLocation;
        ImageLoader.loadWithoutOptions(mImageViewMode,
            directionLocation.getMode().getDrawableResourceId());
        
        mLabel.setText(directionLocation.getLabel());
        mAddress.setText(directionLocation.getFullAddress());
        mDistance.setText(NumberUtils.meterToKilometer(directionLocation.getDistance()));
        
        double durationInSecond = directionLocation.getArrivalTime();
        int durationInHour = (int) durationInSecond / 3600;
        int durationInMinute = (int) (durationInSecond % 3600) / 60;
        
        int colorDuration = directionLocation.getMode()
            .getColorFromDuration((int) durationInSecond);
        boolean isLongDuration =
            directionLocation.getArrivalTime() > directionLocation.getMode().getLongDuration();
        
        if (isLongDuration) {
            String longDurationStr = null;
            if (durationInHour <= 0) {
                longDurationStr = getContext()
                    .getString(R.string.duration_minutes, durationInMinute);
            } else {
                longDurationStr = getContext()
                    .getString(R.string.duration, durationInHour, durationInMinute);
            }
            
            mDuration.setText(longDurationStr);
        } else {
            int minutes = (int) durationInSecond / 60;
            String minuteDurationStr = getContext()
                .getString(R.string.duration_minutes, minutes);
            
            mDuration.setText(minuteDurationStr);
        }
        mDuration.setTextColor(colorDuration);
    }
    
    @Override
    public void onError(DirectionLocation directionLocation) {
        mDirectionLocation = directionLocation;
        
        mLabel.setText(directionLocation.getLabel());
        mAddress.setText(directionLocation.getFullAddress());
        ImageLoader.loadWithoutOptions(mImageViewMode,
            directionLocation.getMode().getDrawableResourceId());
        mDuration.setText(R.string.txt_no_result);
        mDistance.setVisibility(View.GONE);
    }
    
    @Override
    public void showProgress() {
        mProgressBar.show();
    }
    
    @Override
    public void hideProgress() {
        mProgressBar.hide();
    }
    
    @Override
    public void doFinally() {
    
    }
}
