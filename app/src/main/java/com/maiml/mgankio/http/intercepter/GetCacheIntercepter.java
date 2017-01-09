package com.maiml.mgankio.http.intercepter;


import android.content.Context;


import com.maiml.mgankio.App;
import com.maiml.mgankio.utils.CookieDbUtil;
import com.maiml.mgankio.utils.LogUtil;
import com.maiml.mgankio.utils.NetworkUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by maimingliang on 2016/12/26.
 * <p>
 * get 请求缓存
 */

public class GetCacheIntercepter implements Interceptor {

    private CookieDbUtil dbUtil;

    private Context mContext;

    public GetCacheIntercepter(Context context) {
        dbUtil=CookieDbUtil.getInstance(context);
        this.mContext = context.getApplicationContext();

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        HttpUrl urlStr = request.url();

        LogUtil.i("请求头信息 start ");
        LogUtil.i("url = " + urlStr.toString());
        LogUtil.i("method = " + request.method());
//                LogUtil.i("request headers = " + request.headers().toString());
        LogUtil.i("请求头信息 end ");

        //更改请求头
        if (!NetworkUtil.isNetWorkActive(mContext)) {
            //如果没有网络，那么就强制使用缓存数据
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        //获得返回头，如果有网络，就缓存一分钟,没有网络缓存四周
        Response originalResponse = chain.proceed(request);


        if(App.IS_CACHE && request.method().equals("POST") && originalResponse.isSuccessful()){

            ResponseBody body = originalResponse.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            String url =request.url().toString();
            CookieResult result = dbUtil.queryCookieBy(url);
            long time = System.currentTimeMillis();
            /*保存和更新本地数据*/
            if (result == null) {
                result = new CookieResult(url, bodyString, time);
                dbUtil.saveCookie(result);
            } else {
                result.setResulte(bodyString);
                result.setTime(time);
                dbUtil.updateCookie(result);
            }

            return originalResponse;

        }

//                LogUtil.i("响应头信息  start ");
//                LogUtil.i("response headers = " + originalResponse.headers().toString());
//                LogUtil.i("response body = "+originalResponse.body().toString());
//                LogUtil.i("响应头信息  end ");

        //更改响应头
        if (NetworkUtil.isNetWorkActive(mContext)) {

            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=60")//有网失效一分钟
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 6)// 没网失效6小时
                    .build();
        }
      }


}
