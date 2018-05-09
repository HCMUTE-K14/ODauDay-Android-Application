package com.odauday.ui.search.common.view.propertydialog;

import static com.odauday.config.Constants.INTENT_EXTRA_SELECTED_PROPERTY_TYPE;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.odauday.R;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.view.OnCompletePickedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/3/2018.
 */
public class PropertyTypeDialog extends BaseDialogFragment {
    
    private RecyclerView mList;
    
    public static PropertyTypeDialog newInstance(List<Integer> selectedProperty) {
        PropertyTypeDialog dialog = new PropertyTypeDialog();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(INTENT_EXTRA_SELECTED_PROPERTY_TYPE,
            (ArrayList<Integer>) selectedProperty);
        dialog.setArguments(bundle);
        return dialog;
    }
    
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mList = (RecyclerView) View.inflate(getActivity(), R.layout.list_dialog_view, null);
        if (getArguments() == null) {
            throw new IllegalArgumentException("Need Bundle INTENT_EXTRA_SELECTED_PROPERTY_TYPE");
        }
        List<Integer> selectedPropertyType = getArguments()
            .getIntegerArrayList(INTENT_EXTRA_SELECTED_PROPERTY_TYPE);
        PropertyTypeDialogAdapter adapter = new PropertyTypeDialogAdapter(getContext(),
            selectedPropertyType);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mList.getContext(),
            LinearLayoutManager.VERTICAL);
        mList.addItemDecoration(dividerItemDecoration);
        this.mList.setAdapter(adapter);
        setTitle(getString(R.string.txt_property_type));
        setContent(this.mList);
        setPositiveButton(getString(R.string.txt_done), false, (view) -> {
            Fragment fragment = PropertyTypeDialog.this.getTargetFragment();
            if (fragment != null && (fragment instanceof OnCompletePickedType)) {
                ((OnCompletePickedType) fragment)
                    .onCompletePickedType(PropertyTypeDialog.this.getTargetRequestCode(),
                        adapter.getSelectedPropertyType());
            }
            dismiss();
        });
        return create();
    }
}
