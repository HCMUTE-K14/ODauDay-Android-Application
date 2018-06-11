package com.odauday.ui.propertydetail.rowdetails.vital;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 5/8/18.
 */
public class VitalDetailViewHolder extends BaseRowViewHolder<VitalDetailRow> {
    
    TextView mPrice;
    TextView mAddress;
    TextView mStatus;
    Context mContext;
    
    public VitalDetailViewHolder(View view) {
        super(view);
        this.mContext = view.getContext();
        this.mPrice = this.itemView.findViewById(R.id.price);
        this.mAddress = this.itemView.findViewById(R.id.address);
        this.mStatus = this.itemView.findViewById(R.id.status);
    }
    
    @Override
    protected void update(VitalDetailRow vitalDetailRow) {
        super.update(vitalDetailRow);
        
        PropertyDetail propertyDetail = vitalDetailRow.getData();
        if (propertyDetail == null) {
            return;
        }
        String txtStatus;
        if (propertyDetail.getType() == 2) {
            txtStatus =
                mContext.getString(propertyDetail.getTextType()) + " " +
                mContext.getString(R.string.txt_per_month);
        } else {
            txtStatus = mContext.getString(propertyDetail.getTextType());
        }
        mStatus.setText(txtStatus);
        
        mPrice.setText(propertyDetail.getTextPrice());
        
        mAddress.setText(TextUtils.formatAddress(propertyDetail.getAddress()));
    }
    
    @Override
    public void unbind() {
        super.unbind();
        mPrice = null;
        mAddress = null;
        mStatus = null;
        mContext = null;
    }
}
