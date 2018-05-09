package com.odauday.ui.search.autocomplete;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by infamouSs on 4/23/18.
 */
public class AutoCompletePlaceAdapter extends Adapter<ViewHolder> {
    
    private final List<SearchCard> mSearchCards = new ArrayList<>();
    
    private List<AutoCompletePlace> mRecentSearches;
    private List<AutoCompletePlace> mSuggestedLocationList;

    private OnClickItemSearchPlace mOnClickItemSearchPlace;
    
    public AutoCompletePlaceAdapter(OnClickItemSearchPlace onClickItemSearchPlace) {
        this.setOnClickItemSearchPlace(onClickItemSearchPlace);
    }
    
    public void setOnClickItemSearchPlace(
        OnClickItemSearchPlace onClickItemSearchPlace) {
        mOnClickItemSearchPlace = onClickItemSearchPlace;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = SearchCard.getCard(viewType).createViewHolder(parent);
        if (viewHolder instanceof AutoCompletePlaceCardViewHolder) {
            ((AutoCompletePlaceCardViewHolder) viewHolder)
                .setOnClickItemSearchPlace(mOnClickItemSearchPlace);
        }
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof AutoCompletePlaceCardViewHolder) {
            AutoCompletePlaceCardViewHolder autoCompletePlaceCardViewHolder = (AutoCompletePlaceCardViewHolder) holder;

            if (autoCompletePlaceCardViewHolder.getSearchCard() == SearchCard.SUGGESTION &&
                this.mSuggestedLocationList != null) {
                autoCompletePlaceCardViewHolder.update(this.mSuggestedLocationList);
            } else if (autoCompletePlaceCardViewHolder.getSearchCard() ==
                       SearchCard.RECENT_SEARCHES && isListNotEmpty(mRecentSearches)) {
                autoCompletePlaceCardViewHolder.update(mRecentSearches);
            }
        }
    }
    
    @Override
    public int getItemViewType(int position) {
        return mSearchCards.get(position).mType;
    }
    
    @Override
    public int getItemCount() {
        return mSearchCards.size();
    }
    
    private void showRecentSearchesCard() {
        if (isListNotEmpty(this.mRecentSearches)) {
            addCard(SearchCard.RECENT_SEARCHES);
        }
    }
    
    public void setRecentSearches(List<AutoCompletePlace> autoCompletePlaces) {
        if (mRecentSearches == null) {
            this.mRecentSearches = new ArrayList<>();
        }
        this.mRecentSearches.clear();
        this.mRecentSearches.addAll(autoCompletePlaces);
    }
    
    public void setSuggestedLocationList(List<AutoCompletePlace> autoCompletePlaces) {

        if (isListNotEmpty(autoCompletePlaces)) {
            if (mSuggestedLocationList == null) {
                this.mSuggestedLocationList = new ArrayList<>();
            }
            this.mSuggestedLocationList.clear();
            this.mSuggestedLocationList.addAll(autoCompletePlaces);
        }

    }
    
    public void showEmptyStateCards() {
        removeCard(SearchCard.SUGGESTION);
        showRecentSearchesCard();
        notifyDataSetChanged();
    }
    
    private boolean isListNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }
    
    public void removeItemRecentSearch(AutoCompletePlace autoCompletePlace) {
        int index = mRecentSearches.indexOf(autoCompletePlace);
        mRecentSearches.remove(index);
        notifyItemRemoved(index);

        if (mRecentSearches.isEmpty()) {
            hideRecentSearchCard();
        }
    }
    
    private void addCard(SearchCard card) {
        int i = 0;
        while (i < this.mSearchCards.size()) {
            if (this.mSearchCards.get(i) != card) {
                i++;
            } else {
                return;
            }
        }
        this.mSearchCards.add(card);
        notifyItemInserted(this.mSearchCards.size());
    }
    
    private void removeCard(SearchCard searchCard) {
        for (int i = 0; i < this.mSearchCards.size(); i++) {
            if (this.mSearchCards.get(i) == searchCard) {
                this.mSearchCards.remove(i);
                notifyItemRemoved(i + 1);
                return;
            }
        }
    }
    
    public void setData(AutoCompletePlaceCollection collection) {
        if (collection == null) {
            return;
        }

        setRecentSearchListFromAutoCompletePlaceCollection(collection);
        setSuggestionSearchFromAutoCompletePlaceCollection(collection);

        notifyDataSetChanged();
    }
    
    private void setRecentSearchListFromAutoCompletePlaceCollection(
        AutoCompletePlaceCollection collection) {
        List<AutoCompletePlace> recentSearchList = collection.getRecentSearch();
        if (recentSearchList != null) {
            if (recentSearchList.isEmpty()) {
                hideRecentSearchCard();
            } else {
                setRecentSearches(collection.getRecentSearch());
                showRecentSearchCard();
            }
        }
    }
    
    private void setSuggestionSearchFromAutoCompletePlaceCollection(
        AutoCompletePlaceCollection collection) {
        List<AutoCompletePlace> suggestionSearchList = collection.getSuggestionSearch();
        List<AutoCompletePlace> recentSearchList = collection.getRecentSearch();

        if (suggestionSearchList != null) {
            if (suggestionSearchList.isEmpty()) {
                hideSuggestionCard();
            } else {
                suggestionSearchList = removeDuplicateResult(recentSearchList,
                    suggestionSearchList);
                setSuggestedLocationList(suggestionSearchList);
                showSuggestionSearchCard();
            }
        }
    }
    
    private List<AutoCompletePlace> removeDuplicateResult(List<AutoCompletePlace> recentSearches,
        List<AutoCompletePlace> suggestedLocationList) {
        if (isListNotEmpty(recentSearches)) {
            for (AutoCompletePlace i : recentSearches) {
                if (suggestedLocationList.contains(i)) {
                    suggestedLocationList.remove(i);
                }
            }
        }
        return suggestedLocationList;
    }
    
    public void showRecentSearchCard() {
        addCard(SearchCard.RECENT_SEARCHES);
    }
    
    public void showSuggestionSearchCard() {
        addCard(SearchCard.SUGGESTION);
    }
    
    public void hideRecentSearchCard() {
        removeCard(SearchCard.RECENT_SEARCHES);
    }
    
    public void hideSuggestionCard() {
        removeCard(SearchCard.SUGGESTION);
    }
    
    public interface OnClickItemSearchPlace {

        void onSelectedSuggestionPlace(AutoCompletePlace autoCompletePlace);

        void onRemoveRecentSearchPlace(AutoCompletePlace autoCompletePlace);
    }
}
