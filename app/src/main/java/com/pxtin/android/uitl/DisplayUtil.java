package com.pxtin.android.uitl;

import android.content.Context;
import com.pxtin.android.MainApplication;

/**
 * Created by Zneia on 2017/4/19.
 */

public class DisplayUtil
{
    private static Context mContext = MainApplication.getContext();

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变 
     *
     * @param pxValue 值
     * @return int
     */
    public static int pxToDip(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dip/dp 转 px
     *
     * @param dipValue 值
     * @return int
     */
    public static int dipToPx(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param pxValue 值
     * @return int
     */
    public static int pxToSp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param spValue 值
     * @return int
     */
    public static int spToPx(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
