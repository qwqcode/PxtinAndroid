package com.pxtin.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.pxtin.android.MainApplication;
import com.pxtin.android.R;
import com.pxtin.android.ui.BaseFragment;
import com.pxtin.android.ui.BaseMainFragment;

/**
 * 主页
 * Created by Zneia on 2017/4/17.
 */

public class SearchFragment extends BaseMainFragment
{
    private static final String TAG = SearchFragment.class.getSimpleName();

    private View mView;
    private ImageView mSearchBackBtn;
    private EditText mSearchEditText;
    private ImageView mSearchInputBtn;

    public static SearchFragment newInstance()
    {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
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
        mView = inflater.inflate(R.layout.fragment_home_search, container, false);

        mSearchBackBtn = (ImageView) mView.findViewById(R.id.search_back_btn);
        mSearchEditText = (EditText) mView.findViewById(R.id.search_edit_text);
        mSearchInputBtn = (ImageView) mView.findViewById(R.id.search_input_btn);

        return mView;
    }

    /**
     * 懒加载
     */
    public void onLazyInitView(@Nullable Bundle savedInstanceState)
    {
        // 返回按钮监听
        mSearchBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        // 当软键盘按下 Enter 键的监听
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return true;
            }
        });

        // 搜索提交按钮监听
        mSearchInputBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search();
            }
        });

        // 显示软键盘
        showSoftInput(mSearchEditText);
    }

    /**
     * 执行搜索
     */
    private void search()
    {
        // 隐藏软键盘
        hideSoftInput();
        // 失去焦距
        mSearchEditText.clearFocus();
    }
}