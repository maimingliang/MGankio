package com.maiml.mgankio.http.rx.subscribe;

import android.content.Context;
import android.widget.Toast;

import com.maiml.mgankio.http.rx.callback.SubscriberOnNextListener;
import com.maiml.mgankio.http.rx.exception.ErrorHandler;


/**
 * Created by maimingliang on 2016/12/26.
 */

public class NormalSubscriber<T> extends BaseSubscriber<T> {


    public NormalSubscriber(Context context, String method,SubscriberOnNextListener mSubscriberOnNextListener) {
        super(context,method, mSubscriberOnNextListener);
    }

    @Override
    public void onCacheNext(String s) {
        if(mSubscriberOnNextListener.get() != null){
            mSubscriberOnNextListener.get().onCacheNext(s);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    /**
     * 对错误进行统一处理
     * @param e
     */
    @Override
    public void onError(Throwable e) {
//        http://gank.io/api/random/data/Android/10
        doError(e);


    }

    private void doError(Throwable e) {
        Context context = mActivity.get();

        if(context == null){
            return;
        }

        if(ErrorHandler.isNetWorkError(e)){
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, e.getMessage() +"  -- > " + e.getClass().getName(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if(mSubscriberOnNextListener.get() != null){
            mSubscriberOnNextListener.get().onError(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {

        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }
}
