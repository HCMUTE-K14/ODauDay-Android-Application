package com.odauday.ui.search.common.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.config.AppConfig;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 4/18/18.
 */
public class VitalPropertyView extends RelativeLayout {
    
    ImageView mImageView;
    TextView mTextViewPrice;
    TextView mTextViewFeature;
    TextView mTextViewAddress;
    private PropertyResultEntry mProperty;
    
    public VitalPropertyView(Context context) {
        super(context);
        init(context);
    }
    
    public VitalPropertyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public VitalPropertyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_property_detail_vital, this, true);
        
        mImageView = rootView.findViewById(R.id.image);
        mTextViewPrice = rootView.findViewById(R.id.txt_price);
        mTextViewFeature = rootView.findViewById(R.id.txt_feature);
        mTextViewAddress = rootView.findViewById(R.id.txt_address);
    }
    
    
    public ImageView getImageView() {
        return mImageView;
    }
    
    public TextView getTextViewPrice() {
        return mTextViewPrice;
    }
    
    public TextView getTextViewFeature() {
        return mTextViewFeature;
    }
    
    public TextView getTextViewAddress() {
        return mTextViewAddress;
    }
    
    public PropertyResultEntry getProperty() {
        return mProperty;
    }
    
    public void setProperty(PropertyResultEntry property) {
        mProperty = property;
        if (!property.getImages().isEmpty()) {
            String urlFirstImage = property.getImages().get(0).getUrl();
            setImage(urlFirstImage);
        } else {
            setImage(ImageLoader.randomPlaceHolder());
        }
        
        String buildPriceText = TextUtils.formatIntToCurrency((float) property.getPrice() *
                                                              AppConfig.RATE_VND);
        setPrice(buildPriceText);
        
        String buildFeatureText = new StringBuilder()
                  .append(property.getNumOfBathRooms())
                  .append(" ")
                  .append(getContext().getString(R.string.txt_bedrooms))
                  .append(", ")
                  .append(property.getNumOfBedRooms())
                  .append(" ")
                  .append(getContext().getString(R.string.txt_bathrooms))
                  .append(", ")
                  .append(property.getNumOfParkings())
                  .append(" ")
                  .append(getContext().getString(R.string.txt_parking))
                  .toString();
        
        setFeature(buildFeatureText);
        
        String address = new StringBuilder()
                  .append("<b>")
                  .append(getContext().getString(R.string.txt_at))
                  .append("</b> ")
                  .append(property.getAddress()).toString();
        
        getTextViewAddress().setText(Html.fromHtml(address));
    }
    
    public void setImage(String url) {
        ImageLoader.load(this.getContext(), mImageView, url);
    }
    
    public void setImage(int resourceId) {
        ImageLoader.load(this.getContext(), mImageView, resourceId);
    }
    
    public void setPrice(String price) {
        getTextViewPrice().setText(price);
    }
    
    public void setFeature(String feature) {
        getTextViewFeature().setText(feature);
    }
    
    public void setAddress(String address) {
        getTextViewAddress().setText(address);
    }
}
