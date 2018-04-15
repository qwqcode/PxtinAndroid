package com.pxtin.android.net;

import com.pxtin.android.GlobalConstant;
import com.pxtin.android.MainApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.OkHttpClient;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zneia on 2017/3/18.
 */

public class HttpReqUtil
{
    public HttpReqUtil(String keyWord, int pageNum, StringCallback ha){
        String url = "http://192.168.3.105/api/search";

        // 初始化 okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                // 其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        // 下发请求指令
        OkHttpUtils
                .post()
                .url(url)
                .addParams("keyWords", keyWord.trim())
                .addParams("page", String.valueOf(pageNum))
                .addHeader("Referer", "")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                // .addHeader("Authorization", "Basic YmV0YTpiZXRhMTIz")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .build()
                .execute(ha);
    }
}
