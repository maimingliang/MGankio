package com.maiml.mgankio.http.di;


import com.maiml.mgankio.App;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.common.CommonCons;
import com.maiml.mgankio.http.rx.HttpResultFunc;
import com.maiml.mgankio.utils.CookieDbUtil;
import com.maiml.mgankio.http.intercepter.CookieResult;
import com.maiml.mgankio.http.rx.exception.RetryWhenNetworkException;
import com.maiml.mgankio.http.rx.subscribe.BaseSubscriber;
import com.maiml.mgankio.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author maimingliang
 */
public class DataManager {

    private static final String TAG = "DataManager";

    private final ApiService mApiService;

    @Inject
    public DataManager(ApiService apiService) {
        this.mApiService = apiService;
    }


    public void findList(String category, int num, final BaseSubscriber<List<GankIoBean>> s){

        LogUtil.d("findList params");
        LogUtil.d(category);
        LogUtil.d(num);


        if(userCache(s)){
            return;
        }

        Observable<List<GankIoBean>> observable = mApiService.findList(category, num)
                .map(new HttpResultFunc<List<GankIoBean>>());

        toSubscribe(observable,s);

    }
    public void findCategoryList(String category, int num,int page, final BaseSubscriber<List<GankIoBean>> s){

        LogUtil.d("findList params");
        LogUtil.d(category);
        LogUtil.d(num);


        if(userCache(s)){
            return;
        }

        Observable<List<GankIoBean>> observable = mApiService.findCategoryList(category, num,page)
                .map(new HttpResultFunc<List<GankIoBean>>());

        toSubscribe(observable,s);

    }
    private <T> void toSubscribe(Observable<T> o, BaseSubscriber<T> s) {

        o.retryWhen(new RetryWhenNetworkException())  /*失败后的retry配置*/
                .compose(s.getRxAppCompatActivity().<T>bindToLifecycle())     /*生命周期管理*/
                .subscribeOn(Schedulers.io())  /*http请求线程*/
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    private <T> boolean userCache(final BaseSubscriber<T> s) {

        if (!App.IS_CACHE) {
            return false;
        }

        /*获取缓存数据*/
        final CookieResult cookieResulte = CookieDbUtil.getInstance(App.getInstance()).queryCookieBy(s.getMethod());

        if (cookieResulte == null) {
            return false;
        }

        long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;

        if (time >= CommonCons.POST_REQUEST_CACHE_MAX_TIME) {
            CookieDbUtil.getInstance(App.getInstance()).deleteCookie(cookieResulte);
            return false;
        }

        Observable.just(cookieResulte.getResulte()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                s.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                s.onError(e);
                CookieDbUtil.getInstance(App.getInstance()).deleteCookie(cookieResulte);
            }

            @Override
            public void onNext(String str) {

                LogUtil.d("读取缓存 = " + str);
                s.onCacheNext(str);
            }
        });

        return true;


    }

}




