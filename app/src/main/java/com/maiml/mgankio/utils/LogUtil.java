package com.maiml.mgankio.utils;


import android.util.Log;


import com.maiml.mgankio.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * 日志
 *
 * @author maimingliang
 *
 */
public final class LogUtil {

 	private static final String TAG = "LogUtil";

	public static void init(){
		Logger.init(TAG);
	}

	public static void v(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG){
			Logger.v(msg);
		}
	}
	public static void v(String tag, String msg, Throwable tr) {
		if (BuildConfig.LOG_DEBUG){
			Log.v(tag, msg, tr);
		}
	}
	public static void d(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG){
			Logger.d(msg);
		}
	}
	public static void d(String tag, String msg, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			Log.d(tag, msg, tr);
	}

	public static void i(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG ){
			Logger.i(msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			LogUtil.d(tag, msg, tr);
	}

	public static void w(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG )
			Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			Log.w(tag, msg, tr);
	}

	public static void w(String tag, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			Log.w(tag, tr.getMessage(), tr);
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG ){
			Logger.e(msg);
		}
 	}

	public static void e(String tag, String msg, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			Log.e(tag, msg, tr);
	}

	public static void e(String tag, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			Log.e(tag, tr.getMessage(), tr);
	}

	public static void wtf(String tag, String msg) {
		if (BuildConfig.LOG_DEBUG )
			Log.wtf(tag, msg);
	}

	public static void wtf(String tag, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			Log.wtf(tag, tr);
	}

	public static void wtf(String tag, String msg, Throwable tr) {
		if (BuildConfig.LOG_DEBUG )
			LogUtil.wtf(tag, msg, tr);
	}



	/**
	 * log.i
	 * @param msg
	 */
	public static void i(String msg) {
		if (BuildConfig.DEBUG) {
			Logger.i(msg);
		}
	}

	/**
	 * log.d
	 * @param msg
	 */
	public static void d(Object msg) {
		if (BuildConfig.DEBUG) {
			Logger.d(msg);
		}
	}

	/**
	 * log.w
	 * @param msg
	 */
	public static void w(String msg) {
		if (BuildConfig.DEBUG) {
			Logger.w(msg);
		}
	}

	/**
	 * log.e
	 * @param msg
	 */
	public static void e(String msg) {
		Logger.e(msg);
	}


}
