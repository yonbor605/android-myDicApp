package com.yonbor.mydicapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.yonbor.mydicapp.app.AppConstant;

/**
 * Created by Administrator on 2017/5/15.
 * 获取服务端图片时需要图片尺寸
 */

public class ImageSizeUtil {
    public static final int HEAD_W_1 = 400;
    public static final int HEAD_W_2 = 200;
    public static final int HEAD_H_1 = 400;
    public static final int HEAD_H_2 = 200;
    public static final int[] HEAD_W = {HEAD_W_1, HEAD_W_2};
    public static final int[] HEAD_H = {HEAD_H_1, HEAD_H_2};


    public static final int BANNER_W_1 = 1280;
    public static final int BANNER_W_2 = 900;
    public static final int BANNER_W_3 = 600;
    public static final int BANNER_H_1 = 640;
    public static final int BANNER_H_2 = 450;
    public static final int BANNER_H_3 = 300;
    public static final int[] BANNER_W = {BANNER_W_1, BANNER_W_2, BANNER_W_3};
    public static final int[] BANNER_H = {BANNER_H_1, BANNER_H_2, BANNER_H_3};

    public static final int HOSPITAL_W_1 = 1260;
    public static final int HOSPITAL_W_2 = 900;
    public static final int HOSPITAL_W_3 = 600;
    public static final int HOSPITAL_H_1 = 420;
    public static final int HOSPITAL_H_2 = 300;
    public static final int HOSPITAL_H_3 = 200;
    public static final int[] HOSPITAL_W = {HOSPITAL_W_1, HOSPITAL_W_2, HOSPITAL_W_3};
    public static final int[] HOSPITAL_H = {HOSPITAL_H_1, HOSPITAL_H_2, HOSPITAL_H_3};

    public static final int HOSPITAL_GUIDE_W_1 = 1280;
    public static final int HOSPITAL_GUIDE_W_2 = 900;
    public static final int HOSPITAL_GUIDE_W_3 = 300;
    public static final int HOSPITAL_GUIDE_H_1 = 1280;
    public static final int HOSPITAL_GUIDE_H_2 = 900;
    public static final int HOSPITAL_GUIDE_H_3 = 300;
    public static final int[] HOSPITAL_GUIDE_W = {HOSPITAL_GUIDE_W_1, HOSPITAL_GUIDE_W_2, HOSPITAL_GUIDE_W_3};
    public static final int[] HOSPITAL_GUIDE_H = {HOSPITAL_GUIDE_H_1, HOSPITAL_GUIDE_H_2, HOSPITAL_GUIDE_H_3};


    public static final int FEEDBACK_W_1 = 1280;
    public static final int FEEDBACK_W_2 = 300;
    public static final int FEEDBACK_H_1 = 0;
    public static final int FEEDBACK_H_2 = 0;
    public static final int[] FEEDBACK_W = {FEEDBACK_W_1, FEEDBACK_W_2};
    public static final int[] FEEDBACK_H = {FEEDBACK_H_1, FEEDBACK_H_2};

    public static String getHeaderUrl(String url, int width) {
        if (TextUtils.isEmpty(url)) return null;

        int minAbs = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < HEAD_W.length; i++) {
            int abs = Math.abs(width - HEAD_W[i]);
            if (minAbs > abs) {
                minAbs = abs;
                index = i;
            }
        }

        return getUrl(url, HEAD_W[index], HEAD_H[index]);
    }

    public static String getFeedbackUrl(String url, int width) {
        if (TextUtils.isEmpty(url)) return null;

        int minAbs = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < FEEDBACK_W.length; i++) {
            int abs = Math.abs(width - FEEDBACK_W[i]);
            if (minAbs > abs) {
                minAbs = abs;
                index = i;
            }
        }

        return getUrl(url, FEEDBACK_W[index], FEEDBACK_H[index]);
    }

    public static String getBannerUrl(String url, int width) {
        if (TextUtils.isEmpty(url)) return null;

        int minAbs = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < BANNER_W.length; i++) {
            int abs = Math.abs(width - BANNER_W[i]);
            if (minAbs > abs) {
                minAbs = abs;
                index = i;
            }
        }

        return getUrl(url, BANNER_W[index], BANNER_H[index]);
    }

    private static String getUrl(String url, int width, int height) {
        StringBuilder sb = new StringBuilder(url);
        String s = sb.append("?width=").append(width).append("&height=").append(height).toString();
        if (AppConstant.IS_DEBUG) {
            Log.d(AppConstant.APPLICATION_ID, "img===========" + s);
        }

        return s;
    }

}
