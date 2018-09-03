package com.yonbor.mydicapp.net.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.yonbor.baselib.log.AppLogger;
import com.yonbor.baselib.model.http.Statue;
import com.yonbor.mydicapp.model.WanAndroidVo;


/**
 * WanAndroid接口解析类
 * errorCode = 0 代表执行成功
 */
public class Parser2 {
    final static String ERROR_CODE = "errorCode";
    final static String DATA = "data";
    final static String ERROR_MSG = "errorMsg";

    private static final int LIST = 1;
    private static final int OBJECT = 2;
    private static final int BASE = 3;


    private Parser2() {

    }

    private static class SingletonHolder {
        private final static Parser2 INSTANCE = new Parser2();
    }

    public static Parser2 getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public WanAndroidVo parserData(String json, Class clazz) {
        return parserSpData(json, clazz, DATA, true);
    }


    private int getDataType(String s) {
        if (s.startsWith("{")) {
            return OBJECT;
        }
        if (s.startsWith("[")) {
            return LIST;
        }
        return BASE;
    }

    /**
     * @param json
     * @param clazz
     * @param dataId   data节点（接口数据节点不统一(┬＿┬)）
     * @param needCode 是否需要code判断（有些接口没有code(┬＿┬)）
     * @return
     */
    public WanAndroidVo parserSpData(String json, Class clazz, String dataId, boolean needCode) {
        if (json != null) {
            Logger.json(json);
        } else {
            AppLogger.e(json);
        }
        WanAndroidVo mList = new WanAndroidVo();
        if (null != json) {
            try {
                mList.json = json;

                JSONObject ob = JSON.parseObject(json);
                int code = getCode(ob);
                mList.errorCode = code;
                if (ob.containsKey(ERROR_MSG)) {
                    mList.errorMsg = ob.getString(ERROR_MSG);
                }
                if (code == 0 || !needCode) {
                    mList.statue = Statue.SUCCESS;
                    if (ob.containsKey(dataId)) {
                        int type = getDataType(ob.getString(dataId));
                        switch (type) {
                            case LIST:
                                mList.data = JSON.parseArray(ob.getString(dataId), clazz);
                                break;
                            case OBJECT:
                                mList.data = JSON.parseObject(ob.getString(dataId), clazz);
                                break;
                            case BASE:
                                mList.data = ob.get(dataId);
                                break;
                        }

                        return mList;
                    } else {
                        if (TextUtils.isEmpty(mList.errorMsg)) {
                            mList.errorMsg = "数据为空";
                        }
                        return mList;
                    }
                } else {
                    mList.statue = Statue.ERROR;
                    if (ob.containsKey(ERROR_MSG)) {
                        mList.errorMsg = ob.getString(ERROR_MSG);
                    }
                    return mList;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mList.statue = Statue.PARSER_ERROR;
                return mList;
            }
        }
        return mList;
    }


    private int getCode(JSONObject ob) {
        if (null != ob) {
            if (ob.containsKey(ERROR_CODE))
                return ob.getIntValue(ERROR_CODE);
            return -88;
        } else {
            return -88;
        }
    }


}
