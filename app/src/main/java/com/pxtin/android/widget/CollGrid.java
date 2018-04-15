package com.pxtin.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.pxtin.android.MainApplication;
import com.pxtin.android.R;
import com.pxtin.android.activity.MainActivity;
import com.pxtin.android.adapter.CollGridAdapter;
import com.pxtin.android.json.ApiColl;
import com.pxtin.android.json.ApiSearch;
import com.pxtin.android.json.ImageList;
import com.pxtin.android.net.HttpReqUtil;
import com.pxtin.android.ui.CollGridPaddingItemDecoration;
import com.pxtin.android.uitl.DisplayUtil;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;
import okhttp3.Request;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 采集瀑布流 视图
 * Created by Zneia on 2017/4/23.
 */

public class CollGrid extends FrameLayout
{
    private SuperRecyclerView mRecycler;
    private CollGridAdapter.OnItemActionListener mRecyclerItemActionListener;
    private CollGridAdapter mAdapter;

    private final List<ApiColl.coll> mCollList = new ArrayList<>();

    public CollGrid(Context context)
    {
        super(context);
    }

    public CollGrid(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        // 加载视图布局
        LayoutInflater.from(context).inflate(R.layout.coll_grid, this, true);

        // TODO: 初始化 Recycler
        mRecycler = (SuperRecyclerView) findViewById(R.id.coll_grid);
        // 设置布局管理器
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // 设置排水沟
        mRecycler.addItemDecoration(new CollGridPaddingItemDecoration(30));
        // 设置动画效果
        SparseItemRemoveAnimator mSparseAnimator = new SparseItemRemoveAnimator();
        mRecycler.getRecyclerView().setItemAnimator(mSparseAnimator);

        // TODO: 加载内容
        loadCollList("pinterest");

        // 设置加载更多事件
        mRecycler.setupMoreListener(new OnMoreListener(){
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos)
            {
                loadCollList("pinterest");
            }
        }, 1);
    }

    private int mPageNum = 1;
    private boolean mCanLoadPage = true;

    /**
     * 装载图片
     */
    public void loadCollList(final String keyword)
    {
        if(!mCanLoadPage)
            return;

        mCanLoadPage = false;

        new HttpReqUtil(keyword, mPageNum, new StringCallback()
        {
            // 请求之前
            @Override
            public void onBefore(Request request, int id) {}

            // 请求失败
            @Override
            public void onError(Call call, Exception e, int id) {
                // Log.e(TAG, "Req Error: "+e.getMessage());
                e.printStackTrace(); // 打印错误信息
            }

            // 请求成功
            @Override
            public void onResponse(String json, int id) {
                Log.e("CollGrid", "Req Success, Response: "+json);

                // 解析 Json
                JSONObject decodeJson;
                String data;
                try {
                    decodeJson = new JSONObject(json);
                    data = decodeJson.getJSONObject("data").toString();
                } catch (Exception e) {
                    return;
                }

                Log.i("REQ", data);

                // 解析 Json
                ApiColl respData = new Gson().fromJson(data, ApiColl.class);

                // TODO: 初始化 Adapter [只会执行一次]
                initAdapter();

                if(respData==null) return;

                mAdapter.addAll(respData.getColl());
                mPageNum ++;
                if(!respData.getNextPageExists()){
                    // 删除监听
                    mRecycler.removeMoreListener();
                }
            }

            // 请求之后
            @Override
            public void onAfter(int id) {
                mCanLoadPage = true;
            }
        });
    }

    /**
     * 初始化 Adapter
     */
    private void initAdapter()
    {
        if(mAdapter!=null) return;

        mAdapter = new CollGridAdapter(mCollList);
        // 点击单个项目事件
        mAdapter.setOnItemActionListener(mRecyclerItemActionListener);
        mRecycler.setAdapter(mAdapter);
    }

    public List<ApiColl.coll> getCollList()
    {
        return this.mCollList;
    }
    
    public SuperRecyclerView getRecycler()
    {
        return this.mRecycler;
    }

    /**
     * 设置 Recycler 滚动 监听
     */
    public void setRecyclerScrollListener(RecyclerView.OnScrollListener listener)
    {
        mRecycler.setOnScrollListener(listener);
    }

    /**
     * 设置 Recycler 下拉刷新 监听
     */
    public void setRecyclerRefreshListener(SwipeRefreshLayout.OnRefreshListener listener)
    {
        // 设置颜色
        mRecycler.setRefreshingColorResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        // 设置位置
        mRecycler.getSwipeToRefresh().setProgressViewOffset(false, 0, DisplayUtil.dipToPx(75));

        mRecycler.setRefreshListener(listener);
    }

    /**
     * 设置 Recycler 项目点击 监听
     */
    public void setRecyclerItemActionListener(CollGridAdapter.OnItemActionListener listener)
    {
        this.mRecyclerItemActionListener = listener;
    }
}