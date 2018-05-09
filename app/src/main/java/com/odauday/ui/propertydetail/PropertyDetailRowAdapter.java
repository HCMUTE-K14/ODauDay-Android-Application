package com.odauday.ui.propertydetail;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.propertydetail.common.CardViewHasOption;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.propertydetail.rowdetails.RowControllerListener;
import java.util.HashMap;
import java.util.List;

/**
 * Created by infamouSs on 5/8/18.
 */
public class PropertyDetailRowAdapter extends Adapter<BaseRowViewHolder> {
    
    
    private List<BaseRowDetail<PropertyDetail, ? extends BaseRowViewHolder>> mRows;
    private HashMap<Integer, BaseRowDetail<PropertyDetail, ? extends BaseRowViewHolder>> mTypeToRow;
    
    private RowControllerListener mRowControllerListener;
    
    public void setRowControllerListener(
        RowControllerListener rowControllerListener) {
        mRowControllerListener = rowControllerListener;
    }
    
    public PropertyDetailRowAdapter(
        List<BaseRowDetail<PropertyDetail, ? extends BaseRowViewHolder>> rows) {
        this.mRows = rows;
        initTypeToRowCache();
    }
    
    @Override
    public BaseRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTypeToRow.get(viewType).createViewHolder(parent);
    }
    
    @Override
    public void onBindViewHolder(BaseRowViewHolder holder, int position) {
        if (holder instanceof CardViewHasOption) {
            ((CardViewHasOption) holder).setRowControllerListener(mRowControllerListener);
        }
        holder.bind(mRows.get(position));
    }
    
    @Override
    public int getItemCount() {
        return mRows.size();
    }
    
    @Override
    public int getItemViewType(int position) {
        return mRows.get(position).getType();
    }
    
    private void initTypeToRowCache() {
        this.mTypeToRow = new HashMap<>();
        for (BaseRowDetail<PropertyDetail, ? extends BaseRowViewHolder> row : this.mRows) {
            this.mTypeToRow.put(row.getType(), row);
        }
    }
}
