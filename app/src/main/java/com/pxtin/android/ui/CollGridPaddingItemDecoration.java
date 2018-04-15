package com.pxtin.android.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.pxtin.android.uitl.DisplayUtil;

public class CollGridPaddingItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public CollGridPaddingItemDecoration(int space){
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount(); // RecyclerView 中共用多少个项目，从 1 开始
        int lastPosition = itemCount - 1; // 整个 RecyclerView 最后一个 item 的 position
        int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex() + 1; // 列数 1 或 2

        if (spanIndex == 1) {
            outRect.left = mSpace;
            outRect.right = mSpace/2;
        } else { // if you just have 2 span . Or you can use (staggeredGridLayoutManager.getSpanCount()-1) as last span
            outRect.left = mSpace/2;
            outRect.right = mSpace;
        }

        // outRect.bottom = mSpace;
        // Add top margin only for the first item to avoid double space between items
        if (position == 0 || position == 1) {
            outRect.top = DisplayUtil.dipToPx(56) + mSpace;
        }

        outRect.bottom = mSpace;
    }
}