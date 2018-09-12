package com.yonbor.mydicapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yonbor.baselib.base.AppContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/11 17:30
 */
public class CookieUtil {

    private static final String COOKIE_PREF = "cookies_prefs";

    //整合cookie为唯一字符串
    public static String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) continue;
                set.add(s);
            }
        }

        Iterator<String> ite = set.iterator();
        while (ite.hasNext()) {
            String cookie = ite.next();
            sb.append(cookie).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }

        return sb.toString();
    }


    //保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
    //这样能使得该cookie的应用范围更广
    public static void saveCookie(String url, String domain, String cookies) {
        SharedPreferences sp = AppContext.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("Url is null.");
        } else {
            editor.putString(url, cookies);
        }

        if (!TextUtils.isEmpty(domain)) {
            editor.putString(domain, cookies);
        }

        editor.apply();
    }


    public static String getCookie(String url, String domain) {
        SharedPreferences sp = AppContext.getContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url, ""))) {
            return sp.getString(url, "");
        }
        if (!TextUtils.isEmpty(domain) && sp.contains(domain) && !TextUtils.isEmpty(sp.getString(domain, ""))) {
            return sp.getString(domain, "");
        }

        return null;
    }


}
