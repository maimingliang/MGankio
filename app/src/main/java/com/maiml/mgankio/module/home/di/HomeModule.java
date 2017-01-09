package com.maiml.mgankio.module.home.di;

import com.maiml.mgankio.module.home.HomeContract;
import com.maiml.mgankio.module.main.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by maimingliang on 2017/1/9.
 */

@Module
public class HomeModule {

    private final HomeContract.View mView;

    public HomeModule(HomeContract.View view) {
        mView = view;
    }

    @Provides
    HomeContract.View provideHomeView() {
        return mView;
    }
}
