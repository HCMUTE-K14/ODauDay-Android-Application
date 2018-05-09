package com.odauday.ui.propertydetail.rowdetails.direction;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceActivity;

/**
 * Created by infamouSs on 5/9/18.
 */
public class DirectionFormViewHolder {
    
    private TextView mDirectionOrigin;
    private View mParent;
    
    public DirectionFormViewHolder(View view) {
        mParent = view;
        mDirectionOrigin = view.findViewById(R.id.direction_origin);
        TextView directionDestination = view.findViewById(R.id.direction_destination);
        directionDestination.setOnClickListener(direct -> openSearchActivity(direct.getContext()));
    }
    
    
    public void update(PropertyDetail data) {
        if (data != null) {
            mDirectionOrigin.setText(data.getAddress());
        }
    }
    
    public void openSearchActivity(Context context) {
        Intent intent = new Intent(context, AutoCompletePlaceActivity.class);
        context.startActivity(intent);
    }
    
    public void unbind() {
        mDirectionOrigin = null;
        mParent = null;
    }
    
    public View getParent() {
        return mParent;
    }
}
