package com.odauday.ui.view;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.odauday.R;

/**
 * Created by kunsubin on 4/4/2018.
 */

public class HeaderFavoriteView extends RelativeLayout {
    private TextView mTextViewTitle;
    private ImageView mImageViewShare;
    private ImageView mImageViewMap;
    private RelativeLayout mRelativeLayoutSort;
    private TextView mTextViewSort;
    private RelativeLayout mRelativeLayoutFilter;
    private TextView mTextViewFilter;
    private OnClickShareListener mOnClickShareListener;
    private OnClickMapListener mOnClickMapListener;
    private PopupMenu mPopupMenuSort;
    private PopupMenu mPopupMenuFilter;
    private OnItemClickMenuSort mOnItemClickMenuSort;
    private OnItemClickMenuFilter mOnItemClickMenuFilter;
    
    public HeaderFavoriteView(Context context) {
        super(context);
        init(context);
    }
    
    public HeaderFavoriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public HeaderFavoriteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            return;
        }
        View rootView = inflater.inflate(R.layout.header_tab_favorite, this, true);
        
        bindView(rootView);
        initPopup(context);
        addOnClick();
    }
    
    private void initPopup(Context context) {
        mPopupMenuSort= new PopupMenu(context, mRelativeLayoutSort);
        mPopupMenuSort.getMenuInflater().inflate(R.menu.popup_menu_sort, mPopupMenuSort.getMenu());
        mPopupMenuSort.setOnMenuItemClickListener(item -> {
            if(mOnItemClickMenuSort!=null){
                mOnItemClickMenuSort.onClickItemMenuSort(item);
            }
            return false;
        });
        mPopupMenuFilter=new PopupMenu(context,mRelativeLayoutFilter);
        mPopupMenuFilter.getMenuInflater().inflate(R.menu.popup_menu_filter, mPopupMenuFilter.getMenu());
        mPopupMenuFilter.setOnMenuItemClickListener(item -> {
            if(mOnItemClickMenuFilter!=null){
                mOnItemClickMenuFilter.onClickItemMenuFilter(item);
            }
            return false;
        });
    }
    
    private void bindView(View rootView) {
        if(rootView==null){
            return;
        }
        mTextViewTitle=rootView.findViewById(R.id.txt_title);
        mImageViewShare=rootView.findViewById(R.id.image_share);
        mImageViewMap=rootView.findViewById(R.id.image_map);
        mRelativeLayoutSort=rootView.findViewById(R.id.relative_layout_sort);
        mTextViewSort=rootView.findViewById(R.id.txt_sort_name);
        mRelativeLayoutFilter=rootView.findViewById(R.id.relative_layout_filter);
        mTextViewFilter=rootView.findViewById(R.id.txt_filter_name);
        
       
    }
    private void addOnClick(){
        if(mOnClickShareListener!=null){
            mImageViewShare.setOnClickListener(view ->{
                mOnClickShareListener.onClick(view);
            });
        }
        if(mOnClickMapListener!=null){
            mImageViewMap.setOnClickListener(view -> {
                mOnClickMapListener.onClick(view);
            });
        }
        mRelativeLayoutSort.setOnClickListener(view -> {
            mPopupMenuSort.show();
        });
        mRelativeLayoutFilter.setOnClickListener(view -> {
            mPopupMenuFilter.show();
        });
        return;
    }
    public void setTitle(String title){
        mTextViewTitle.setText(title);
    }
    public void setOnClickShareListener(OnClickShareListener onClickShareListener){
        this.mOnClickShareListener=onClickShareListener;
    }
    public void setOnClickMapListener(OnClickMapListener onClickMapListener){
        this.mOnClickMapListener=onClickMapListener;
    }
    public void setOnItemClickMenuSort(
              OnItemClickMenuSort onItemClickMenuSort) {
        mOnItemClickMenuSort = onItemClickMenuSort;
    }
    
    public void setOnItemClickMenuFilter(
              OnItemClickMenuFilter onItemClickMenuFilter) {
        mOnItemClickMenuFilter = onItemClickMenuFilter;
    }
    public void setTextViewSort(String textViewSort){
        this.mTextViewSort.setText(textViewSort);
    }
    public void setTextViewFilter(String textViewFilter){
        this.mTextViewFilter.setText(textViewFilter);
    }
    
    public static class Builder{
        private Context mContext;
        private String mTitle;
        private OnClickShareListener mOnClickShareListener;
        private OnClickMapListener mOnClickMapListener;
        private OnItemClickMenuSort mOnItemClickMenuSort;
        private OnItemClickMenuFilter mOnItemClickMenuFilter;
        
        public Builder(Context context){
            this.mContext=context;
        }
        public Builder setTitle(String title){
            this.mTitle=title;
            return this;
        }
        public Builder setOnClickShareListener(OnClickShareListener onClickShareListener){
            this.mOnClickShareListener=onClickShareListener;
            return this;
        }
        public Builder setOnClickMapListener(OnClickMapListener onClickMapListener){
            this.mOnClickMapListener=onClickMapListener;
            return this;
        }
        public Builder setOnItemClickMenuSort(
                  OnItemClickMenuSort onItemClickMenuSort) {
            this.mOnItemClickMenuSort = onItemClickMenuSort;
            return this;
        }
    
        public Builder setOnItemClickMenuFilter(
                  OnItemClickMenuFilter onItemClickMenuFilter) {
            this.mOnItemClickMenuFilter = onItemClickMenuFilter;
            return this;
        }
        public HeaderFavoriteView build(){
            HeaderFavoriteView headerFavoriteView=new HeaderFavoriteView(mContext);
            headerFavoriteView.setTitle(mTitle);
            headerFavoriteView.setOnClickShareListener(mOnClickShareListener);
            headerFavoriteView.setOnClickMapListener(mOnClickMapListener);
            headerFavoriteView.setOnItemClickMenuSort(mOnItemClickMenuSort);
            headerFavoriteView.setOnItemClickMenuFilter(mOnItemClickMenuFilter);
            return headerFavoriteView;
        }
    }
    
    public interface OnClickShareListener{
        void onClick(View view);
    }
    public interface OnClickMapListener{
        void onClick(View view);
    }
    public interface  OnItemClickMenuSort{
        void onClickItemMenuSort(MenuItem item);
    }
    public interface  OnItemClickMenuFilter{
        void onClickItemMenuFilter(MenuItem item);
    }
}
