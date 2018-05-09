package com.odauday.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.api.EndPoint;
import com.odauday.config.AppConfig;
import com.odauday.config.AppConfig.LANGUAGE;
import com.odauday.config.Type;
import com.odauday.model.Image;
import com.odauday.ui.propertymanager.status.Status;
import java.util.List;
import timber.log.Timber;


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
    
    @BindingAdapter("textDouble")
    public static void textDouble(TextView view, double value) {
        view.setText(TextUtils.doubleFormat(value));
    }
    
    @BindingAdapter("textFloat")
    public static void textFloat(TextView view, double value) {
        view.setText(TextUtils.formatDecimal(value));
    }
    
    @BindingAdapter("textDoublePrice")
    public static void textDoublePrice(TextView view, double value) {
        String text = new StringBuilder()
            .append(TextUtils.formatNumber(value, LANGUAGE.VI))
            .append(AppConfig.VN_CURRENCY).toString();
        
        view.setText(text);
    }
    
    @BindingAdapter("textInteger")
    public static void textInteger(TextView view, int value) {
        view.setText(String.valueOf(value));
    }
    
    @BindingAdapter("loadImageMainPropertyInListImage")
    public static void loadImageMainPropertyInListImage(ImageView view, List<Image> images) {
        if (images != null && images.size() > 0) {
            ImageLoader.loadWithoutOptions(view, EndPoint.BASE_URL + images.get(0).getUrl());
        }
    }
    
    @BindingAdapter("loadIconMenu")
    public static void loadIconMenu(ImageView view, String icon_name) {
        Context context = view.getContext();
        int resID = context.getResources()
            .getIdentifier(icon_name, "drawable", context.getPackageName());
        view.setImageDrawable(context.getResources().getDrawable(resID));
    }
    
    @BindingAdapter("activeProperty")
    public static void activeProperty(TextView view, String status) {
        Timber.d(status);
        switch (status) {
            case Status.ACTIVE:
                view.setTextColor(view.getContext().getResources().getColor(R.color.colorPrimary));
                view.setText(status);
                break;
            case Status.PENDING:
                view.setTextColor(view.getContext().getResources().getColor(R.color.colorPrimary));
                view.setText(status);
                break;
            case Status.EXPIRED:
                view.setTextColor(view.getContext().getResources().getColor(R.color.red));
                view.setText(status);
                break;
            default:
                break;
        }
    }
    
    @BindingAdapter("setTypeProperty")
    public static void setTypeProperty(TextView view, String type) {
        switch (type) {
            case Type.BUY:
                view.setText(view.getContext().getString(R.string.sell));
                break;
            case Type.RENT:
                view.setText(type);
                break;
            default:
                break;
        }
    }
    
}
