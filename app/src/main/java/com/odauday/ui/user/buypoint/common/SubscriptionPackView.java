package com.odauday.ui.user.buypoint.common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.model.Premium;
import com.odauday.ui.user.subscribe.SubscribePremiumActivity;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.TextUtils;

/**
 * Created by infamouSs on 5/31/18.
 */
public class SubscriptionPackView extends LinearLayout {
    
    
    ImageView mImage;
    TextView mName;
    TextView mDescription;
    Button mPrice;
    
    private Premium mPremium;
    
    public SubscriptionPackView(Context context) {
        super(context);
        init(context);
    }
    
    public SubscriptionPackView(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public SubscriptionPackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_subscription_pack, this, true);
        
        mImage = rootView.findViewById(R.id.image);
        mName = rootView.findViewById(R.id.name);
        mDescription = rootView.findViewById(R.id.description);
        mPrice = rootView.findViewById(R.id.price);
        
        mPrice.setOnClickListener(view -> {
            if (mPremium != null) {
                Intent intent = new Intent(getContext(), SubscribePremiumActivity.class);
                intent.putExtra("a", mPremium);
                
                getContext().startActivity(intent);
            }
        });
    }
    
    public Premium getPremium() {
        return mPremium;
    }
    
    public void setPremium(Premium premium) {
        mPremium = premium;
        
        SubscriptionInfor infor = SubscriptionInfor.getById(mPremium.getId());
        
        mName.setText(infor.getName());
        
        String description =
            String.valueOf(mPremium.getValue()) + " " + getContext().getString(R.string.txt_point);
        mDescription.setText(description);
        
        String price = TextUtils.formatIntToCurrency((float) mPremium.getRealValue());
        
        mPrice.setText(price.trim());
        
        ImageLoader.loadWithoutOptions(mImage, infor.getImage());
    }
    
}
