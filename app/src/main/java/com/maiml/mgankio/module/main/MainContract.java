package com.maiml.mgankio.module.main;

import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.maiml.mgankio.base.BasePresenter;
import com.maiml.mgankio.base.BaseView;

/**
 * Created by maimingliang on 2017/1/6.
 */

public interface MainContract {

    interface View extends BaseView{

        void setBanner(String imgUrl);

        void startBannerLoadingAnim();

        void stopBannerLoadingAnim();

        void enableFabButton();

        void disEnableFabButton();

        void setAppBarLayoutColor(int appBarLayoutColor);

        void setFabButtonColor(int color);
    }

    interface Presenter extends BasePresenter{

        void getRandomBanner();

        void getBanner(final boolean isRandom);

        void setThemeColor(@Nullable Palette palette);
    }

}
