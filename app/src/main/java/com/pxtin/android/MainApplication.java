package com.pxtin.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by Zneia on 2017/4/16.
 */

public class MainApplication extends Application
{
    public static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();

        /*Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                // 建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();*/
    }

    public static Context getContext()
    {
        return context;
    }

    /*public static String getReqUrl()
    {
        SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String data = preferences.getString("REQ_URL", "");
        if(data.equals("")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("REQ_URL", "http://www.qwqaq.com/pictin/search.html?keyword=");
            editor.apply();
        }
        return data;
    }*/
}
