package com.maiml.mgankio.http.di;

import android.content.Context;


import com.maiml.mgankio.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by maimingliang on 16/6/15.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    Context context();  // 提供Applicaiton的Context

//    void inject(MainActivity activity);
 }
