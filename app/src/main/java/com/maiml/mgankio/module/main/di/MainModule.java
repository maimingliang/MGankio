package com.maiml.mgankio.module.main.di;

import com.maiml.mgankio.module.main.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by maimingliang on 2017/1/6.
 */
@Module
public class MainModule {

    private final MainContract.View mView;

    public MainModule(MainContract.View view) {
        mView = view;
    }

    @Provides
    MainContract.View provideMainView() {
        return mView;
    }
}
