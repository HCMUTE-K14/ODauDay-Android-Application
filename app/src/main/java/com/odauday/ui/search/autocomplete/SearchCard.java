package com.odauday.ui.search.autocomplete;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.odauday.R;

/**
 * Created by infamouSs on 4/23/18.
 */
public enum SearchCard {
    SUGGESTION(1, R.string.txt_sugguesstion_place, R.layout.item_auto_complete_place) {
        ViewHolder createViewHolder(ViewGroup parent) {
            return new AutoCompletePlaceCardViewHolder(
                      inflateView(parent, this.mLayoutRes), this);
        }
    },
    RECENT_SEARCHES(2, R.string.txt_recent_place, R.layout.item_auto_complete_place) {
        ViewHolder createViewHolder(ViewGroup parent) {
            return new AutoCompletePlaceCardViewHolder(
                      inflateView(parent, this.mLayoutRes), this);
        }
    };
    
    final int mLayoutRes;
    final int mType;
    final int mHeaderRes;
    
    private SearchCard(int type, int headerRes, int layoutRes) {
        this.mLayoutRes = layoutRes;
        this.mHeaderRes = headerRes;
        this.mType = type;
    }
    
    static SearchCard getCard(int type) {
        switch (type) {
            case 1:
                return SUGGESTION;
            case 2:
                return RECENT_SEARCHES;
            default:
                throw new IllegalArgumentException("Not found card with type");
        }
    }
    
    private static View inflateView(ViewGroup parent, int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }
    
    abstract ViewHolder createViewHolder(ViewGroup viewGroup);
}
