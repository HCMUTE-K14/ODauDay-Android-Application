package com.odauday.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by infamouSs on 3/5/18.
 */

public class BindingAdapterUtils {


    @BindingAdapter("loadImage")
    public static void setImageUri(ImageView view, String imageUri) {
        ImageLoader.load(view, imageUri);
    }

    @BindingAdapter("loadImage")
    public static void setImageUri(ImageView view, Uri imageUri) {
        ImageLoader.load(view, imageUri);
    }

    @BindingAdapter("loadImage")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        ImageLoader.load(view, drawable);
    }

    @BindingAdapter("loadImage")
    public static void setImageResource(ImageView view, int resource) {
        ImageLoader.load(view, resource);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
