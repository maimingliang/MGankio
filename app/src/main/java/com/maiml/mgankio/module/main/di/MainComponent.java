package com.maiml.mgankio.module.main.di;

import com.maiml.mgankio.MainActivity;
import com.maiml.mgankio.http.di.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by maimingliang on 2017/1/6.
 */

@Component(modules = {MainModule.class,AppModule.class})
@Singleton
public interface MainComponent {

    void inject(MainActivity activity);
}
