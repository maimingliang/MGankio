package com.maiml.mgankio.module.launch;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * LauncherPresenter
 * Created by bakumon on 16-12-6.
 */

public class LauncherPresenter implements LauncherContract.Presenter {

    private LauncherContract.View mLauncherView;


    public LauncherPresenter(LauncherContract.View launcherView) {
        mLauncherView = launcherView;

    }

    @Override
    public void subscribe() {
        start();
    }

    @Override
    public void unsubscribe() {

    }

    private void start() {
        Observable.just(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delaySubscription(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mLauncherView.toHome();
                    }
                });
    }
}
