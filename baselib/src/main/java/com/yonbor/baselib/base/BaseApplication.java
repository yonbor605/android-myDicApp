package com.yonbor.baselib.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * APPLICATION
 */
public class BaseApplication extends Application {


    // 分辨率-宽高
    private static int widthPixels;
    private static int heightPixels;

    /**
     * 获取分辨率的宽度
     *
     * @return
     */
    public static int getWidthPixels() {
        if (0 == widthPixels) {
            WindowManager localWindowManager = (WindowManager) AppContext
                    .getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics localDisplayMetrics = new DisplayMetrics();
            localWindowManager.getDefaultDisplay().getMetrics(
                    localDisplayMetrics);
            widthPixels = localDisplayMetrics.widthPixels;
            heightPixels = localDisplayMetrics.heightPixels;
        }
        return widthPixels;
    }

    /**
     * 获取分辨率的高度
     *
     * @return
     */
    public static int getHeightPixels() {
        if (0 == heightPixels) {
            WindowManager localWindowManager = (WindowManager) AppContext
                    .getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics localDisplayMetrics = new DisplayMetrics();
            localWindowManager.getDefaultDisplay().getMetrics(
                    localDisplayMetrics);
            widthPixels = localDisplayMetrics.widthPixels;
            heightPixels = localDisplayMetrics.heightPixels;
        }
        return heightPixels;
    }


    /**
     * 得到分辨率的ga高宽比
     *
     * @return
     */
    public float getWH() {
        int w = getWidthPixels();
        if (w <= 0) {
            return 1;
        }
        int h = getHeightPixels();
        if (h <= 0) {
            return 1;
        }
        return (float) w / h;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //设置autolayout不忽略状态栏高度
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppContext.clear();
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
