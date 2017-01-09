package com.maiml.mgankio.base;

import android.content.Context;

import com.maiml.mgankio.http.di.DataManager;

/**
 * BaseView
 * Created by maimingliang
 */
public interface  BaseView {


    DataManager getDataManager();

    Context getContext();

}
