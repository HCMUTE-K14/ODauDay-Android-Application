package com.odauday.ui.base;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 2/28/18.
 */

public abstract class BaseAdapter<T, VB extends ViewDataBinding> extends
                                                                 RecyclerView.Adapter<BaseViewHolder<VB>> {
    
    protected List<T> data;
    
    private int dataVersion = 0;
    
    
    public void setData(List<T> _data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        
        data.addAll(_data);
        notifyDataSetChanged();
    }
    
    @SuppressLint("StaticFieldLeak")
    public void update(List<T> newData) {
        if (data == null) {
            data = new ArrayList<>();
        }
        dataVersion++;
        if (data == null) {
            if (newData == null) {
                return;
            }
            data = newData;
            notifyDataSetChanged();
        } else if (newData == null) {
            int oldSize = data.size();
            data = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            final int startVersion = dataVersion;
            final List<T> oldData = data;
            
            new AsyncTask<Void, Void, DiffResult>() {
                @Override
                protected DiffUtil.DiffResult doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldData.size();
                        }
                        
                        @Override
                        public int getNewListSize() {
                            return newData.size();
                        }
                        
                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            T oldItem = oldData.get(oldItemPosition);
                            T newItem = newData.get(newItemPosition);
                            return BaseAdapter.this.areItemsTheSame(oldItem, newItem);
                        }
                        
                        @Override
                        public boolean areContentsTheSame(int oldItemPosition,
                            int newItemPosition) {
                            T oldItem = oldData.get(oldItemPosition);
                            T newItem = newData.get(newItemPosition);
                            return BaseAdapter.this.areContentsTheSame(oldItem, newItem);
                        }
                    });
                }
                
                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if (startVersion != dataVersion) {
                        return;
                    }
                    data.addAll(newData);
                    diffResult.dispatchUpdatesTo(BaseAdapter.this);
                    
                }
            }.execute();
        }
    }
    
    @Override
    public BaseViewHolder<VB> onCreateViewHolder(ViewGroup parent, int viewType) {
        VB binding = createBinding(parent);
        return new BaseViewHolder<>(binding);
    }
    
    @Override
    public void onBindViewHolder(BaseViewHolder<VB> holder, int position) {
        bind(holder.mBinding, data.get(position));
        holder.mBinding.executePendingBindings();
    }
    
    protected abstract VB createBinding(ViewGroup parent);
    
    protected abstract void bind(VB binding, T item);
    
    protected abstract boolean areItemsTheSame(T oldItem, T newItem);
    
    protected abstract boolean areContentsTheSame(T oldItem, T newItem);
    
    private Single<DiffResult> createSingleDiffResult(final List<T> oldData,
        final List<T> newData) {
        return Single.fromCallable(
            () -> DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return oldData.size();
                }
                
                @Override
                public int getNewListSize() {
                    return newData.size();
                }
                
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    T oldItem = oldData.get(oldItemPosition);
                    T newItem = newData.get(newItemPosition);
                    return BaseAdapter.this.areItemsTheSame(oldItem, newItem);
                }
                
                @Override
                public boolean areContentsTheSame(int oldItemPosition,
                    int newItemPosition) {
                    T oldItem = oldData.get(oldItemPosition);
                    T newItem = newData.get(newItemPosition);
                    return BaseAdapter.this.areContentsTheSame(oldItem, newItem);
                }
            })
        );
    }
    
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
