package com.odauday.ui.search.mapview;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;
import com.odauday.R;

/**
 * Created by infamouSs on 4/11/18.
 */

public class MapOverlayView extends RelativeLayout {
    
    private MapOverlayListener mMapOverlayListener;
    
    public MapOverlayView(Context context) {
        super(context);
        init(context);
    }
    
    public MapOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public MapOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.layout_overlay_map, this, true);
        
        setupListener(context);
    }
    
    private void setupListener(Context context) {
        getButtonMyLocation().setOnClickListener(view -> {
            if (mMapOverlayListener != null) {
                mMapOverlayListener.onClickMyLocation();
            }
        });
        
        getButtonMapLayer().setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                      .setTitle(context.getString(R.string.txt_dialog_title_map_type))
                      .setItems(R.array.map_type, (dialogInterface, i) -> {
                          if (mMapOverlayListener != null) {
                              mMapOverlayListener.onClickMapLayer(i);
                          }
                      });
            builder.create().show();
        });
        
        getButtonLockMap().setOnCheckedChangeListener((compoundButton, b) -> {
            if (mMapOverlayListener != null) {
                mMapOverlayListener.onMapLockToggle(b);
            }
        });
    }
    
    public Button getButtonMyLocation() {
        return getRootView().findViewById(R.id.btn_my_location);
    }
    
    public Button getButtonMapLayer() {
        return getRootView().findViewById(R.id.btn_map_layer);
    }
    
    public ToggleButton getButtonLockMap() {
        return getRootView().findViewById(R.id.btn_lock_map);
    }
    
    public void setMapOverlayListener(
              MapOverlayListener mapOverlayListener) {
        mMapOverlayListener = mapOverlayListener;
    }
    
    public interface MapOverlayListener {
        
        void onClickMyLocation();
        
        void onClickMapLayer(int type);
        
        void onMapLockToggle(boolean isMapUnlocked);
    }
}
