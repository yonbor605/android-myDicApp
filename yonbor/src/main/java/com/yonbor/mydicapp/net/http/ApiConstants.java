package com.yonbor.mydicapp.net.http;

import com.yonbor.mydicapp.app.AppConstant;

public class ApiConstants {


    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.BASE_URL_FIRST:
                host = AppConstant.BASE_URL;
                break;
            case HostType.BASE_URL_SECOND:
                host = AppConstant.BASE_URL2;
                break;

            default:
                host = "";
                break;
        }
        return host;
    }
}
