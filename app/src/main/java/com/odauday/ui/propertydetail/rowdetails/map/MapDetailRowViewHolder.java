package com.odauday.ui.propertydetail.rowdetails.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;

/**
 * Created by infamouSs on 5/8/18.
 */
public class MapDetailRowViewHolder extends BaseRowViewHolder<MapDetailRow> {
    
    ImageView mMap;
    ImageView mStreetView;
    ImageView mDirections;
    
    private PropertyDetail mData;
    
    public MapDetailRowViewHolder(View view) {
        super(view);
        mMap = itemView.findViewById(R.id.map);
        mStreetView = itemView.findViewById(R.id.street_view);
        mDirections = itemView.findViewById(R.id.directions);
    }
    
    @Override
    protected void update(MapDetailRow mapDetailRow) {
        super.update(mapDetailRow);
        mData = mapDetailRow.getData();
        
        if (mData == null) {
            return;
        }
        
        String urlMap = TextUtils.buildUrlStaticMap(mData.getLocation(), 13, "600x300");
        
        Glide.with(mMap.getContext())
            .load(urlMap)
            .apply(new RequestOptions()
                .placeholder(R.drawable.map_placeholder)
                .error(R.drawable.map_placeholder))
            .into(mMap);
        
        mMap.setOnClickListener(this::onClickMap);
        mStreetView.setOnClickListener(this::onClickStreetView);
        mDirections.setOnClickListener(this::onClickDirections);
    }
    
    @Override
    public void unbind() {
        mMap = null;
        mStreetView = null;
        mDirections = null;
    }
    
    public void onClickMap(View view) {
        Context context = this.itemView.getContext();
        Uri gmmIntentUri;
        GeoLocation location = mData.getLocation();
        if (location != null) {
            gmmIntentUri = Uri.parse("geo:" + location.getLatitude() +
                                     "," +
                                     location.getLongitude() + "?q=" +
                                     location.getLatitude() +
                                     "," +
                                     location.getLongitude() + "(" +
                                     TextUtils.formatAddress(mData.getAddress()) + ")");
        } else {
            gmmIntentUri = Uri.parse(Constants.MAPS_INTENT_URI + mData.getAddress());
        }
        Intent mapIntent = new Intent("android.intent.action.VIEW", gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (ViewUtils.isIntentAvailable(context, mapIntent)) {
            context.startActivity(mapIntent);
        }
    }
    
    public void onClickStreetView(View view) {
        GeoLocation location = mData.getLocation();
        Intent streetView = new Intent("android.intent.action.VIEW", Uri
            .parse("google.streetview:cbll=" + location.getLatitude() +
                   "," + location.getLongitude() +
                   "&cbp=1,99.56,,1,-5.27&mz=21"));
        Context context = this.itemView.getContext();
        if (!ViewUtils.isIntentAvailable(context, streetView)) {
            streetView = new Intent("android.intent.action.VIEW",
                Uri.parse("market://details?id=com.google.android.street"));
            Toast.makeText(context, R.string.message_google_street_view_missing, Toast.LENGTH_LONG)
                .show();
        }
        context.startActivity(streetView);
    }
    
    public void onClickDirections(View view) {
        Context context = this.itemView.getContext();
        Intent intent = createMapDirectionsIntent(mData.getLocation());
        if (ViewUtils.isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.message_error_no_maps_directions, Toast.LENGTH_LONG)
                .show();
        }
    }
    
    private Intent createMapDirectionsIntent(GeoLocation location) {
        String q;
        q = location.getLatitude() + "," + location.getLongitude();
        Intent intent = new Intent("android.intent.action.VIEW",
            Uri.parse("google.navigation:q=" + q));
        intent.setPackage("com.google.android.apps.maps");
        return intent;
    }
}
