package com.yonbor.baselib.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yonbor.baselib.base.AppContext;

import java.util.Map;

/**
 * yonbor605
 */
public class SharedPreferencesUtil {

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferencesUtil mSharedPreferencesUtil;
    private static SharedPreferences.Editor mEditor;

    public SharedPreferencesUtil(Context context) {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = this.mSharedPreferences.edit();
    }


    /**
     * 单例模式
     *
     * @return
     */
    public static SharedPreferencesUtil getInstance() {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(AppContext.getContext());
        }
        return mSharedPreferencesUtil;
    }


    public void setStringData(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public String getStringData(String key) {
        return this.mSharedPreferences.getString(key, null);
    }

    public String getStringData(String key, String defaultValue) {
        return this.mSharedPreferences.getString(key, defaultValue);
    }


    public void setLongData(String key, long value) {
        mEditor.putLong(key, value).commit();
    }

    public long getLongData(String key) {
        return this.mSharedPreferences.getLong(key, 0);
    }

    public long getLongData(String key, long defaultValue) {
        return this.mSharedPreferences.getLong(key, defaultValue);
    }


    public void setBooleanData(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    public boolean getBooleanData(String key) {
        return this.mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBooleanData(String key, boolean defaultValue) {
        return this.mSharedPreferences.getBoolean(key, defaultValue);
    }


    public void setIntData(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public int getIntData(String key) {
        return this.mSharedPreferences.getInt(key, 0);
    }

    public int getIntData(String key, int defaultValue) {
        return this.mSharedPreferences.getInt(key, defaultValue);
    }


    public void setFloatData(String key, float value) {
        mEditor.putFloat(key, value).commit();
    }

    public float getFloatData(String key) {
        return this.mSharedPreferences.getFloat(key, 0);
    }

    public float getFloatData(String key, float defaultValue) {
        return this.mSharedPreferences.getFloat(key, defaultValue);
    }


    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return this.mSharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return this.mSharedPreferences.getAll();
    }

}
