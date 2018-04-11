package com.cjym.yunmabao.utils;

import android.util.Log;

import com.cjym.yunmabao.config.AppConfig;

public class LogUtils {

	public static final boolean is_Debug = true;
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	public static int LEVEL = NOTHING;//如果把等级设为4，那么只有4，5才能打印出来，其他的不能打印出来

	static {
		if(!AppConfig.DEBUG) {
			LEVEL = NOTHING;   //如果不是debug模式，什么都不要打印
		} else {
			LEVEL = DEBUG;
		}
	}
	public static void v(String tag,String msg) {
		if(is_Debug) {
			if(LEVEL <= VERBOSE) {
				Log.v(tag, msg);
			}
		}
	}
	public static void d(String tag,String msg) {
		if(is_Debug) {
			if(LEVEL <= DEBUG) {
				Log.d(tag, msg);
			}
		}
	}
	public static void i(String tag,String msg) {
		if(is_Debug) {
			if(LEVEL <= INFO) {
				Log.i(tag, msg);
			}
		}
	}
	public static void w(String tag,String msg) {
		if(is_Debug) {
			if(LEVEL <= WARN) {
				Log.w(tag, msg);
			}
		}
	}
	public static void e(String tag,String msg) {
		if(is_Debug) {
			if(LEVEL <= ERROR) {
				Log.e(tag, msg);
			}
		}
	}
}
