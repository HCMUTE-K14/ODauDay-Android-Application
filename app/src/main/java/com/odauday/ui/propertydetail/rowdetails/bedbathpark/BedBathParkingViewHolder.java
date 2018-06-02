package com.odauday.ui.propertydetail.rowdetails.bedbathpark;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.model.Category;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.search.navigation.PropertyType;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;
import java.util.List;

/**
 * Created by infamouSs on 5/8/18.
 */
public class BedBathParkingViewHolder extends BaseRowViewHolder<BedBathParkingRow> {
    
    
    LinearLayout mBedsContainer;
    LinearLayout mBathContainer;
    LinearLayout mParkingContainer;
    LinearLayout mLandSizeContainer;
    
    TextView mBeds;
    TextView mBaths;
    TextView mParkings;
    TextView mLandSize;
    TextView mCategory;
    
    Context mContext;
    
    public BedBathParkingViewHolder(View view) {
        super(view);
        mContext = view.getContext();
        
        mBedsContainer = itemView.findViewById(R.id.beds_container);
        mBathContainer = itemView.findViewById(R.id.baths_container);
        mParkingContainer = itemView.findViewById(R.id.parkings_container);
        mLandSizeContainer = itemView.findViewById(R.id.land_size_container);
        
        mBeds = itemView.findViewById(R.id.beds);
        mBaths = itemView.findViewById(R.id.baths);
        mLandSize = itemView.findViewById(R.id.land_size);
        mParkings = itemView.findViewById(R.id.parkings);
        mCategory = itemView.findViewById(R.id.category);
    }
    
    
    @Override
    protected void update(BedBathParkingRow bedBathParkingRow) {
        super.update(bedBathParkingRow);
        PropertyDetail propertyDetail = bedBathParkingRow.getData();
        
        if (propertyDetail == null) {
            return;
        }
        
        int beds = propertyDetail.getNumOfBedrooms();
        int baths = propertyDetail.getNumOfBathrooms();
        int parking = propertyDetail.getNumOfParkings();
        
        double landSize = propertyDetail.getSize();
        
        showData(mBedsContainer, mBeds, beds);
        showData(mBathContainer, mBaths, baths);
        showData(mParkingContainer, mParkings, parking);
        
        if (landSize <= 0) {
            ViewUtils.showHideView(mLandSizeContainer, false);
        } else {
            String textLandSize = String.valueOf(landSize) + "m²";
            mLandSize.setText(textLandSize);
            ViewUtils.showHideView(mLandSizeContainer, true);
        }
        
        StringBuilder builder = new StringBuilder();
        List<Category> categories = propertyDetail.getCategories();
        List<PropertyType> propertyTypes = PropertyType.convertCategoriesToPropertyType(categories);
        
        for (PropertyType propertyType : propertyTypes) {
            appendFeatureWithComma(builder,
                mContext.getString(propertyType.getDisplayStringResource()));
        }
        mCategory.setText(builder.toString());
    }
    
    @Override
    public void unbind() {
        super.unbind();
        mBedsContainer = null;
        mBathContainer = null;
        mParkingContainer = null;
        mLandSizeContainer = null;
        
        mBeds = null;
        mBaths = null;
        mParkings = null;
        mLandSize = null;
        
        mContext = null;
    }
    
    protected void appendFeatureWithComma(StringBuilder sb, String feature) {
        if (sb.length() == 0) {
            sb.append(TextUtils.isEmpty(feature) ? "" : feature.trim());
        } else {
            sb.append(TextUtils.isEmpty(feature) ? "" : " • " + feature.trim());
        }
    }
    
    private void showData(View container, TextView textView, int value) {
        if (value <= 0) {
            ViewUtils.showHideView(container, false);
        } else {
            textView.setText(String.valueOf(value));
            ViewUtils.showHideView(container, true);
        }
    }
    
}
