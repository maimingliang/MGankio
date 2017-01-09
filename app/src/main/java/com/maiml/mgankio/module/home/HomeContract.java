package com.maiml.mgankio.module.home;

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
    }

    interface Presenter extends BasePresenter{

        void findCategoryList(SearchTypeEnum searchTypeEnum);
    }
}
