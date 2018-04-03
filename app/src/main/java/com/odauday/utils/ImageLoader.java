package com.odauday.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;

/**
 * Created by infamouSs on 3/6/18.
 */

public class ImageLoader {

    public static void load(ImageView imageView, Object image) {
        Glide.with(imageView.getContext())
            .load(image)
            .apply(new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background))
            .into(imageView);
    }

    public static void loadWithoutOptions(ImageView imageView, Object image) {
        Glide.with(imageView.getContext())
            .load(image)
            .into(imageView);
    }
}
