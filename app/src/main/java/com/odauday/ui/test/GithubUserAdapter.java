package com.odauday.ui.test;

import android.view.ViewGroup;
import com.odauday.databinding.UserItemBinding;
import com.odauday.model.User;
import com.odauday.ui.base.BaseAdapter;

/**
 * Created by infamouSs on 3/5/18.
 */

public class GithubUserAdapter extends BaseAdapter<User, UserItemBinding> {


    @Override
    protected UserItemBinding createBinding(ViewGroup parent) {

        return null;
    }

    @Override
    protected void bind(UserItemBinding binding, User item) {
        binding.setUser(item);
    }

    @Override
    protected boolean areItemsTheSame(User oldItem, User newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    protected boolean areContentsTheSame(User oldItem, User newItem) {
        return oldItem.equals(newItem);
    }
}
