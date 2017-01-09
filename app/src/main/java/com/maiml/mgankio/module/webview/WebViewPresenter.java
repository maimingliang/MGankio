package com.maiml.mgankio.module.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.maiml.mgankio.ThemeManage;

import javax.inject.Inject;


/**
 * WebViewPresenter
 * Created by bakumon on 16-12-10.
 */

public class WebViewPresenter implements WebViewContract.Presenter {

    private WebViewContract.View mWebViewView;


    @Inject
    public WebViewPresenter(WebViewContract.View webViewView) {
        mWebViewView = webViewView;
    }

    @Override
    public void subscribe() {

        mWebViewView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        loadDate();
    }

    private void loadDate() {

        mWebViewView.initWebView();
         mWebViewView.loadGankURL();
    }

    @Override
    public void unsubscribe() {
    }


}
