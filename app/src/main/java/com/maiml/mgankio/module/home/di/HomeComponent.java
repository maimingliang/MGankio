package com.maiml.mgankio.module.home.di;

import com.maiml.mgankio.MainActivity;
import com.maiml.mgankio.http.di.AppModule;
import com.maiml.mgankio.module.home.CategoryFragment;
import com.maiml.mgankio.module.home.MeiZhiFragment;
import com.maiml.mgankio.module.main.di.MainModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by maimingliang on 2017/1/6.
 */

@Component(modules = {HomeModule.class,AppModule.class})
@Singleton
public interface HomeComponent {

    void inject(MeiZhiFragment activity);
    void inject(CategoryFragment fragment);

}
