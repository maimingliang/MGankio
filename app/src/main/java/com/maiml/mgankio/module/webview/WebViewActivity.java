package com.maiml.mgankio.module.webview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maiml.mgankio.R;
import com.maiml.mgankio.ThemeManage;
import com.maiml.mgankio.base.BaseActivity;
import com.maiml.mgankio.http.di.AppModule;
import com.maiml.mgankio.http.di.DataManager;
import com.maiml.mgankio.module.home.di.DaggerHomeComponent;
import com.maiml.mgankio.module.home.di.HomeModule;
import com.maiml.mgankio.utils.DeviceUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WebViewActivity extends BaseActivity implements WebViewContract.View {
    public static final String GANK_URL = "gank_url";
    public static  final String GANK_TITLE = "gank_title";

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.progressbar_webview)
    ProgressBar mProgressbarWebview;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.web_view)
    WebView mWebView;

    private String mGankUrl;
    private String mGankTitle;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initViewsAndEvents() {


        mGankUrl = getIntent().getStringExtra(GANK_URL);
        mGankTitle = getIntent().getStringExtra(GANK_TITLE);

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbar.setPadding(
                    mAppbar.getPaddingLeft(),
                    mAppbar.getPaddingTop() + DeviceUtil.getStatusBarHeight(this),
                    mAppbar.getPaddingRight(),
                    mAppbar.getPaddingBottom());
        }
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTvTitle.setText(mGankTitle);
        subscribe();
    }

    public void subscribe() {
        setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        loadDate();
    }

    private void loadDate() {
      initWebView();
      loadGankURL();

    }

    @Override
    public void loadGankURL() {
        mWebView.loadUrl(mGankUrl);
    }

    @Override
    public void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        mWebView.setWebChromeClient(new MyWebChrome());
        mWebView.setWebViewClient(new MyWebClient());
    }
    class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(mProgressbarWebview != null){
                mProgressbarWebview.setVisibility(View.VISIBLE);
                mProgressbarWebview.setProgress(newProgress);
            }

        }
    }

    class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if(mProgressbarWebview != null){
                mProgressbarWebview.setVisibility(View.GONE);
            }

        }
    }
    @Override
    public void setToolbarBackgroundColor(int color) {
        mAppbar.setBackgroundColor(color);
    }


    @Override
    public DataManager getDataManager() {
        return null;
    }

    @Override
    public Context getContext() {
        return this;
    }
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

}
