package com.pxtin.android.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.pxtin.android.R;
import com.pxtin.android.fragment.*;
import com.pxtin.android.ui.BaseMainFragment;
import com.pxtin.android.ui.BottomBar;
import com.pxtin.android.ui.BottomBarTab;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener
{

    private Context mContext;
    private BottomBar mBnav;

    public static final int F_HOME = 0;
    public static final int F_EXPLORE = 1;
    public static final int F_USER = 2;
    private SupportFragment[] mFragments = new SupportFragment[3];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 修改状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        setContentView(R.layout.activity_main);

        mContext = this;
        mBnav = (BottomBar) findViewById(R.id.bnav);

        // Fragment
        if (savedInstanceState == null) {
            mFragments[F_HOME] = HomeFragment.newInstance();
            mFragments[F_EXPLORE] = ExploreFragment.newInstance();
            mFragments[F_USER] = UserFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, F_HOME,
                    mFragments[F_HOME],
                    mFragments[F_EXPLORE],
                    mFragments[F_USER]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[F_HOME] = findFragment(HomeFragment.class);
            mFragments[F_EXPLORE] = findFragment(ExploreFragment.class);
            mFragments[F_USER] = findFragment(UserFragment.class);
        }

        init();
    }

    private void init()
    {
        mBnav.addItem(new BottomBarTab(this, R.drawable.ic_home_white_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_explore_white_24dp))
                .addItem(new BottomBarTab(this, R.drawable.ic_person_white_24dp));

        mBnav.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof HomeFragment) {
                        currentFragment.popToChild(HomeMainFragment.class, false);
                    } else if (currentFragment instanceof ExploreFragment) {
                        currentFragment.popToChild(ExploreMainFragment.class, false);
                    } else if (currentFragment instanceof UserFragment) {
                        currentFragment.popToChild(UserMainFragment.class, false);
                    }
                    return;
                }


                // 这里推荐使用EventBus来实现 -> 解耦
                /*if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }*/
            }
        });

    }

    /*@Override
    public void onBackPressed()
    {
        // 回退键不再显示 APP 第一屏
        // super.onBackPressed(); // 注意不要调用父类的方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }*/

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public void onBackToFirstFragment() {
        mBnav.setCurrentItem(0);
    }

    /**
     * 底部导航条控制
     */
    public void mBnavCtrl(final String type)
    {
        if(!type.equals("show")&&!type.equals("hide")){ return; }

        Animation hideAnim;
        if(type.equals("hide")) {
            hideAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_bottom);
        } else {
            hideAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);
        }

        mBnav.startAnimation(hideAnim);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if(type.equals("hide")) {
                    mBnav.setVisibility(View.GONE);
                } else {
                    mBnav.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
