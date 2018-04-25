package com.odauday.ui.addeditproperty;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import com.odauday.R;
import com.odauday.databinding.ActivityAddEditPropertyBinding;
import com.odauday.model.MyProperty;
import com.odauday.ui.addeditproperty.BaseStepFragment.NavigationStepListener;
import com.odauday.ui.addeditproperty.step1.OnCompleteStep1Event;
import com.odauday.ui.addeditproperty.step1.Step1Fragment;
import com.odauday.ui.addeditproperty.step2.OnCompleteStep2Event;
import com.odauday.ui.addeditproperty.step2.Step2Fragment;
import com.odauday.ui.addeditproperty.step3.Step3Fragment;
import com.odauday.ui.addeditproperty.step4.Step4Fragment;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.viewmodel.BaseViewModel;
import java.util.Stack;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/24/18.
 */
public class AddEditPropertyActivity extends
                                     BaseMVVMActivity<ActivityAddEditPropertyBinding> implements
                                                                                      NavigationStepListener {
    
    public static final int NUM_OF_STEP = 4;
    
    public static final String EXTRA_PROPERTY = "extra_property";
    
    @Inject
    EventBus mBus;
    
    private Step1Fragment mStep1Fragment;
    
    private Step2Fragment mStep2Fragment;
    
    private Step3Fragment mStep3Fragment;
    
    private Step4Fragment mStep4Fragment;
    
    private Stack<Integer> mStackFragment = new Stack<>();
    
    private MyProperty mCurrentProperty;
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
    
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_edit_property;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBus == null) {
            Timber.d("BUS is NULL");
        }
        EventBus.getDefault().register(this);
        
        init();
    }
    
    @Subscribe
    public void nothingToDoHere() {
    
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    
    @Override
    public void onBackPressed() {
        if (mStackFragment.isEmpty()) {
            super.onBackPressed();
            return;
        }
        
        int currentStep = mStackFragment.peek();
        if (currentStep == Step1Fragment.STEP) {
            super.onBackPressed();
            return;
        }
        BaseStepFragment currentFragment = findStepFragmentByStep(currentStep);
        mStackFragment.pop();
        
        int previousStep = mStackFragment.peek();
        BaseStepFragment previousFragment = findStepFragmentByStep(previousStep);
        mStackFragment.pop();
        
        hideFragment(currentFragment);
        showFragment(previousFragment);
        setTitle(previousStep);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Subscribe()
    public void onCompleteStep1(OnCompleteStep1Event event) {
        MyProperty property = event.getData();
        
        mCurrentProperty.setType_id(property.getType_id());
        mCurrentProperty.setPrice(property.getPrice());
        mCurrentProperty.setLocation(property.getLocation());
        mCurrentProperty.setAddress(property.getAddress());
        mCurrentProperty.setEmails(property.getEmails());
        mCurrentProperty.setPhones(property.getPhones());
        mCurrentProperty.setCategories(property.getCategories());
    }
    
    @Subscribe()
    public void onCompleteStep2(OnCompleteStep2Event event) {
        MyProperty property = event.getData();
        
        mCurrentProperty.setSize(property.getSize());
        mCurrentProperty.setNumOfBedRoom(property.getNumOfBedRoom());
        mCurrentProperty.setNumOfBathRoom(property.getNumOfBathRoom());
        mCurrentProperty.setNumOfParking(property.getNumOfParking());
    }
    
    
    @Override
    public void navigate(int from, int to) {
        if (from > 0 && to > 0) {
            
            BaseStepFragment currentFragment = findStepFragmentByStep(from);
            hideFragment(currentFragment);
            
            BaseStepFragment nextFragment = findStepFragmentByStep(to);
            showFragment(nextFragment);
            
            setTitle(to);
            mStackFragment.push(from);
            mStackFragment.push(to);
        }
    }
    
    private BaseStepFragment findStepFragmentByStep(int step) {
        switch (step) {
            case Step1Fragment.STEP:
                return mStep1Fragment;
            case Step2Fragment.STEP:
                return mStep2Fragment;
            case Step3Fragment.STEP:
                return mStep3Fragment;
            case Step4Fragment.STEP:
                return mStep4Fragment;
            default:
                return null;
        }
    }
    
    private void init() {
        getProperty();
        initStepFragment();
        initToolbar();
    }
    
    private void initStepFragment() {
        new Handler().postDelayed(() -> {
            mStep1Fragment = Step1Fragment.newInstance(mCurrentProperty);
            mStep2Fragment = Step2Fragment.newInstance(mCurrentProperty);
            mStep3Fragment = Step3Fragment.newInstance(mCurrentProperty);
            mStep4Fragment = Step4Fragment.newInstance(mCurrentProperty);
            
            mStep1Fragment.setNavigationStepListener(this);
            mStep2Fragment.setNavigationStepListener(this);
            mStep3Fragment.setNavigationStepListener(this);
            mStep4Fragment.setNavigationStepListener(this);
            
            addFragment(mStep1Fragment, Step1Fragment.TAG);
            addFragment(mStep2Fragment, Step2Fragment.TAG);
            addFragment(mStep3Fragment, Step3Fragment.TAG);
            addFragment(mStep4Fragment, Step4Fragment.TAG);
            
            hideFragment(mStep2Fragment);
            hideFragment(mStep3Fragment);
            hideFragment(mStep4Fragment);
            
            setTitle(mStep1Fragment.getStep());
        }, 200);
    }
    
    private void getProperty() {
        if (getIntent() != null) {
            mCurrentProperty = getIntent().getParcelableExtra(EXTRA_PROPERTY);
        } else {
            mCurrentProperty = new MyProperty();
        }
    }
    
    
    private void initToolbar() {
        setSupportActionBar(mBinding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    
    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                  .beginTransaction()
                  .add(R.id.step_property_container, fragment, tag)
                  .commit();
        
    }
    
    private void showFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                      .beginTransaction()
                      .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                      .show(fragment)
                      .commit();
        }
    }
    
    private void hideFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                      .beginTransaction()
                      .hide(fragment)
                      .commit();
        }
    }
    
    public void setTitle(int step) {
        String title = getString(R.string.txt_add_property_step, String.valueOf(step),
                  String.valueOf(NUM_OF_STEP));
        
        mBinding.includeToolbar.title
                  .setText(title);
    }
}
