package com.odauday.ui.propertydetail.common;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import com.odauday.R;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.propertydetail.rowdetails.RowControllerListener;

/**
 * Created by infamouSs on 5/9/18.
 */
public abstract class CardViewHasOption<ROW extends BaseRowDetail> extends BaseRowViewHolder<ROW> {
    
    protected abstract int getMenuResId();
    
    private RowControllerListener mRowControllerListener;
    
    private BaseRowDetail mRow;
    
    public CardViewHasOption(View view, boolean isHideable) {
        super(view);
        
        View menuView = itemView.findViewById(R.id.menu);
        int menuResId = getMenuResId();
        if (menuResId != 0 || isHideable) {
            final PopupMenu overflowMenu = new PopupMenu(view.getContext(), menuView);
            if (menuResId != 0) {
                overflowMenu.getMenuInflater().inflate(menuResId, overflowMenu.getMenu());
            }
            if (isHideable) {
                overflowMenu.getMenu().add(0, R.id.hide, 0, R.string.txt_hide_card);
            }
            overflowMenu.setOnMenuItemClickListener(item -> onMenuItemSelected(item.getItemId()));
            menuView.setOnClickListener(v -> overflowMenu.show());
            return;
        }
        menuView.setVisibility(View.GONE);
    }
    
    public void setRow(BaseRowDetail row) {
        mRow = row;
    }
    
    public BaseRowDetail getRow() {
        return mRow;
    }
    
    public void setRowControllerListener(
        RowControllerListener rowControllerListener) {
        mRowControllerListener = rowControllerListener;
    }
    
    private boolean onMenuItemSelected(int idMenu) {
        if (R.id.hide != idMenu) {
            return false;
        }
        
        if (mRowControllerListener == null) {
            return false;
        }
        mRowControllerListener.removeRow(mRow);
        Snackbar snackbar = Snackbar
            .make(this.itemView, R.string.txt_card_is_hidden, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.txt_undo, v -> mRowControllerListener.addRow(mRow));
        snackbar.setActionTextColor(ContextCompat.getColor(itemView.getContext(), R.color.green));
        snackbar.show();
        return true;
    }
}
