package com.maiml.mgankio.http.rx;

/**
 * Created by maimingliang on 16/6/22.
 */



import com.maiml.mgankio.base.BaseHttpResult;
import com.maiml.mgankio.http.rx.exception.ApiException;
import com.maiml.mgankio.http.rx.exception.ErrorHandler;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
 *
 * @author maimingliang
 */
public class HttpResultFunc<T> implements Func1<BaseHttpResult<T>, T> {

    private static final String TAG = "HttpResultFunc";

    @Override
    public T call(BaseHttpResult<T> httpResult) {

        if (!ErrorHandler.isRequestSuccussful(httpResult.getStatus())) {
            throw new ApiException(httpResult.getStatus(),httpResult.getError()==null?"":httpResult.getError());
        }
        return httpResult.getResults();
    }
}

