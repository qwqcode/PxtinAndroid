package com.pxtin.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.pxtin.android.R;
import com.pxtin.android.uitl.PermissionHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * APP 第一屏
 */
public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Context mContext;
    private PermissionHelper mPermissionHelper;

    private static final int DELAY_TIME = 1500; // 最短停留显示时间

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        mContext = this;
        // 设置布局
        setContentView(R.layout.activity_splash);

        // TODO: 权限检测，并正式运行程序
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                // Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
                runtimeInit();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            // 如果系统版本低于23，直接跑应用的逻辑
            // Log.d(TAG, "The api level of system is lower than 23, so run app logic directly.");
            runtimeInit();
        } else {
            // 如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                // Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
                runtimeInit();
            } else {
                // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
                // Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 程序运行
     */
    private void runtimeInit()
    {
        // 延迟特定的时间送入主界面s
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_TIME);
    }
}
