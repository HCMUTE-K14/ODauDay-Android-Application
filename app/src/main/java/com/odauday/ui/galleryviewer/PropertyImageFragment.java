package com.odauday.ui.galleryviewer;

import static com.odauday.config.Constants.INTENT_EXTRA_SELECTED_IMAGE_URL;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.odauday.R;
import com.odauday.ui.base.BaseFragment;

/**
 * Created by infamouSs on 5/19/18.
 */
public class PropertyImageFragment extends BaseFragment {
    
    
    private SubsamplingScaleImageView mScaleImageView;
    
    
    private String mImageUrl;
    
    public static PropertyImageFragment newInstance(String imageUrl) {
        
        Bundle args = new Bundle();
        
        PropertyImageFragment fragment = new PropertyImageFragment();
        args.putString(INTENT_EXTRA_SELECTED_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_gallery_item;
    }
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        if (getArguments() != null) {
            mImageUrl = getArguments().getString(INTENT_EXTRA_SELECTED_IMAGE_URL);
        }
        
        mScaleImageView = view.findViewById(R.id.image);
        mScaleImageView.setMinimumDpi(50);
        mScaleImageView.setMaxScale(3.0f);
        mScaleImageView.setZoomEnabled(true);
        mScaleImageView.setPanEnabled(true);

        Glide.with(this)
            .asBitmap()
            .load(mImageUrl)
            .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource,
                    @Nullable Transition<? super Bitmap> transition) {
                    mScaleImageView.setImage(ImageSource.bitmap(resource));
                }
            });

        return view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
    }
}
