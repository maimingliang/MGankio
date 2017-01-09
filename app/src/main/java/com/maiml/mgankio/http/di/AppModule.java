package com.maiml.mgankio.http.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maiml.mgankio.App;
import com.maiml.mgankio.http.intercepter.GetCacheIntercepter;
import com.maiml.mgankio.utils.FileUtils;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maimingliang
 */
@Module
public class AppModule {

    private final Context mContext;


    public AppModule(Context context) {
        this.mContext = context;
    }



    @Provides
    Interceptor providesIntercepter(){
        return new GetCacheIntercepter(mContext);
    }

    @Provides
    Cache providesCache(){
        File httpCacheFile = FileUtils.getDiskCacheDir("response");
        return new Cache(httpCacheFile, 1024 * 100 * 1024);
    }


    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Interceptor interceptor) {

        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //设置出现错误进行重新连接。

                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .build());
                    }
                })   //拦截器
                 .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor)
//                .cache(providesCache())
                .build();



         return okhttpClient;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okhttpClient) {

        Gson gson = new GsonBuilder().serializeNulls().create();


        Retrofit retrofit = new Retrofit.Builder()
                .client(okhttpClient)
                .baseUrl(App.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


    @Provides
    public DataManager provideManager(ApiService service) {
        return new DataManager(service);
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }


}
