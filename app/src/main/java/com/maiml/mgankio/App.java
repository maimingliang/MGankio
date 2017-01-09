package com.maiml.mgankio;

import android.app.Application;
import android.content.Context;

import com.maiml.mgankio.utils.CrashHandler;
import com.maiml.mgankio.utils.LogUtil;


/**
 *
 * 什么是逻辑
 *     UI逻辑
 *     业务逻辑
 * 什么算一个模块
 *      一个功能一个模块？
 *
 * Created by maimingliang on 2016/12/29.
 */

public class App extends Application {


    private static Context instance;

    public final static boolean IS_CACHE = false;
    public final static String  SERVER_ADDRESS = "http://gank.io/api/";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.init();
//        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
//        ComponentHolder.setAppComponent(appComponent);
        //捕获全局异常
        CrashHandler.getInstance().init(this);
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));


    }


    public static Context getInstance(){
        return instance;
    }

}
