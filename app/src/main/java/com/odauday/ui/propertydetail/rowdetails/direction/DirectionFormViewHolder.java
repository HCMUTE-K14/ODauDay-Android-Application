package com.odauday.ui.propertydetail.rowdetails.direction;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.odauday.R;
import com.odauday.data.remote.autocompleteplace.model.AutoCompletePlace;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.common.DirectionMode;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceActivity;
import com.odauday.ui.search.common.event.OnSelectedPlaceEvent;
import com.odauday.ui.view.SelectableViewGroup;
import com.odauday.utils.TextUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by infamouSs on 5/9/18.
 */
public class DirectionFormViewHolder {
    
    private TextView mDirectionOrigin;
    private TextView mDirectionDestination;
    private SelectableViewGroup mSelectableViewGroup;
    private View mParent;
    
    private AutoCompletePlace mPlace;
    
    public DirectionFormViewHolder(View view) {
        mParent = view;
        mDirectionOrigin = view.findViewById(R.id.direction_origin);
        mDirectionDestination = view.findViewById(R.id.direction_destination);
        mSelectableViewGroup = view.findViewById(R.id.controller_container);
        
        mDirectionDestination.setOnClickListener(direct -> openSearchActivity(direct.getContext()));
        EventBus.getDefault().register(this);
    }
    
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedPlace(OnSelectedPlaceEvent event) {
        mPlace = event.getData();
        mDirectionDestination.setText(mPlace.getName());
    }
    
    public void update(PropertyDetail data) {
        if (data != null) {
            mDirectionOrigin.setText(TextUtils.formatAddress(data.getAddress()));
        }
    }
    
    public boolean isValidForm() {
        String str = mDirectionDestination.getText().toString();
        
        if (TextUtils.isEmpty(str)) {
            mDirectionDestination
                .setError(mParent.getContext().getString(R.string.message_destination_is_reuiqred));
            return false;
        }
        return true;
    }
    
    public SelectableViewGroup getSelectableViewGroup() {
        return mSelectableViewGroup;
    }
    
    public AutoCompletePlace getPlace() {
        return mPlace;
    }
    
    public DirectionMode getMode() {
        int indexMode = mSelectableViewGroup.getSelectedIndex();
        return DirectionMode.getModeByIndex(indexMode);
    }
    
    public void openSearchActivity(Context context) {
        Intent intent = new Intent(context, AutoCompletePlaceActivity.class);
        intent.putExtra(AutoCompletePlaceActivity.EXTRA_NEED_MOVE_MAP_IN_MAP_FRAGMENT, false);
        context.startActivity(intent);
    }
    
    public void unbind() {
        EventBus.getDefault().unregister(this);
        
        mDirectionOrigin = null;
        mParent = null;
    }
    
    public View getParent() {
        return mParent;
    }
}
