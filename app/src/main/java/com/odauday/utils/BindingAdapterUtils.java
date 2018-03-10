package com.odauday.utils;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by infamouSs on 3/5/18.
 */

public class BindingAdapterUtils {
    
    
    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String url) {
        ImageLoader.load(imageView, url);
    }
    
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    
}
