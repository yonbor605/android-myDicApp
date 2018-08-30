package com.yonbor.baselib.base;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Administrator on 2018/7/11.
 */
public class AppContext {
    private static Context sContext;
    private static Resources sResource;//直接获取Resources 放在application，可以减少调用getResources的次数
    private static String baseUrl, baseUrl2;
    private static String httpHeaderAction;

    public static void initialize(Context context) {
        sContext = context;
        sResource = sContext.getResources();
    }

    public static void initialize(Context context, String url, String url2) {
        initialize(context);
        baseUrl = url;
        baseUrl2 = url2;
    }

    public static void initialize(Context context, String url, String action, String url2) {
        initialize(context);
        baseUrl = url;
        httpHeaderAction = action;
        baseUrl2 = url2;
    }


    public static Context getContext() {
        return sContext;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getBaseUrl2() {
        return baseUrl2;
    }

    public static String getHttpHeaderAction() {
        return httpHeaderAction;
    }


    public static Resources resources() {
        return sResource;
    }


    public static void clear() {
        sContext = null;
        sResource = null;
        baseUrl = null;
        baseUrl2 = null;
        httpHeaderAction = null;
    }
}
