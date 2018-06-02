package com.odauday.ui.view.maplistbutton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.utils.ImageLoader;

/**
 * Created by infamouSs on 3/31/18.
 */

public class MapListToggleButton extends RelativeLayout {
    
    private RelativeLayout mMapListButton;
    
    private ImageView mImageView;
    
    private TextView mTextView;
    
    private OnClickMapListListener mButtonListener;
    
    private StateMapListButton mCurrentState = StateMapListButton.SHOWING_MAP_VIEW;
    
    public MapListToggleButton(@NonNull Context context) {
        super(context);
        init(context);
    }
    
    public MapListToggleButton(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public MapListToggleButton(@NonNull Context context, @Nullable AttributeSet attrs,
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
        View rootView = inflater.inflate(R.layout.layout_map_list_toggle_button, this, true);
        
        bindView(rootView);
        
        addListener();
    }
    
    
    private void bindView(View rootView) {
        if (rootView == null) {
            return;
        }
        
        mMapListButton = rootView.findViewById(R.id.btn_toggle_map_list);
        mImageView = rootView.findViewById(R.id.image_btn);
        mTextView = rootView.findViewById(R.id.text_btn);
    }
    
    private void addListener() {
        if (mMapListButton == null) {
            return;
        }
        
        mMapListButton.setOnClickListener(view -> changeState());
    }
    
    public void changeState() {
        
        if (mButtonListener == null) {
            return;
        }
        
        if (mCurrentState == null) {
            mCurrentState = StateMapListButton.SHOWING_MAP_VIEW;
        }
        
        switch (mCurrentState) {
            case SHOWING_MAP_VIEW:
                mButtonListener.onShowListView();
                
                mCurrentState = StateMapListButton.SHOWING_LIST_VIEW;
                break;
            case SHOWING_LIST_VIEW:
                mButtonListener.onShowMapView();
                
                mCurrentState = StateMapListButton.SHOWING_MAP_VIEW;
                break;
        }
        changeView(mCurrentState);
    }
    
    private void changeView(StateMapListButton currentState) {
        if (currentState == StateMapListButton.SHOWING_MAP_VIEW) {
            ImageLoader.loadWithoutOptions(mImageView, R.drawable.ic_fab_list);
            mTextView.setText(getContext().getString(R.string.txt_list_view));
        } else if (currentState == StateMapListButton.SHOWING_LIST_VIEW) {
            ImageLoader.loadWithoutOptions(mImageView, R.drawable.ic_fab_map);
            mTextView.setText(getContext().getString(R.string.txt_map_view));
        }
    }
    
    public void setOnClickMapListListener(
        OnClickMapListListener buttonListener) {
        mButtonListener = buttonListener;
    }
    
    public interface OnClickMapListListener {
        
        void onShowListView();
        
        void onShowMapView();
    }
}
