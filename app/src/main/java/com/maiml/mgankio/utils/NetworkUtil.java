package com.maiml.mgankio.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by maimingliang
 */
public class NetworkUtil {
    /*
    * 判断是否有网络连接
    *
    * */

    public static boolean isNetWorkActive(Context context)
    {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
