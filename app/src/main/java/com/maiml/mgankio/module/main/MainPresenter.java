package com.maiml.mgankio.module.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.maiml.mgankio.R;
import com.maiml.mgankio.ThemeManage;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.http.di.DataManager;
import com.maiml.mgankio.http.rx.callback.SubscriberOnNextListener;
import com.maiml.mgankio.http.rx.subscribe.NormalSubscriber;
import com.maiml.mgankio.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

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
    public void subscribe() {
        getBanner(true);
    }

    @Override
    public void unsubscribe() {

    }
}
