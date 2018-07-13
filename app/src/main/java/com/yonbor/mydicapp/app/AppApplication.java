package com.yonbor.mydicapp.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yonbor.baselib.base.BaseApplication;
import com.yonbor.baselib.base.AppContext;
import com.yonbor.mydicapp.utils.CommonUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

public class AppApplication extends BaseApplication {

    public static AppApplication appApplication;
    /**
     * 维护Activity的List
     */
    private static List<Activity> mActivities = Collections.synchronizedList(new LinkedList<Activity>());

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        /**检测当前进程名称是否为应用包名，否则return （像百度地图等sdk需要在单独的进程中执行，会多次执行Application的onCreate()方法，所以为了只初始化一次应用配置，作此判断）*/
        if (!CommonUtil.getCurProcessName(this).equals(getPackageName())) {
            return;
        }
        /**初始化一些应用配置*/
        init();


    }

    private void init() {
        /** 初始化ARouter*/
        if (AppConstant.IS_DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application

        /**初始化base url*/
        AppContext.initialize(getApplicationContext(), AppConstant.BASE_URL);
        /**注册ActivityListener*/
        registerActivityListener();
        /**crash异常捕获*/
        if (AppConstant.IS_DEBUG) {
            CustomActivityOnCrash.install(this);
        }

        /**初始化Logger */
        if (AppConstant.IS_LOG) {
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                    .methodCount(2)         // (Optional) How many method line to show. Default 2
                    .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                    .tag("Logger")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        }


        /**初始化LeakCanary */
//        if (AppConstant.IS_DEBUG) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
//        }
    }

    /**
     * 注册ActivityListener
     */
    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    if (null == mActivities) {
                        return;
                    }
                    mActivities.add(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activity && mActivities.isEmpty()) {
                        return;
                    }
                    if (mActivities.contains(activity)) {
                        mActivities.remove(activity);
                    }
                }
            });
        }
    }

    public static AppApplication getApplication() {
        return appApplication;
    }


    public static void finishAllActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

    /**
     * 获取当前Activity
     */
    public static Activity getCurrentActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return null;
        }
        Activity activity = mActivities.get(mActivities.size() - 1);
        return activity;
    }
}
