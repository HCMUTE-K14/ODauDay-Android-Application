package com.odauday.utils;

import android.widget.ImageView;
import com.odauday.R;
import com.squareup.picasso.Picasso;

/**
 * Created by infamouSs on 3/6/18.
 */

public class ImageLoader {
    
    public static void load(ImageView imageView, String image) {
        Picasso.with(imageView.getContext())
                  .load(image)
                  .placeholder(R.drawable.ic_launcher_background)
                  .error(R.drawable.ic_launcher_background)
                  .into(imageView);
    }
}
