package com.pxtin.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pxtin.android.R;
import com.pxtin.android.ui.BaseMainFragment;

/**
 * Created by Zneia on 2017/4/17.
 */

public class UserMainFragment extends BaseMainFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        return view;
    }

    public static UserMainFragment newInstance()
    {
        Bundle args = new Bundle();

        UserMainFragment fragment = new UserMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
}