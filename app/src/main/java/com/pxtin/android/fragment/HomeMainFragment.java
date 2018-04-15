package com.pxtin.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.pxtin.android.MainApplication;
import com.pxtin.android.R;
import com.pxtin.android.activity.MainActivity;
import com.pxtin.android.adapter.CollGridAdapter;
import com.pxtin.android.json.ImageList;
import com.pxtin.android.net.HttpReqUtil;
import com.pxtin.android.ui.BaseFragment;
import com.pxtin.android.ui.BaseMainFragment;
import com.pxtin.android.ui.CollGridPaddingItemDecoration;
import com.pxtin.android.uitl.DisplayUtil;
import com.pxtin.android.widget.CollGrid;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * Created by Zneia on 2017/4/17.
 */

public class HomeMainFragment extends BaseMainFragment implements SwipeRefreshLayout.OnRefreshListener, CollGridAdapter.OnItemActionListener
{
    private static final String TAG = HomeMainFragment.class.getSimpleName();

    private View mView;

    private FrameLayout mSearchBar;
    private CollGrid mCollGrid;

    public static HomeMainFragment newInstance()
    {
        Bundle args = new Bundle();

        HomeMainFragment fragment = new HomeMainFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * 视图初始化
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_home_main, container, false);

        init(); // TODO: 执行初始化方法
        return mView;
    }

    /**
     * 设置Listener、各种Adapter、请求数据等等
     */
    private void init()
    {
        // TODO: 初始化 SearchBar
        mSearchBar = (FrameLayout) mView.findViewById(R.id.home_search_bar);
        LinearLayout searchBtn = (LinearLayout) mSearchBar.findViewById(R.id.home_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance();
                start(fragment);
            }
        });

        // TODO: 初始化 CollGrid
        mCollGrid = (CollGrid) mView.findViewById(R.id.home_coll_grid);
        // 向下滚动隐藏悬浮元素，向上滚动显示悬浮元素
        mCollGrid.setRecyclerScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isAllFloaterShow = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isAllFloaterShow && dy>20) {
                    searchBarCtrl("hide");
                    ((MainActivity) getActivity()).mBnavCtrl("hide");
                    isAllFloaterShow = false;
                }else if(!isAllFloaterShow && dy<0){
                    searchBarCtrl("show");
                    ((MainActivity) getActivity()).mBnavCtrl("show");
                    isAllFloaterShow = true;
                }
            }
        });
        mCollGrid.setRecyclerRefreshListener(this);
        mCollGrid.setRecyclerItemActionListener(this);
    }

    /**
     * 刷新事件
     */
    @Override
    public void onRefresh()
    {
        // Log.i(TAG, "Start Refresh Data");
        // Toast.makeText(MainApplication.getContext(), "onRefresh", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mCollGrid.getRecycler().getSwipeToRefresh().isRefreshing()) {
                    mCollGrid.getRecycler().getSwipeToRefresh().setRefreshing(false);
                }
            }
        }, 3000);
    }

    /**
     * 点击项目事件
     */
    @Override
    public void onItemClickListener(View v, int pos) {
        Snackbar.make(((ViewGroup)_mActivity.findViewById(android.R.id.content)).getChildAt(0), "[Click] Num:" + String.valueOf(pos) + " PicId:" + String.valueOf(pos) + " PicId:" + String.valueOf(mCollGrid.getCollList().get(pos).getId()), Snackbar.LENGTH_SHORT).show();
        /*Intent intent=new Intent(GridImageActivity.this,MainActivity.class);
        // 传递这张图片的 Url 地址给 Activity
        intent.putExtra("imageUrl",urls.get(pos));
        startActivity(intent);*/
    }

    /**
     * 长按项目事件
     */
    @Override
    public boolean onItemLongClickListener(View v, int pos){
        Snackbar.make(((ViewGroup)_mActivity.findViewById(android.R.id.content)).getChildAt(0), "[LongClick] Num:" + String.valueOf(pos) + " PicId:" + String.valueOf(mCollGrid.getCollList().get(pos).getId()), Snackbar.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 搜索条控制
     */
    public void searchBarCtrl(final String type)
    {
        if(!type.equals("show")&&!type.equals("hide")){ return; }

        Animation hideAnim;
        if(type.equals("hide")) {
            hideAnim = AnimationUtils.loadAnimation(MainApplication.getContext(), R.anim.slide_out_top);
        } else {
            hideAnim = AnimationUtils.loadAnimation(MainApplication.getContext(), R.anim.slide_in_top);
        }

        mSearchBar.startAnimation(hideAnim);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if(type.equals("hide")) {
                    mSearchBar.setVisibility(View.GONE);
                } else {
                    mSearchBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}