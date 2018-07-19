package com.yonbor.baselib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yonbor.baselib.base.AppContext;


/**
 * Created by Administrator on 2017/3/9.
 */
public class NetworkUtil {

    /**
     * 描述：判断网络是否有效.
     *
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) AppContext.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 判断当前网络是否是移动数据网络.
     *
     * @return boolean
     */
    public static boolean isMobile() {
        ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

}
