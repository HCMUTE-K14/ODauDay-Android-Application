package com.odauday.ui.search.common.view.tagdialog;

import static com.odauday.config.Constants.INTENT_EXTRA_RECENT_TAG;
import static com.odauday.config.Constants.INTENT_EXTRA_SELECTED_TAG;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.odauday.R;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.view.OnCompletePickedType;
import com.odauday.ui.search.common.view.tagdialog.TagRecentAdapter.OnClickTagRecent;
import com.pchmn.materialchips.ChipsInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class TagTypeDialog extends BaseDialogFragment implements OnClickTagRecent {
    
    
    private static final float TEXT_SIZE = 18.0f;
    
    private ChipsInput mChipsInput;
    private RecyclerView mRecyclerView;
    private OnCompletePickedType mOnCompletePickedType;
    
    
    public static TagTypeDialog newInstance(List<Tag> selectedTags, List<Tag> recentTags) {
        
        Bundle args = new Bundle();
        
        args.putParcelableArrayList(INTENT_EXTRA_SELECTED_TAG, (ArrayList<Tag>) selectedTags);
        args.putParcelableArrayList(INTENT_EXTRA_RECENT_TAG, (ArrayList<Tag>) recentTags);
        
        TagTypeDialog fragment = new TagTypeDialog();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View mView = View.inflate(getActivity(), R.layout.layout_dialog_choose_tag, null);
        
        mRecyclerView = mView.findViewById(R.id.recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                  mRecyclerView.getContext(),
                  LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        
        mChipsInput = mView.findViewById(R.id.chips_input);
        mChipsInput.getEditText().setTextSize(TEXT_SIZE);
        
        if (getArguments() == null) {
            throw new IllegalArgumentException(
                      "Need EXTRA_INTENT_SECLTED_TAG && EXTRA_INTENT_RECENT_TAG");
        }
        
        List<Tag> selectedTags = getArguments()
                  .getParcelableArrayList(INTENT_EXTRA_SELECTED_TAG);
        
        List<Tag> recentTags = getArguments().getParcelableArrayList(INTENT_EXTRA_RECENT_TAG);
        setChipsData();
        setSelectedChipData(selectedTags);
        initRecentTag(recentTags);
        
        setTitle(getString(R.string.txt_filter_tags));
        setContent(mView);
        setPositiveAlertDialogButton(getString(R.string.txt_done), (view, which) -> {
            Fragment fragment = TagTypeDialog.this.getTargetFragment();
            if (fragment != null && (fragment instanceof OnCompletePickedType)) {
                ((OnCompletePickedType) fragment)
                          .onCompletePickedType(
                                    TagTypeDialog.this.getTargetRequestCode(),
                                    Collections
                                              .unmodifiableList(mChipsInput.getSelectedChipList()));
            }
            dismiss();
        });
        
        return create();
    }
    
    @Override
    public void onRecentTagClick(Tag tag) {
        addTagChip(tag);
    }
    
    private void setChipsData() {
        List<String> data = Arrays.asList(getResources().getStringArray(R.array.tags));
        List<TagChip> tagChips = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            tagChips.add(new TagChip(new Tag(String.valueOf(i), data.get(i))));
        }
        mChipsInput.setFilterableList(tagChips);
    }
    
    private void setSelectedChipData(List<Tag> selectedTags) {
        if (selectedTags != null) {
            for (Tag tag : selectedTags) {
                addTagChip(tag);
            }
        }
    }
    
    private void addTagChip(Tag tag) {
        mChipsInput.addChip(tag.getId(), (Uri) null, tag.getName(), "");
    }
    
    private void initRecentTag(List<Tag> recentTags) {
        TagRecentAdapter adapter = new TagRecentAdapter(recentTags);
        adapter.setOnClickTagRecent(this);
        
        mRecyclerView.setAdapter(adapter);
    }
    
    public OnCompletePickedType getOnCompletePickedType() {
        return mOnCompletePickedType;
    }
    
    public void setOnCompletePickedType(
              OnCompletePickedType onCompletePickedType) {
        mOnCompletePickedType = onCompletePickedType;
    }
}
