package com.odauday.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import java.util.Random;

/**
 * Created by infamouSs on 3/6/18.
 */

public class ImageLoader {
    
    public static int[] PLACE_HOLDER = new int[]{
              R.drawable.ic_gallery_placeholder1,
              R.drawable.ic_gallery_placeholder2,
              R.drawable.ic_gallery_placeholder3,
              R.drawable.ic_gallery_placeholder4,
              R.drawable.ic_gallery_placeholder5,
              R.drawable.ic_gallery_placeholder6,
              R.drawable.ic_gallery_placeholder7,
              R.drawable.ic_gallery_placeholder8,
              R.drawable.ic_gallery_placeholder9,
              R.drawable.ic_gallery_placeholder10};
    
    public static void load(ImageView imageView, Object image) {
        int placeHolder = randomPlaceHolder();
        
        Glide.with(imageView.getContext())
                  .load(image)
                  .apply(new RequestOptions()
                            .placeholder(placeHolder)
                            .error(placeHolder))
                  .into(imageView);
    }
    
    public static void loadWithoutOptions(ImageView imageView, Object image) {
        Glide.with(imageView.getContext())
                  .load(image)
                  .into(imageView);
    }
    
    private static int randomPlaceHolder() {
        return new Random().nextInt(PLACE_HOLDER.length);
    }
}
