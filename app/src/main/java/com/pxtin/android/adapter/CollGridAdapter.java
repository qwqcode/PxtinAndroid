package com.pxtin.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.pxtin.android.MainApplication;
import com.pxtin.android.json.ApiColl;
import com.pxtin.android.json.ImageList;
import com.pxtin.android.ui.CircleImageView;
import com.pxtin.android.GlobalConstant;
import com.pxtin.android.R;
import com.pxtin.android.ui.DynamicHeightImageView;

import java.util.List;

/**
 * Image Grid View Adapter
 * 瀑布流图片列表 适配器
 *
 * Created by Zneia on 2017/3/18.
 */
public class CollGridAdapter extends RecyclerView.Adapter<CollGridAdapterViewHolder>
{

    private static final String TAG = CollGridAdapter.class.getSimpleName();

    private Context mContext;
    private final String WEB_BASE = "http://192.168.1.111";
    private final LayoutInflater mLayoutInflater;
    private List<ApiColl.coll> mData;

    /**
     * 构造方法传入需要的参数
     * @param data 列表数据
     */
    public CollGridAdapter(List<ApiColl.coll> data)
    {
        mContext = MainApplication.getContext();
        // 获得布局填充器
        mLayoutInflater = LayoutInflater.from(mContext);
        // 图片数据
        mData = data;
    }

    /**
     * https://my.oschina.net/ososchina/blog/491564
     */
    @Override
    public void onBindViewHolder(final CollGridAdapterViewHolder holderView, final int position)
    {
        // Image Src Full Url Path
        String imageUrl = WEB_BASE + mData.get(position).getPicInfo().getThumb().getUrl();

        // Handle Image Height Ratio
        double imageWidth = mData.get(position).getPicInfo().getWidth();
        double imageHeight = mData.get(position).getPicInfo().getHeight();
        double imageHeightRatio = imageHeight / imageWidth;

        // 使用图片数据到控件上
        holderView.picContent.setHeightRatio(imageHeightRatio);
        holderView.picDesc.setText(String.valueOf(mData.get(position).getShortDesc().trim()));
        holderView.picUsername.setText(String.valueOf(mData.get(position).getUserInfo().getName().trim()));

        // 异步加载图片 By Glide
        Glide.with(mContext).load(getGlideUrl(imageUrl))
                .crossFade()
                .skipMemoryCache(true) // 仅使用磁盘缓存
                .into(holderView.picContent);
        Glide.with(mContext).load(getGlideUrl(WEB_BASE+mData.get(position).getUserInfo().getAvatar()))
                .crossFade()
                .skipMemoryCache(true)
                .into(holderView.picUserImg);

        if(mOnItemActionListener!=null) {
            holderView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemActionListener.onItemClickListener(v, holderView.getLayoutPosition());
                }
            });
            holderView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemActionListener.onItemLongClickListener(v, holderView.getLayoutPosition());
                }
            });
        }
    }

    private GlideUrl getGlideUrl(String url){
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", GlobalConstant.ReqAuthorization)
                .build());
    }

    @Override
    public CollGridAdapterViewHolder onCreateViewHolder(ViewGroup viewgroup, int i) {

        View v =  mLayoutInflater.inflate(R.layout.coll_grid_item, viewgroup, false);
        CollGridAdapterViewHolder viewHolder = new CollGridAdapterViewHolder(v);
        // viewHolder.setIsRecyclable(true);

        return viewHolder;
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * 添加单个
     */
    public void add(ApiColl.coll content)
    {
        insert(content, mData.size());
    }

    /**
     * 插入单个
     */
    public void insert(ApiColl.coll content, int position)
    {
        mData.add(position, content);
        notifyItemInserted(position);
    }

    /**
     * 删除单个
     */
    public void remove(int position)
    {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 清除所有
     */
    public void clear()
    {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * 所有添加
     */
    public void addAll(List<ApiColl.coll> contents)
    {
        int startIndex = mData.size();
        mData.addAll(startIndex, contents);
        notifyItemRangeInserted(startIndex, contents.size());
    }

    /**
     * 定义点击事件
     */
    private OnItemActionListener mOnItemActionListener;
    public interface OnItemActionListener
    {
        public void onItemClickListener(View v, int pos);
        public boolean onItemLongClickListener(View v, int pos);
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.mOnItemActionListener = onItemActionListener;
    }
}