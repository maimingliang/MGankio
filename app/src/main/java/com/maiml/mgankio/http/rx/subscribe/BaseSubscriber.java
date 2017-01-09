package com.maiml.mgankio.http.rx.subscribe;

import android.content.Context;


import com.maiml.mgankio.http.rx.callback.SubscriberOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * Created by maimingliang on 2016/12/26.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    /* 软引用回调接口*/
    protected SoftReference<SubscriberOnNextListener> mSubscriberOnNextListener;


    /*软引用反正内存泄露*/
    protected SoftReference<RxAppCompatActivity> mActivity;

    protected String method;

    public BaseSubscriber(Context context, String method,SubscriberOnNextListener mSubscriberOnNextListener) {

        this.mSubscriberOnNextListener = new SoftReference<SubscriberOnNextListener>(mSubscriberOnNextListener);
        this.mActivity = new SoftReference<RxAppCompatActivity>((RxAppCompatActivity) context);

        this.method = method;
    }


    public RxAppCompatActivity getRxAppCompatActivity() {
        return mActivity.get();
    }

    public String getMethod() {
        return method;
    }

    public abstract void onCacheNext(String s);
}
