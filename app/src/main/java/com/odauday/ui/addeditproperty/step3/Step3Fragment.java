package com.odauday.ui.addeditproperty.step3;

import static com.odauday.config.Constants.Task.TASK_GET_RECENT_TAG;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.odauday.R;
import com.odauday.databinding.FragmentAddEditStep3Binding;
import com.odauday.model.MyProperty;
import com.odauday.model.Tag;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.addeditproperty.BaseStepFragment;
import com.odauday.ui.base.BaseDialogFragment;
import com.odauday.ui.search.common.view.OnCompletePickedType;
import com.odauday.ui.search.common.view.tagdialog.TagChip;
import com.odauday.ui.search.common.view.tagdialog.TagTypeDialog;
import com.odauday.ui.search.navigation.FilterOption;
import com.odauday.viewmodel.BaseViewModel;
import com.pchmn.materialchips.model.ChipInterface;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/24/18.
 */
public class Step3Fragment extends BaseStepFragment<FragmentAddEditStep3Binding> implements
                                                                                 OnCompletePickedType,
                                                                                 Step3Contract {
    
    public static final String TAG = Step3Fragment.class.getSimpleName();
    
    public static final int STEP = 3;
    
    public static Step3Fragment newInstance(MyProperty myProperty) {
        
        Bundle args = new Bundle();
        
        Step3Fragment fragment = new Step3Fragment();
        args.putParcelable(AddEditPropertyActivity.EXTRA_PROPERTY, myProperty);
        
        fragment.setArguments(args);
        return fragment;
    }
    
    private MyProperty mProperty;
    
    @Inject
    EventBus mBus;
    
    @Inject
    Step3ViewModel mStep3ViewModel;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_edit_step_3;
    }
    
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProperty = getArguments().getParcelable(AddEditPropertyActivity.EXTRA_PROPERTY);
        }
        if (mProperty == null) {
            mProperty = new MyProperty();
        }
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mNextButton = mBinding.get().buttonNav.btnNextStep;
        mBackButton = mBinding.get().buttonNav.btnBackStep;
        super.onViewCreated(view, savedInstanceState);
        
        mBinding.get().txtTags.setOnClickListener(this::openChooseTagsDialog);
    }
    
    @Override
    public void onNextStep(View view) {
        mProperty.setDescription(mBinding.get().txtDescription.getText().toString());
        mProperty.setTags(null);
        
        mBus.post(new OnCompleteStep3Event(mProperty));
        mNavigationStepListener.navigate(getStep(), getNextStep());
    }
    
    public void openChooseTagsDialog(View view) {
        mStep3ViewModel.getRecentTags();
    }
    
    
    @Override
    public int getStep() {
        return STEP;
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return mStep3ViewModel;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected void processingTaskFromViewModel() {
        mStep3ViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        if (resource.task.equals(TASK_GET_RECENT_TAG)) {
                            onErrorGetRecentTag((List<Tag>) resource.data);
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(TASK_GET_RECENT_TAG)) {
                            onSuccessGetRecentTag((List<Tag>) resource.data);
                        }
                        break;
                    case LOADING:
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void onCompletePickedType(int requestCode, Object value) {
        if (requestCode == FilterOption.TAGS.getRequestCode()) {
            List<Tag> selectedTag = TagChip.convertToTag((List<ChipInterface>) value);
            mProperty.setTags(selectedTag);
            String textTags = buildStringTags(selectedTag);
            mBinding.get().txtTags.setText(textTags);
            
            mStep3ViewModel.insertCurrentTags(selectedTag);
        }
    }
    
    private String buildStringTags(List<Tag> list) {
        StringBuilder builder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                builder.append(list.get(i).getName());
                break;
            }
            builder.append(list.get(i).getName())
                      .append(", ");
        }
        
        return builder.toString();
    }
    
    @Override
    public void onSuccessGetRecentTag(List<Tag> recentTags) {
        Timber.d(recentTags.toString());
        
        List<Tag> selectedTag = mProperty.getTags();
        openTagTypeDialog(selectedTag, recentTags);
    }
    
    @Override
    public void onErrorGetRecentTag(List<Tag> ex) {
        List<Tag> selectedTag = mProperty.getTags();
        openTagTypeDialog(selectedTag, new ArrayList<>());
    }
    
    private void openTagTypeDialog(List<Tag> selectedTag, List<Tag> recentTag) {
        if (getFragmentManager() == null) {
            return;
        }
        BaseDialogFragment dialog = TagTypeDialog
                  .newInstance(selectedTag, recentTag);
        
        if (dialog == null) {
            return;
        }
        
        dialog.setTargetFragment(this, FilterOption.TAGS.getRequestCode());
        dialog.show(this.getFragmentManager(), FilterOption.TAGS.getTag());
    }
}
