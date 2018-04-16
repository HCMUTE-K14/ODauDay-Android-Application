package com.odauday.ui.common;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by infamouSs on 3/31/18.
 */

public class AttachFragmentRunnable implements Runnable {
    
    public static final int TYPE_ADD = 0;
    public static final int TYPE_REPLACE = 1;
    
    private int mContainerId;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private String mTagFragment;
    private int mTypeAttach;
    private boolean mAddToBackTrack;
    private int mAnimationIn;
    private int mAnimationOut;
    
    
    public AttachFragmentRunnable(AttachFragmentBuilder builder) {
        this.mContainerId = builder.mContainerId;
        this.mFragment = builder.mFragment;
        this.mFragmentManager = builder.mFragmentManager;
        this.mTagFragment = builder.mTagFragment;
        this.mTypeAttach = builder.mTypeAttach;
        this.mAddToBackTrack = builder.mAddToBackTrack;
        this.mAnimationIn = builder.mAnimationIn;
        this.mAnimationOut = builder.mAnimationOut;
    }
    
    @Override
    public void run() {
        if (mContainerId == 0) {
            throw new IllegalArgumentException("Not found Container View with this id");
        }
        
        if (mFragment == null) {
            throw new IllegalArgumentException("Not found Fragment");
        }
        
        if (mFragmentManager == null) {
            throw new IllegalArgumentException("Not found FragmentManager");
        }
        
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        
        if (mTypeAttach == TYPE_ADD) {
            transaction.add(mContainerId, mFragment, mTagFragment);
            
        } else if (mTypeAttach == TYPE_REPLACE) {
            transaction.replace(mContainerId, mFragment, mTagFragment);
        }
        
        if (mAnimationIn != 0 && mAnimationOut != 0) {
            transaction.setCustomAnimations(mAnimationIn, mAnimationOut);
        }
        
        if (mAddToBackTrack) {
            transaction.addToBackStack(mTagFragment);
            transaction.commit();
        } else {
            transaction.commit();
        }
    }
    
    public static class AttachFragmentBuilder {
        
        private int mContainerId;
        private Fragment mFragment;
        private FragmentManager mFragmentManager;
        private String mTagFragment;
        private int mTypeAttach;
        private boolean mAddToBackTrack;
        private int mAnimationIn;
        private int mAnimationOut;
        
        public AttachFragmentBuilder setContainerId(int id) {
            this.mContainerId = id;
            return this;
        }
        
        public AttachFragmentBuilder setFragment(Fragment fragment) {
            this.mFragment = fragment;
            return this;
        }
        
        public AttachFragmentBuilder setFragmentManager(FragmentManager fragmentManager) {
            this.mFragmentManager = fragmentManager;
            return this;
        }
        
        public AttachFragmentBuilder setTagFragment(String tag) {
            this.mTagFragment = tag;
            return this;
        }
        
        public AttachFragmentBuilder setTypeAttach(int type) {
            this.mTypeAttach = type;
            return this;
        }
        
        public AttachFragmentBuilder setAddToBackTrack(boolean addtoBackTrack) {
            mAddToBackTrack = addtoBackTrack;
            
            return this;
        }
        
        public AttachFragmentBuilder setAnimationIn(int animationIn) {
            mAnimationIn = animationIn;
            return this;
        }
        
        public AttachFragmentBuilder setAnimationOut(int animationOut) {
            mAnimationOut = animationOut;
            return this;
        }
        
        public AttachFragmentRunnable build() {
            return new AttachFragmentRunnable(this);
        }
    }
}
