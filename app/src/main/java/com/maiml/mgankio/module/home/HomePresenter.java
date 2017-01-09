package com.maiml.mgankio.module.home;

import android.content.Context;

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

import java.util.List;

import javax.inject.Inject;

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
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
