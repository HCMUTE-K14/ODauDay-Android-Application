package com.odauday.ui.search.common.view;

import static com.odauday.config.Constants.INTENT_EXTRA_LATLNG_CENTER;
import static com.odauday.config.Constants.INTENT_EXTRA_SAVE_SEARCH_CRITERIA;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.SearchCriteria;

/**
 * Created by infamouSs on 4/23/18.
 */
public class SavedSearchDialog extends BaseDialogFragment {
    
    public static final int REQUEST_CODE = 123;
    
    private SearchCriteria mSearchCriteria;
    private GeoLocation mLocation;
    
    private SaveSearchListener mSaveSearchListener;
    
    public static SavedSearchDialog newInstance(SearchCriteria searchCriteria,
              GeoLocation location) {
        SavedSearchDialog dialog = new SavedSearchDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_EXTRA_SAVE_SEARCH_CRITERIA, searchCriteria);
        bundle.putParcelable(INTENT_EXTRA_LATLNG_CENTER, location);
        dialog.setArguments(bundle);
        return dialog;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchCriteria = getArguments().getParcelable(INTENT_EXTRA_SAVE_SEARCH_CRITERIA);
            mLocation = getArguments().getParcelable(INTENT_EXTRA_LATLNG_CENTER);
        }
    }
    
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_save_search_heading));
        View v = View.inflate(getActivity(), R.layout.layout_dialog_save_search, null);
        setContent(v);
        setNegativeAlertDialogButton(getString(R.string.txt_cancel), (dialog, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        setPositiveAlertDialogButton(getString(R.string.txt_save),
                  (dialog, which) -> mSaveSearchListener.onSaveSearch(mSearchCriteria, mLocation));
        
        return create();
    }
    
    public void setSaveSearchListener(
              SaveSearchListener saveSearchListener) {
        mSaveSearchListener = saveSearchListener;
    }
    
    public interface SaveSearchListener {
        
        void onSaveSearch(SearchCriteria searchCriteria, GeoLocation location);
    }
}
