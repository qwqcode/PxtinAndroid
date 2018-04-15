package com.pxtin.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pxtin.android.R;
import com.pxtin.android.ui.CircleImageView;
import com.pxtin.android.ui.DynamicHeightImageView;

/**
 * Created by Zneia on 2017/4/22.
 */

public class CollGridAdapterViewHolder extends RecyclerView.ViewHolder
{
    public DynamicHeightImageView picContent;

    public LinearLayout picInfo;
    public TextView picDesc;

    public LinearLayout picUserInfo;
    public CircleImageView picUserImg;
    public TextView picUsername;

    public CollGridAdapterViewHolder(View layout) {
        super(layout);
        picContent = (DynamicHeightImageView) layout.findViewById(R.id.picContent);

        picInfo = (LinearLayout) layout.findViewById(R.id.picInfo);
        picDesc = (TextView) layout.findViewById(R.id.picDesc);

        picUserInfo = (LinearLayout) layout.findViewById(R.id.picUserInfo);
        picUserImg = (CircleImageView) layout.findViewById(R.id.picUserImg);
        picUsername = (TextView) layout.findViewById(R.id.picUsername);
    }
}
