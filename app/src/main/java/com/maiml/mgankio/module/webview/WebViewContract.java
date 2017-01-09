package com.maiml.mgankio.module.webview;

import android.app.Activity;

import com.maiml.mgankio.base.BasePresenter;
import com.maiml.mgankio.base.BaseView;


/**
 * WebViewContract
 * Created by bakumon on 16-12-10.
 */

public interface WebViewContract {

    interface View extends BaseView {




        void loadGankURL();

        void initWebView();

        void setToolbarBackgroundColor(int color);


    }

    interface Presenter extends BasePresenter {

    }
}
