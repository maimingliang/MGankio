package com.maiml.mgankio.module.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.maiml.mgankio.R;
import com.maiml.mgankio.ThemeManage;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.http.di.DataManager;
import com.maiml.mgankio.http.rx.callback.SubscriberOnNextListener;
import com.maiml.mgankio.http.rx.subscribe.NormalSubscriber;
import com.maiml.mgankio.utils.ImageUtil;
import com.maiml.mgankio.utils.LogUtil;
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
 * Created by maimingliang on 2017/1/6.
 */

public class MainPresenter implements MainContract.Presenter{


    private MainContract.View mView;

    private DataManager mDataManager;

    private Context mContext;
    @Inject
    public MainPresenter(MainContract.View view){
        this.mView = view;
        mDataManager = mView.getDataManager();
        mContext = mView.getContext();
    }





    @Override
    public void getRandomBanner() {

    }

    @Override
    public void getBanner(boolean isRandom) {

        if(mDataManager == null){
            return;
        }

        mView.startBannerLoadingAnim();
        mView.disEnableFabButton();
        SubscriberOnNextListener<List<GankIoBean>> s = new SubscriberOnNextListener<List<GankIoBean>>() {
            @Override
            public void onNext(List<GankIoBean> gankIoBeen) {
                LogUtil.d(gankIoBeen);
                GankIoBean bean = gankIoBeen.get(0);

                mView.setBanner(bean.getUrl());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.enableFabButton();
                mView.stopBannerLoadingAnim();
            }
        };
        mDataManager.findList("福利",1,new NormalSubscriber<List<GankIoBean>>(mContext,"list",s));
    }

    @Override
    public void setThemeColor(@Nullable Palette palette) {
        if (palette != null) {
            int colorPrimary = mContext.getResources().getColor(R.color.colorPrimary);
            // 把从调色板上获取的主题色保存在内存中
            ThemeManage.INSTANCE.setColorPrimary(palette.getDarkVibrantColor(colorPrimary));
            // 设置 AppBarLayout 的背景色
            mView.setAppBarLayoutColor(ThemeManage.INSTANCE.getColorPrimary());
            // 设置 FabButton 的背景色
            mView.setFabButtonColor(ThemeManage.INSTANCE.getColorPrimary());
            // 停止 FabButton 加载中动画
            mView.enableFabButton();
            mView.stopBannerLoadingAnim();
        }
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
        getBanner(true);
    }

    @Override
    public void unsubscribe() {

    }
}
