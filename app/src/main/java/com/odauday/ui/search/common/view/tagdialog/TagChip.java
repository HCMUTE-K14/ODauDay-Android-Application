package com.odauday.ui.search.common.view.tagdialog;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.odauday.model.Tag;
import com.pchmn.materialchips.model.ChipInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/4/2018.
 */
public class TagChip implements ChipInterface {
    
    private Tag tag;
    
    public TagChip(Tag tag) {
        this.tag = tag;
    }
    
    public static List<Tag> convertToTag(List<ChipInterface> tagChips) {
        List<Tag> tags = new ArrayList<>();
        for (ChipInterface tagChip : tagChips) {
            tags.add(new Tag(tagChip.getId().toString(), tagChip.getLabel()));
        }
        
        return tags;
    }
    
    @Override
    public Object getId() {
        return this.tag.getId();
    }
    
    @Override
    public Uri getAvatarUri() {
        return null;
    }
    
    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }
    
    @Override
    public String getLabel() {
        return this.tag.getName();
    }
    
    @Override
    public String getInfo() {
        return null;
    }
    
    public Tag getTag() {
        return tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    @Override
    public String toString() {
        return "TagChip{" +
               "tag=" + tag +
               '}';
    }
}
