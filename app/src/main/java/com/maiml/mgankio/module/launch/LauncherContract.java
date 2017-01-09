package com.maiml.mgankio.module.launch;

import com.maiml.mgankio.base.BasePresenter;
import com.maiml.mgankio.base.BaseView;

/**
 * Created by maimingliang on 2017/1/6.
 */

public interface LauncherContract {

    interface View extends BaseView {

        void toHome();
    }

    interface Presenter extends BasePresenter {

    }

}
