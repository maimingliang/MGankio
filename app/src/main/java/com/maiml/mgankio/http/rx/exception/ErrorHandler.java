package com.maiml.mgankio.http.rx.exception;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by maimingliang on 2016/12/21.
 * 判断网络请求是否成功
 */

public class ErrorHandler {


    /**
     * 请求是否成功的处理逻辑
     * @param code   这里假设 code == 0 请求成功
     * @return
     */
    public static boolean isRequestSuccussful(int code){


        if(code == 0){
            return true;
        }

        return false;
    }


    public static boolean isNetWorkError(Throwable e){

        if (e instanceof SocketTimeoutException) {
             return true;
        }

        if (e instanceof ConnectException) {
            return true;
        }

        if(e instanceof UnknownHostException){
            return true;
        }

        if(e instanceof SocketException){
            return true;
        }

        if(e instanceof HttpException){
            return true;
        }

        return false;
    }

}
