package com.maiml.mgankio.module.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.common.CommonCons;
import com.maiml.mgankio.common.SearchTypeEnum;
import com.maiml.mgankio.http.di.DataManager;
import com.maiml.mgankio.http.rx.callback.SubscriberOnNextListener;
import com.maiml.mgankio.http.rx.exception.ErrorHandler;
import com.maiml.mgankio.http.rx.subscribe.NormalSubscriber;
import com.maiml.mgankio.module.main.MainContract;
import com.maiml.mgankio.utils.ImageUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by maimingliang on 2017/1/9.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    private DataManager mDataManager;

    private Context mContext;

    private  int page = 0;

    @Inject
    public HomePresenter(HomeContract.View view) {
        mView = view;
        mContext = mView.getContext();
        mDataManager = mView.getDataManager();
    }

    @Override
    public void findCategoryList(final SearchTypeEnum searchTypeEnum) {

        if(searchTypeEnum.NEW == searchTypeEnum){
            page = 0;
            mView.showSwipLoading();
        }
        page++;
        SubscriberOnNextListener<List<GankIoBean>> s = new SubscriberOnNextListener<List<GankIoBean>>() {
            @Override
            public void onNext(List<GankIoBean> gankIoBeen) {
                mView.hideSwipLoading();
                mView.notifyDataChanger(gankIoBeen,searchTypeEnum);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideSwipLoading();
            }
        };

        mDataManager.findCategoryList(mView.getCategoryName(), CommonCons.PAGE_SIZE,page,new NormalSubscriber<List<GankIoBean>>(mContext,"random/data",s));

    }

    @Override
    public void saveImg(final Drawable drawable) {
        if (drawable == null) {
            mView.showSaveImgInfo("图片为空，不能保存");
            return;
        }
        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        Subscription requestPermissionSubscription = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            saveImageToGallery(ImageUtil.drawableToBitmap(drawable));
                        } else {
                            mView.showSaveImgInfo("需要权限才能保存妹子");
                        }
                    }
                });

    }

    @Override
    public void showSaveImgDialog(Drawable drawable) {
        mView.showSaveImgDialog(drawable);
    }

    private void saveImageToGallery(final Bitmap bitmap) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean isSaveSuccess = ImageUtil.saveImageToGallery(mContext, bitmap);
                subscriber.onNext(isSaveSuccess);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean isSaveSuccess) {
                        if (isSaveSuccess) {
                            mView.showSaveImgInfo("图片保存成功");
                        } else {
                            mView.showSaveImgInfo("图片保存失败");
                        }
                    }
                });

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
