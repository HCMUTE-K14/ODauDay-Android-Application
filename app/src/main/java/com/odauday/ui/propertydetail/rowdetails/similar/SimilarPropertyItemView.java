package com.odauday.ui.propertydetail.rowdetails.similar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import com.odauday.api.EndPoint;
import com.odauday.config.AppConfig;
import com.odauday.config.Constants;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.PropertyDetailActivity;
import com.odauday.ui.propertydetail.common.SimilarProperty;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 5/18/18.
 */
public class SimilarPropertyItemView extends LinearLayout {
    
    private View mRootView;
    private ImageView mImage;
    private TextView mPrice;
    private TextView mAddress;
    private TextView mContent;
    private CardView mContainer;
    
    private SimilarProperty mData;
    
    public SimilarPropertyItemView(Context context) {
        super(context);
        init(context);
    }
    
    public SimilarPropertyItemView(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public SimilarPropertyItemView(Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        
        mRootView = inflater.inflate(R.layout.row_similar_property_item, this, true);
        
        mContainer = mRootView.findViewById(R.id.container);
        mImage = mRootView.findViewById(R.id.image);
        mPrice = mRootView.findViewById(R.id.price);
        mAddress = mRootView.findViewById(R.id.address);
        mContent = mRootView.findViewById(R.id.bed_bath_parking);
        
        mContainer.setOnClickListener(view -> {
            PropertyDetail detail = new PropertyDetail();
            detail.setId(mData.getId());
            detail.setFavorite(mData.isFavorite());
    
            Intent intent = new Intent(getContext(), PropertyDetailActivity.class);
            intent.putExtra(Constants.INTENT_EXTRA_PROPERTY_DETAIL, detail);
    
            getContext().startActivity(intent);
        });
    }
    
    public void bind() {
        if (mData == null) {
            return;
        }
        ImageLoader.load(mImage, EndPoint.BASE_URL + mData.getImage().getUrl(),
            new RequestOptions()
                .placeholder(ImageLoader.randomPlaceHolder())
                .error(R.drawable.ic_no_image));
        String textPrice = TextUtils.formatIntToCurrency((float) mData.getPrice() *
                                                         AppConfig.RATE_VND);
        mPrice.setText(textPrice);
        
        mAddress.setText(mData.getAddress());
        
        String bedBathParkingText = mRootView.getContext()
            .getString(R.string.txt_display_bedroom_bathroom_parking, mData.getNumOfBedroom(),
                mData.getNumOfBathroom(), mData.getNumOfParking());
        
        mContent.setText(bedBathParkingText);
    }
    
    public void unbind() {
        mImage = null;
        mPrice = null;
        mAddress = null;
        mContent = null;
        mRootView = null;
        mData = null;
    }
    
    public SimilarProperty getData() {
        return mData;
    }
    
    public void setData(SimilarProperty data) {
        mData = data;
    }
}
