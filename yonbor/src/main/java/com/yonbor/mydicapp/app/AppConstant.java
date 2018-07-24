
package com.yonbor.mydicapp.app;


import com.yonbor.mydicapp.BuildConfig;

public class AppConstant {

    public final static boolean IS_DEBUG = BuildConfig.IS_DEBUG;
    public static final String APPLICATION_ID = BuildConfig.APPLICATION_ID;
    public final static boolean IS_LOG = BuildConfig.IS_LOG;

    public static String BASE_URL = BuildConfig.BASE_URL;
    //图片地址
    public static String HTTP_IMG_URL = BuildConfig.HTTP_IMG_URL;

    public static final String Product_Name = "hcn.chaoyang.patient_android";//产品编码

    //http header里事件处理
    public static final String Http_Header_ACTION = APPLICATION_ID + ".http.header.action";
    // 退出
    public static final String Logout_ACTION = APPLICATION_ID + ".logout.action";
    // 关闭
    public static final String CLOSE_ACTION = APPLICATION_ID + ".close.action";





    public static final String SP_KEY_MADE_CODE = "SP_KEY_MADE_CODE";
    public static final String SP_KEY_SCAN_CODE = "SP_KEY_SCAN_CODE";










}
