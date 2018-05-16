package com.odauday.ui.propertydetail.rowdetails.tag;

import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.model.PropertyDetail;
import com.odauday.model.Tag;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.utils.TextUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/8/18.
 */
public class TagDetailViewHolder extends BaseRowViewHolder<TagDetailRow> {
    
    TextView mTagLeft;
    TextView mTagRight;
    
    public TagDetailViewHolder(View view) {
        super(view);
        mTagLeft = itemView.findViewById(R.id.tags_left);
        mTagRight = itemView.findViewById(R.id.tags_right);
    }
    
    @Override
    protected void update(TagDetailRow tagDetailRow) {
        super.update(tagDetailRow);
        PropertyDetail propertyDetail = tagDetailRow.getData();
        if (propertyDetail == null) {
            return;
        }
        List<Tag> tags = propertyDetail.getTags();
        List<String> tagStrList = new ArrayList<>();
        for (Tag tag : tags) {
            tagStrList.add(TextUtils.getTagsById(Integer.valueOf(tag.getId())));
        }
        int size = tagStrList.size();
        int half = (size / 2) + (size % 2);
        seTags(mTagLeft, tagStrList.subList(0, half));
        seTags(mTagRight, tagStrList.subList(half, size));
    }
    
    private void seTags(TextView textView, List<String> tags) {
        if (tags != null && !tags.isEmpty()) {
            SpannableStringBuilder bulletList = new SpannableStringBuilder();
            int start = 0;
            for (String tag : tags) {
                bulletList.append(tag).append('\n');
                BulletSpan bulletSpan = new BulletSpan();
                int start2 = bulletList.length();
                bulletList.setSpan(bulletSpan, start, start2, 0);
                start = start2;
            }
            textView.setText(bulletList);
        }
    }
    
    @Override
    public void unbind() {
        super.unbind();
        
        mTagLeft = null;
        mTagRight = null;
    }
}
