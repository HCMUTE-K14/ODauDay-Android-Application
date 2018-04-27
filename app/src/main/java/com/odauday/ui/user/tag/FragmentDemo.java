package com.odauday.ui.user.tag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.databinding.FragmentDemoBinding;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseMVVMFragment;
import com.odauday.viewmodel.BaseViewModel;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/1/2018.
 */

public class FragmentDemo extends BaseMVVMFragment<FragmentDemoBinding> implements TagContract {
    
    @Inject
    TagViewModel mTagViewModel;
    OnClickListener mOnClickListener = view -> {
        mTagViewModel.getAllTag();
    };
    private TagAdapter mTagAdapter;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_demo;
    }
    
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mTagViewModel;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        Log.d("Res", "Không có j là không thể");
        mTagViewModel.response().observe(this, resource -> {
            Log.d("Res", "response 1");
            if (resource != null) {
                Log.d("Res", "response sd");
                switch (resource.status) {
                    case ERROR:
                        Log.d("Res", "response 2");
                        loading(false);
                        onFailure((Exception) resource.data);
                        break;
                    case SUCCESS:
                        Log.d("Res", "response 3");
                        loading(false);
                        onSuccess(resource.data);
                        break;
                    case LOADING:
                        Log.d("Res", "response 4");
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });
        mBinding.get().recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mTagAdapter = new TagAdapter();
    }
    
    @Override
    public void loading(boolean isLoading) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    
    @Override
    public void onSuccess(Object object) {
        List<Tag> list = (List<Tag>) object;

        Log.d("Result:", list.get(0).getName());

        Toast.makeText(getActivity(), list.get(0).getName(), Toast.LENGTH_LONG).show();

        mTagAdapter.setData(list);

    }
    
    @Override
    public void onFailure(Exception ex) {
        Log.d("ERROR:", ex.getMessage());
    }

    /*public void onClickTag(Tag tag){
        Timber.tag("Song song ki").d(tag.getName());
        Toast.makeText(getActivity(),tag.getName(),Toast.LENGTH_LONG).show();
    }*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        FragmentDemoBinding fragmentDemoBinding = mBinding.get();
        fragmentDemoBinding.btnChose.setOnClickListener(mOnClickListener);
    }
    
}
