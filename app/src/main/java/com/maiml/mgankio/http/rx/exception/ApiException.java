package com.maiml.mgankio.http.rx.exception;



/**
 * Created by maimingliang on 16/3/10.
 *  由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
 * 需要根据错误码对错误信息进行一个转换，在显示给用户
 */
public class ApiException extends RuntimeException {



    public ApiException(int resultCode, String errorMsg) {
        this(getApiExceptionMessage(resultCode,errorMsg));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }


    private static String getApiExceptionMessage(int code,String errMsg){
         return errMsg;
    }
}

