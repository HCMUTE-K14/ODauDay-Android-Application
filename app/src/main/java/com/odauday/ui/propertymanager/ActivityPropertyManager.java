package com.odauday.ui.propertymanager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import com.odauday.R;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.databinding.ActivityPropertyManagerBinding;
import com.odauday.exception.RetrofitException;
import com.odauday.model.Property;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.favorite.ServiceUnavailableAdapter;
import com.odauday.ui.propertymanager.PropertyAdapter.OnClickMenuListener;
import com.odauday.utils.SnackBarUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/18/2018.
 */

public class ActivityPropertyManager extends
                                     BaseMVVMActivity<ActivityPropertyManagerBinding> implements
                                                                                      PropertyManagerContract {
    
    public static final String TAG = ActivityPropertyManager.class.getSimpleName();
    
    @Inject
    PropertyManagerViewModel mPropertyManagerViewModel;
    @Inject
    PreferencesHelper mPreferencesHelper;
    AlertDialog.Builder mBuilderAlertDialog;
    private PropertyAdapter mPropertyAdapter;
    private ServiceUnavailableAdapter mServiceUnavailableAdapter;
    private EmptyPropertyAdapter mEmptyPropertyAdapter;
    private ProgressDialog mProgressDialog;
    private Property mPropertyDelete;
    private List<Property> mProperties;
    private PopupMenu mPopupMenu;
    private PropertyAdapter.OnClickMenuListener mOnClickMenuListener = new OnClickMenuListener() {
        @Override
        public void editProperty(Property property) {
            Timber.tag(TAG).d("edit:" + property.getAddress());
        }
        
        @Override
        public void deleteProperty(Property property) {
            Timber.tag(TAG).d("delete:" + property.getAddress());
            
            if (property != null) {
                
                mBuilderAlertDialog.setMessage(getString(R.string.message_delele_property));
                mBuilderAlertDialog.setCancelable(true);
                mBuilderAlertDialog.setIcon(getResources().getDrawable(R.drawable.ic_warning));
                mBuilderAlertDialog
                    .setPositiveButton(getString(R.string.txt_ok), (dialogInterface, i) -> {
                        mPropertyDelete = property;
                        mPropertyManagerViewModel.deleteProperty(property.getId());
                    });
                mBuilderAlertDialog
                    .setNegativeButton(getString(R.string.txt_cancel), (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
                AlertDialog alert11 = mBuilderAlertDialog.create();
                alert11.show();
            }
        }
        
        @Override
        public void markTheEndProperty(Property property) {
            Timber.tag(TAG).d("mark:" + property.getAddress());
        }
        
        @Override
        public void addExtendsTime(Property property) {
            Timber.tag(TAG).d("add:" + property.getAddress());
        }
        
        @Override
        public void reuseProperty(Property property) {
            Timber.tag(TAG).d("reuse:" + property.getAddress());
        }
    };
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_property_manager;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mPropertyManagerViewModel;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    private void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getString(R.string.txt_progress));
        mBuilderAlertDialog = new Builder(this);
        mBinding.txtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            
            }
            
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Timber.tag(TAG).d(charSequence.toString());
                if (mPropertyAdapter != null) {
                    mPropertyAdapter.filter(charSequence.toString());
                }
            }
            
            @Override
            public void afterTextChanged(Editable editable) {
            
            }
        });
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }
    
    private void getData() {
        mProgressDialog.show();
        mPropertyManagerViewModel.setPropertyManagerContract(this);
        mPropertyManagerViewModel.getPropertyOfUser(mPreferencesHelper.get(PrefKey.USER_ID, ""));
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mPropertyManagerViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        onFailure((Exception) resource.data);
                        loading(false);
                        break;
                    case SUCCESS:
                        onSuccess(resource.data);
                        loading(false);
                        break;
                    case LOADING:
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });
        mPropertyAdapter = new PropertyAdapter();
        mPropertyAdapter.setOnClickMenuListener(mOnClickMenuListener);
        mBinding.recycleViewProperties.setLayoutManager(new GridLayoutManager(this, 1));
        mBinding.recycleViewProperties.setNestedScrollingEnabled(false);
        mServiceUnavailableAdapter = new ServiceUnavailableAdapter();
        mEmptyPropertyAdapter = new EmptyPropertyAdapter();
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        Timber.tag(TAG).d("loading");
        if (isLoading) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }
    
    @Override
    public void onSuccess(Object object) {
        Timber.tag(TAG).d("success");
        List<Property> list = (List<Property>) object;
        if (list != null && list.size() > 0) {
            mProperties = list;
            if (mBinding.recycleViewProperties.getAdapter() instanceof PropertyAdapter) {
            
            } else {
                mBinding.recycleViewProperties.setAdapter(mPropertyAdapter);
            }
            mPropertyAdapter.setData(list);
            
        } else {
            mBinding.recycleViewProperties.setAdapter(mEmptyPropertyAdapter);
        }
    }
    
    @Override
    public void onFailure(Exception ex) {
        Timber.tag(TAG).d("error: " + ex.getMessage());
        
        String message;
        if (ex instanceof RetrofitException) {
            message = getString(R.string.message_service_unavailable);
            mBinding.recycleViewProperties.setAdapter(mServiceUnavailableAdapter);
        } else {
            message = getString(R.string.empty_property);
            mBinding.recycleViewProperties.setAdapter(mEmptyPropertyAdapter);
        }
        SnackBarUtils.showSnackBar(mBinding.propertyManager, message);
    }
    
    public void onClickBack(View view) {
        this.finish();
    }
    
    @Override
    public void onSuccessDeleteProperty(Object object) {
        Timber.tag(TAG).d("On Success delete");
        mBinding.txtSearchBar.setText("");
        if (mProperties != null && mProperties.size() > 0) {
            if (mPropertyDelete != null) {
                mProperties.remove(mPropertyDelete);
                if (mBinding.recycleViewProperties.getAdapter() instanceof PropertyAdapter) {
                
                } else {
                    mBinding.recycleViewProperties.setAdapter(mPropertyAdapter);
                }
                mPropertyAdapter.setData(mProperties);
            }
        }
        MessageResponse messageResponse = (MessageResponse) object;
        SnackBarUtils.showSnackBar(mBinding.propertyManager, messageResponse.getMessage());
    }
    
    @Override
    public void onErrorDeleteProperty(Object object) {
        Timber.tag(TAG).d("On Error delete");
        Exception ex = (Exception) object;
        String message;
        if (ex instanceof RetrofitException) {
            message = getString(R.string.message_service_unavailable);
            mBinding.recycleViewProperties.setAdapter(mServiceUnavailableAdapter);
        } else {
            message = getString(R.string.cannot_delete_property);
        }
        SnackBarUtils.showSnackBar(mBinding.propertyManager, message);
    }
    
    public void onClickMore(View view) {
        mPopupMenu = new PopupMenu(this, view);
        mPopupMenu.getMenuInflater()
            .inflate(R.menu.menu_sort_property_manager, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(item -> {
            handlerClickItem(item);
            return false;
        });
        mPopupMenu.show();
    }
    
    private void handlerClickItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ascending:
                Timber.tag(TAG).d("Ascending Click");
                ascendingProperty();
                break;
            case R.id.descending:
                Timber.tag(TAG).d("Descending Click");
                descendingProperty();
                break;
            default:
                break;
        }
    }
    
    private void ascendingProperty() {
        List<Property> list = mProperties;
        Comparator<Property> comparator = (property1, property2) -> {
            return property1.getAddress().compareTo(property2.getAddress());
        };
        if (list != null && list.size() > 0) {
            
            Collections.sort(list, comparator);
            
            if (mBinding.recycleViewProperties.getAdapter() instanceof PropertyAdapter) {
            
            } else {
                mBinding.recycleViewProperties.setAdapter(mPropertyAdapter);
            }
            mPropertyAdapter.setData(list);
        } else {
            mBinding.recycleViewProperties.setAdapter(mEmptyPropertyAdapter);
        }
    }
    
    private void descendingProperty() {
        List<Property> list = mProperties;
        Comparator<Property> comparator = (property1, property2) -> {
            return property2.getAddress().compareTo(property1.getAddress());
        };
        if (list != null && list.size() > 0) {
            
            Collections.sort(list, comparator);
            
            if (mBinding.recycleViewProperties.getAdapter() instanceof PropertyAdapter) {
            
            } else {
                mBinding.recycleViewProperties.setAdapter(mPropertyAdapter);
            }
            mPropertyAdapter.setData(list);
        } else {
            mBinding.recycleViewProperties.setAdapter(mEmptyPropertyAdapter);
        }
    }
}
