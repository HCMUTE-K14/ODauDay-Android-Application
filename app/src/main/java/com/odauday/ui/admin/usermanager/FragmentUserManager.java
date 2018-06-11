package com.odauday.ui.admin.usermanager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.PopupMenu;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.odauday.R;
import com.odauday.data.remote.model.MessageResponse;
import com.odauday.data.remote.user.model.Status;
import com.odauday.databinding.FragmentUserManagerBinding;
import com.odauday.exception.RetrofitException;
import com.odauday.model.User;
import com.odauday.ui.ClearMemory;
import com.odauday.ui.admin.usermanager.UserManagerAdapter.OnClickActionListener;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.ui.favorite.ServiceUnavailableAdapter;
import com.odauday.utils.SnackBarUtils;
import com.odauday.viewmodel.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 5/4/2018.
 */

public class FragmentUserManager extends BaseMVVMFragment<FragmentUserManagerBinding> implements UserManagerContract,
                                                                                                 ClearMemory{
    
    public static final String TAG=FragmentUserManager.class.getSimpleName();
    @Inject
    UserManagerModel mUserManagerModel;
    
    private int mPage;
    private int mLimit;
    private String mStatus;
    private String mLikeName;
    private RecyclerView mRecyclerView;
    private UserManagerAdapter mUserManagerAdapter;
    private ServiceUnavailableAdapter mServiceUnavailableAdapter;
    private Boolean mStatusGetUser;
    private PopupMenu mPopupMenu;
    private boolean userScrolled = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;
    AlertDialog.Builder mBuilderAlertDialog;
    private User mUserChangeStatus;
    private String mStatusUser;
    RecyclerView.OnScrollListener mOnScrollListener=new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState==recyclerView.SCROLL_STATE_SETTLING){
                userScrolled=true;
            }
        }
    
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        
            if(mStatusGetUser){
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisibleItems = mLayoutManager
                    .findFirstVisibleItemPosition();
                
                if (userScrolled
                    && (visibleItemCount + pastVisibleItems) == totalItemCount) {
    
                    userScrolled = false;
    
                    mPage++;
                    getData();
                
                }
            }
        }
    };
    private UserManagerAdapter.OnClickActionListener mOnClickActionListener=new OnClickActionListener() {
        @Override
        public void Active(User user) {
            Timber.tag(TAG).d("Active: "+user.getEmail());
            actionUser(user,getString(R.string.txt_active_message),Status.ACTIVE);
        }
    
        @Override
        public void Ban(User user) {
            Timber.tag(TAG).d("Ban: "+user.getEmail());
            actionUser(user,getString(R.string.txt_ban_message),Status.DISABLED);
        }
    };
    
    public static FragmentUserManager newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentUserManager fragment = new FragmentUserManager();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_manager;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }
    
    private void init() {
        mPage=1;
        mLimit=7;
        mStatus="all";
        mLikeName=mBinding.get().editTextSearch.getText().toString().trim();
        mStatusGetUser=true;
        mBinding.get().setHandler(this);
        mUserManagerModel.setUserManagerContract(this);
    
        mBuilderAlertDialog = new Builder(getActivity());
        mRecyclerView=mBinding.get().recycleViewUser;
        mServiceUnavailableAdapter=new ServiceUnavailableAdapter();
        mUserManagerAdapter=new UserManagerAdapter();
        mUserManagerAdapter.setOnClickActionListener(mOnClickActionListener);
        mLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mUserManagerAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        getData();
        setUpSearch();
    }
    
    private void getData() {
        mUserManagerModel.getUserByAdmin(String.valueOf(mPage),String.valueOf(mLimit),mStatus,mLikeName);
    }
    private void setUpSearch() {
        RxTextView.afterTextChangeEvents(mBinding.get().editTextSearch).debounce(500, TimeUnit.MILLISECONDS).observeOn(
            AndroidSchedulers.mainThread()).subscribe(success ->{
            updateUI();
        },throwable -> {
            Timber.tag(TAG).d("Error RxTextView");
        });
    }
    
    private void updateUI() {
        mLimit=7;
        mPage=1;
        mStatusGetUser=true;
        mLikeName=mBinding.get().editTextSearch.getText().toString().trim();
        mUserManagerAdapter.clearData();
        
        getData();
    }
    @Override
    protected void processingTaskFromViewModel() {
        mUserManagerModel.response().observe(this, resource -> {
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
    }
    
    @Override
    public void loading(boolean isLoading) {
        Timber.tag(TAG).d("Loading");
    }
    
    @Override
    public void onSuccess(Object object) {
        
        Timber.tag(TAG).d("Success");
        
        List<User> list=(List<User>) object;
        if(list!=null&&list.size()>0){
            if(!(mRecyclerView.getAdapter() instanceof UserManagerAdapter)){
                mRecyclerView.setAdapter(mUserManagerAdapter);
            }
            if(mUserManagerAdapter.getItemCount()>0){
                mUserManagerAdapter.update(list);
            }else {
                mUserManagerAdapter.setData(list);
            }
            mStatusGetUser=true;
        }else {
            mStatusGetUser=false;
        }
        if(mUserManagerAdapter.getItemCount()==0){
            mUserManagerAdapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void onFailure(Exception ex) {
        Timber.tag(TAG).d("Error");
        
        if (ex instanceof RetrofitException) {
            mRecyclerView.setAdapter(mServiceUnavailableAdapter);
            SnackBarUtils.showSnackBar(mBinding.get().userManager, getString(R.string.message_service_unavailable));
        }
    }
    @Override
    public void onChangeStatusSuccess(Object object) {
        if(mUserChangeStatus!=null&&mStatusUser!=null){
            if(mStatus.equals("all")){
                mUserManagerAdapter.changeStatusItem(mUserChangeStatus,mStatusUser);
            }else {
                mUserManagerAdapter.removeItem(mUserChangeStatus);
            }
        }
        MessageResponse messageResponse=(MessageResponse) object;
        if(messageResponse!=null){
            SnackBarUtils.showSnackBar(mBinding.get().userManager, messageResponse.getMessage());
        }
    }
    
    @Override
    public void onChangeStatusFailure(Object object) {
        Exception ex=(Exception) object;
        Timber.tag(TAG).d("Error: "+ex.getMessage());
        if (ex instanceof RetrofitException) {
            mRecyclerView.setAdapter(mServiceUnavailableAdapter);
            SnackBarUtils.showSnackBar(mBinding.get().userManager, getString(R.string.message_service_unavailable));
            return;
        }
        SnackBarUtils.showSnackBar(mBinding.get().userManager, "Error change status");
    }
    
    @Override
    public void onStop() {
        clearMemory();
        super.onStop();
    }
    
    @Override
    public void clearMemory() {
        mBuilderAlertDialog =null;
        mRecyclerView=null;
        mServiceUnavailableAdapter=null;
        mUserManagerAdapter=null;
        mLayoutManager=null;
    }
    
    public void onClickMoreStatus(View view){
        mPopupMenu = new PopupMenu(view.getContext(), view);
        mPopupMenu.getMenuInflater()
            .inflate(R.menu.menu_select_status_user, mPopupMenu.getMenu());
    
        mPopupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.all:
                    mStatus="all";
                    mBinding.get().txtStatus.setText(item.getTitle());
                    clear();
                    getData();
                    break;
                case R.id.pending:
                    mStatus="pending";
                    mBinding.get().txtStatus.setText(item.getTitle());
                    clear();
                    getData();
                    break;
                case R.id.active:
                    clear();
                    mStatus="active";
                    mBinding.get().txtStatus.setText(item.getTitle());
                    getData();
                    break;
                case R.id.disabled:
                    mStatus="disabled";
                    mBinding.get().txtStatus.setText(item.getTitle());
                    clear();
                    getData();
                    break;
                default: break;
            }
        
        
            return false;
        });
        mPopupMenu.show();
    }
    private void clear() {
        mLimit=7;
        mPage=1;
        mStatusGetUser=true;
        mBinding.get().editTextSearch.setText("");
        mLikeName=mBinding.get().editTextSearch.getText().toString().trim();
        mUserManagerAdapter.clearData();
    }
    public void actionUser(User user,String message,String status){
        if (user != null) {
            mBuilderAlertDialog.setMessage(message);
            mBuilderAlertDialog.setCancelable(true);
            mBuilderAlertDialog
                .setPositiveButton(getString(R.string.txt_ok), (dialogInterface, i) -> {
                    mUserChangeStatus=user;
                    mStatusUser=status;
                    mUserManagerModel.changeStatusUser(user.getId(),
                        status);
                });
            mBuilderAlertDialog
                .setNegativeButton(getString(R.string.txt_cancel), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
            AlertDialog alert11 = mBuilderAlertDialog.create();
            alert11.show();
        }
    }
    
   
}
