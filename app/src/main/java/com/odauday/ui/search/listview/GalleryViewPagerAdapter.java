package com.odauday.ui.search.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.odauday.R;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.model.Image;
import com.odauday.utils.ImageLoader;
import com.odauday.utils.TextUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by infamouSs on 6/1/18.
 */
public class GalleryViewPagerAdapter extends PagerAdapter {
    
    
    private List<Image> mImages;
    private final LinkedList<View> mRecycledViewsList;
    private Context mContext;
    
    public GalleryViewPagerListener mGalleryViewPagerListener;
    
    public GalleryViewPagerAdapter(Context context,
        GalleryViewPagerListener galleryViewPagerListener) {
        mRecycledViewsList = new LinkedList<>();
        mContext = context;
        mGalleryViewPagerListener = galleryViewPagerListener;
    }
    
    @Override
    public int getCount() {
        return mImages == null ? 0 : mImages.size();
    }
    
    public void setData(PropertyResultEntry item) {
        if (mImages == null) {
            mImages = new ArrayList<>();
        }
        mImages.clear();
        mImages
            .add(0, new Image(TextUtils.buildUrlStaticMap(item.getLocation(), 17.0f, "600x300")));
        
        for (Image image : item.getImages()) {
            mImages.add(new Image(TextUtils.getImageUrl(image.getUrl())));
        }
    }
    
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.getTag(R.id.image_view_id) == object;
    }
    
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView;
        
        if (mRecycledViewsList.isEmpty()) {
            imageView = (ImageView) LayoutInflater
                .from(mContext).inflate(R.layout.item_image_view, container, false);
        } else {
            imageView = (ImageView) mRecycledViewsList.pop();
        }
        if (position == 1) {
            if (mGalleryViewPagerListener != null) {
                mGalleryViewPagerListener.showHints();
            }
        } else {
            if (mGalleryViewPagerListener != null) {
                mGalleryViewPagerListener.hideHints();
            }
        }
        
        Image image = mImages.get(position);
        String url = image.getUrl();
        imageView.setTag(R.id.image_view_id, url);
        imageView.setTag(R.id.image_view_position, position);
        Timber.d(url);
        int placeHolder = ImageLoader.randomPlaceHolder();
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.load(imageView, url, new RequestOptions()
                .placeholder(placeHolder)
                .error(placeHolder)
                .priority(getPriority(position))
            );
        }
        
        imageView.setOnClickListener(view -> {
            if (mGalleryViewPagerListener != null) {
                mGalleryViewPagerListener.onClickImage(position);
            }
        });
        
        container.addView(imageView);
        
        return url;
    }
    
    private Priority getPriority(int position) {
        if (position == 0) {
            return Priority.NORMAL;
        }
        if (position == 1) {
            return Priority.HIGH;
        }
        return Priority.LOW;
    }
    
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = container.getChildAt(i);
            if (((Integer) childAt.getTag(R.id.image_view_position)).intValue() == position) {
                ImageView view = (ImageView) childAt;
                freeResources(view);
                container.removeView(view);
                mRecycledViewsList.push(view);
                return;
            }
        }
    }
    
    private void freeResources(ImageView imageView) {
        Glide.get(imageView.getContext()).clearMemory();
        imageView.setImageDrawable(null);
        imageView.setTag(R.id.image_view_state, "discard");
    }
    
    public interface GalleryViewPagerListener {
        
        void showHints();
        
        void hideHints();
        
        void onClickImage(int position);
    }
}
