package com.cjym.yunmabao.config;

import com.cjym.yunmabao.BuildConfig;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 17:00
 * 修改人：Administrator
 * 修改时间：2017-12-07 17:00
 * 修改备注：
 */
public class AppConfig {
    public static boolean DEBUG = BuildConfig.LOG_DEBUG;
    public static final boolean SPEAKER = true;

    //开发环境
    public static final String Base_URL = "http://store.demo.box.bdhongshan.com/index.php/boss/";
    public static final String Base_URL_EC = "http://store.demo.box.bdhongshan.com/index.php/boss/";



    public static final int connect_timeout = 10;
    public static final int read_timeout = 10;
    public static final int write_timeout = 10;
}
