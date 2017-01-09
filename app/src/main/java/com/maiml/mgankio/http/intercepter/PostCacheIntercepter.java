package com.maiml.mgankio.http.intercepter;


import android.content.Context;


import com.maiml.mgankio.utils.CookieDbUtil;
import com.maiml.mgankio.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

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
 * Post 请求缓存
 */

public class PostCacheIntercepter implements Interceptor {


    private CookieDbUtil dbUtil;
    /*是否缓存标识*/
    private boolean cache;

    public PostCacheIntercepter(boolean cache, Context context) {
        dbUtil=CookieDbUtil.getInstance(context);
        this.cache=cache;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        HttpUrl printUrl = request.url();

        LogUtil.i("请求头信息 start ");
        LogUtil.i("url = " + printUrl.toString());
//                LogUtil.i("request headers = " + request.headers().toString());
        LogUtil.i("请求头信息 end ");

        //获得返回头，如果有网络，就缓存一分钟,没有网络缓存四周
        Response originalResponse = chain.proceed(request);


//                LogUtil.i("响应头信息  start ");
//                LogUtil.i("response headers = " + originalResponse.headers().toString());
//                LogUtil.i("response body = "+originalResponse.body().toString());
//                LogUtil.i("响应头信息  end ");

        if (cache) {
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
            String url = request.url().toString();
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
        }
        return originalResponse;

    }
}
