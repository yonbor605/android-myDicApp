
package com.yonbor.mydicapp.app;


import com.yonbor.mydicapp.BuildConfig;

public class AppConstant {

    public final static boolean IS_DEBUG = BuildConfig.IS_DEBUG;
    public static final String APPLICATION_ID = BuildConfig.APPLICATION_ID;
    public final static boolean IS_LOG = BuildConfig.IS_LOG;

    public static String BASE_URL = BuildConfig.BASE_URL;

    //http header里事件处理
    public static final String Http_Header_ACTION = APPLICATION_ID + ".http.header.action";
    // 退出
    public static final String Logout_ACTION = APPLICATION_ID + ".logout.action";
    // 关闭
    public static final String CLOSE_ACTION = APPLICATION_ID + ".close.action";










}
