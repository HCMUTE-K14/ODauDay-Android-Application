package com.odauday.ui.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by infamouSs on 2/28/18.
 */

public class BaseViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final VB mBinding;

    public BaseViewHolder(VB dataBinding) {
        super(dataBinding.getRoot());
        this.mBinding = dataBinding;
    }
}
