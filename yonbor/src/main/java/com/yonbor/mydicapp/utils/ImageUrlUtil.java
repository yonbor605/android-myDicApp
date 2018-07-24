package com.yonbor.mydicapp.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/6/22.
 */

public class ImageUrlUtil {
    public static String getUrl(String url, String id){
        if (TextUtils.isEmpty(id) || "0".equals(id)) return null;
        return url + id;
    }
    public static String getUrl(String url, long id){
        if(id == 0) return null;
        return url + id;
    }
}
