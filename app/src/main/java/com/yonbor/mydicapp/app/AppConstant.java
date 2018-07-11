
package com.yonbor.mydicapp.app;


import com.yonbor.mydicapp.BuildConfig;

public class AppConstant {

    public static boolean DEBUG = BuildConfig.DEBUG;
    public static final String APPLICATION_ID = BuildConfig.APPLICATION_ID;

    // api地址
    public static String HttpApiUrl;

    //http header里事件处理
    public static final String Http_Header_ACTION = APPLICATION_ID + ".http.header.action";



}
