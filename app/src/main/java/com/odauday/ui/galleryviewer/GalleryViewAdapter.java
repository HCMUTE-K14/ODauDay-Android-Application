package com.odauday.ui.galleryviewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.odauday.api.EndPoint;
import com.odauday.model.Image;
import java.util.List;

/**
 * Created by infamouSs on 5/19/18.
 */
public class GalleryViewAdapter extends FragmentStatePagerAdapter {
    
    private List<Image> mImages;
    
    public GalleryViewAdapter(FragmentManager manager, List<Image> images) {
        super(manager);
        this.mImages = images;
    }
    
    public int getCount() {
        return mImages == null ? 0 : mImages.size();
    }
    
    public Fragment getItem(final int position) {
        final Image selectedImage = this.mImages.get(position);
        String imageUrl = EndPoint.BASE_URL + selectedImage.getUrl();
        return PropertyImageFragment.newInstance(imageUrl);
    }
}
