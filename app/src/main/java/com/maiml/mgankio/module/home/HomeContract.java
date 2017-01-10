package com.maiml.mgankio.module.home;

import android.graphics.drawable.Drawable;

import com.maiml.mgankio.base.BasePresenter;
import com.maiml.mgankio.base.BaseView;
import com.maiml.mgankio.bean.GankIoBean;
import com.maiml.mgankio.common.SearchTypeEnum;

import java.util.List;

/**
 * Created by maimingliang on 2017/1/9.
 */

public interface HomeContract {


    interface View extends BaseView{

        String getCategoryName();

        void showSwipLoading();

        void hideSwipLoading();

        void notifyDataChanger(List<GankIoBean> list , SearchTypeEnum searchTypeEnum);

        void showSaveImgDialog(Drawable drawable);

        void showSaveImgInfo(String msg);
    }

    interface Presenter extends BasePresenter{

        void findCategoryList(SearchTypeEnum searchTypeEnum);

        void saveImg(Drawable drawable);

        void showSaveImgDialog(Drawable drawable);
    }
}
