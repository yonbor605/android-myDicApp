package com.yonbor.baselib.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yonbor.baselib.base.AppContext;


public class SharedPreferencesUtil {

    private static SharedPreferences mPrefs;
    private static SharedPreferencesUtil mSharedPreferencesUtil;

    public SharedPreferencesUtil(Context context) {
        this.mPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
    }


    // 单例模式
    public static SharedPreferencesUtil getInstance() {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(AppContext.getContext());
        }
        return mSharedPreferencesUtil;
    }


    public void setStringData(String key, String value) {
        this.mPrefs.edit().putString(key, value).commit();
    }

    public String getStringData(String key) {
        return this.mPrefs.getString(key, null);
    }


    public String getStringData(String key, String defaultValue) {
        return this.mPrefs.getString(key, defaultValue);
    }


    public void setLongData(String key, long value) {
        this.mPrefs.edit().putLong(key, value).commit();
    }

    public long getLongData(String key) {
        return this.mPrefs.getLong(key, 0);
    }

    public void setBooleanData(String key, boolean value) {
        this.mPrefs.edit().putBoolean(key, value).commit();
    }

    public boolean getBooleanData(String key) {
        return this.mPrefs.getBoolean(key, false);
    }

    public boolean getBooleanData(String key, boolean flag) {
        return this.mPrefs.getBoolean(key, flag);
    }

}
