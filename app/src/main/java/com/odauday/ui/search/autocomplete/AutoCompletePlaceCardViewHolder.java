package com.odauday.ui.search.autocomplete;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceAdapter.OnClickItemSearchPlace;
import java.util.List;

/**
 * Created by infamouSs on 4/23/18.
 */
public class AutoCompletePlaceCardViewHolder extends ViewHolder {
    
    private final SearchCard mSearchCard;
    
    private RecyclerView mListContainer;
    
    private RowItemAdapter mRowItemAdapter;
    
    private OnClickItemSearchPlace mOnClickItemSearchPlace;
    
    public AutoCompletePlaceCardViewHolder(View view, SearchCard searchCard) {
        super(view);
        this.mSearchCard = searchCard;
        
        setup();
    }
    
    public void setOnClickItemSearchPlace(
        OnClickItemSearchPlace onClickItemSearchPlace) {
        mOnClickItemSearchPlace = onClickItemSearchPlace;
    }
    
    private void setup() {
        TextView mTextViewHeader = this.itemView.findViewById(R.id.header_name);
        mTextViewHeader.setText(mSearchCard.mHeaderRes);
        
        mListContainer = this.itemView.findViewById(R.id.list_container);
    }
    
    
    public void update(List<AutoCompletePlace> autoCompletePlaces) {
        if (mRowItemAdapter == null) {
            boolean isNeedShowRemoveButton = mSearchCard == SearchCard.RECENT_SEARCHES;
            mRowItemAdapter = new RowItemAdapter(mOnClickItemSearchPlace, isNeedShowRemoveButton);
            mListContainer.setAdapter(mRowItemAdapter);
        }
        mRowItemAdapter.setData(autoCompletePlaces);
    }
    
    public SearchCard getSearchCard() {
        return mSearchCard;
    }
}
