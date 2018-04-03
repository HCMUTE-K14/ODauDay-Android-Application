package com.odauday.ui.user.tag;

import static com.odauday.viewmodel.model.Status.ERROR;
import static com.odauday.viewmodel.model.Status.LOADING;
import static com.odauday.viewmodel.model.Status.SUCCESS;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.databinding.FragmentDemoBinding;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseMVVMFragment;
import dagger.android.AndroidInjection;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/1/2018.
 */

public class FragmentDemo extends BaseMVVMFragment<TagViewModel,FragmentDemoBinding> implements TagContract{
    
    private TagAdapter mTagAdapter;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_demo;
    }
    @Override
    protected TagViewModel buildViewModel() {
        return mViewModel;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        processingTaskFromViewModel();
    }
    
    private void processingTaskFromViewModel() {
        (((ActivityDemo)getActivity()).mTagViewModel).response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        loading(false);
                        onFailure((Exception) resource.data);
                        break;
                    case SUCCESS:
                        loading(false);
                        onSuccess(resource.data);
                        break;
                    case LOADING:
                        loading(true);
                        break;
                    default:
                        break;
                }
            }
        });
        mBinding.get().recycleView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mTagAdapter=new TagAdapter();
        mBinding.get().recycleView.setAdapter(mTagAdapter);
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
        List<Tag> list=(List<Tag>)object;
        Log.d("Result:",list.get(0).getName());
       
        Toast.makeText(getActivity(),list.get(0).getName(),Toast.LENGTH_LONG).show();
        
        mTagAdapter.setData(list);
        
    }
    
    @Override
    public void onFailure(Exception ex) {
        Log.d("ERROR:",ex.getMessage());
    }
    
    OnClickListener mOnClickListener=view -> {
        ((ActivityDemo)getActivity()).mTagViewModel.getAllTag();
    };
   /* public void onClickTag(Tag tag){
        Timber.tag("Song song ki").d(tag.getName());
        Toast.makeText(getActivity(),tag.getName(),Toast.LENGTH_LONG).show();
    }*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        FragmentDemoBinding fragmentDemoBinding=mBinding.get();
        fragmentDemoBinding.btnChose.setOnClickListener(mOnClickListener);
    }
    
}
