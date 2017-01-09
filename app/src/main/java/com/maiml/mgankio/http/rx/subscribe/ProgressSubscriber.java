package com.maiml.mgankio.http.rx.subscribe;

import android.content.Context;
import android.widget.Toast;


import com.maiml.mgankio.http.rx.callback.DialogListener;
import com.maiml.mgankio.http.rx.callback.SubscriberOnNextListener;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by
 */
public class ProgressSubscriber<T> extends BaseSubscriber<T> {

    private static final String TAG = "ProgressSubscriber";


    private DialogListener mDialogListener;

    public ProgressSubscriber(Context context, String method, SubscriberOnNextListener mSubscriberOnNextListener, DialogListener mDialogListener) {
         super(context,method,mSubscriberOnNextListener);
         this.mDialogListener = mDialogListener;
     }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
//        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
//        dismissProgressDialog();
//        Toast.makeText(context, "获取数据成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        Context context = mActivity.get();

        if(context == null){
            return;
        }

        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if(e instanceof UnknownHostException){
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if(e instanceof SocketException){
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCacheNext(String s) {

    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
//    @Override
//    public void onCancelProgress() {
//        if (!this.isUnsubscribed()) {
//            this.unsubscribe();
//        }
//    }
}