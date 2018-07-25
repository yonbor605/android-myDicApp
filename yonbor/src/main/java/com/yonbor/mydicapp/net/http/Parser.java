package com.yonbor.mydicapp.net.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.yonbor.baselib.log.AppLogger;
import com.yonbor.baselib.model.http.ResultModel;
import com.yonbor.baselib.model.http.Statue;


/**
 * 解析
 * Created by Administrator on 2016/10/24.
 */
public class Parser {
    final static String CODE = "code";
    final static String DATA = "body";
    final static String MSG = "msg";
    final static String PRO = "properties";

    private static final int LIST = 1;
    private static final int OBJECT = 2;
    private static final int BASE = 3;


    private Parser() {

    }

    private static class SingletonHolder {
        private final static Parser INSTANCE = new Parser();
    }

    public static Parser getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public ResultModel parserData(String json, Class clazz) {
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
    public ResultModel parserSpData(String json, Class clazz, String dataId, boolean needCode) {
        if (json != null) {
            Logger.json(json);
        } else {
            AppLogger.e(json);
        }
        ResultModel mList = new ResultModel();
        if (null != json) {
            try {
                mList.json = json;

                JSONObject ob = JSON.parseObject(json);
                ResultModel fList = null;
                int code = getCode(ob);
                mList.setCode(code);
                if (ob.containsKey(MSG)) {
                    mList.message = ob.getString(MSG);
                }
                fList = doFilter(code, ob);
                if (null != fList) {
                    fList.setCode(code);
                    return fList;
                }
                if (code == 200 || !needCode) {
                    mList.statue = Statue.SUCCESS;
                    if (ob.containsKey(PRO)) {
                        mList.pro = ob.getString(PRO);
                    }
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
                        if (TextUtils.isEmpty(mList.message)) {
                            mList.message = "数据为空";
                        }
                        return mList;
                    }
                } else {
                    mList.statue = Statue.ERROR;
                    if (ob.containsKey(MSG)) {
                        mList.message = ob.getString(MSG);
                    }
                    return mList;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mList.statue = Statue.PARSER_ERROR;
//                mList.data = data;
                return mList;
            }
        }
        return mList;
    }


    private int getCode(JSONObject ob) {
        if (null != ob) {
            if (ob.containsKey(CODE))
                return ob.getIntValue(CODE);
            return -88;
        } else {
            return -88;
        }
    }

    private ResultModel doFilter(int code, JSONObject ob) {
        ResultModel mList = null;
        if (code == -6) {
            mList = new ResultModel();
            mList.statue = Statue.FORBID;
            if (ob.containsKey(MSG)) {
                mList.message = ob.getString(MSG);
            }
            return mList;
        }
//        if (code == 403) {
//            PushInfo info = new PushInfo();
//            info.description = "您的账号在其他设备上登陆,请重新登录!";
//            info.title = "提示";
//            Intent logoutIntent = new Intent(Constants.Logout_ACTION);
//            logoutIntent.putExtra("pushInfo", info);
//            AppContext.getContext().sendBroadcast(logoutIntent);
//
//            mList = new ResultModel();
//            mList.statue = Statue.DEVICEID_ERROR;
//            return mList;
//        }
//        if (code == -501) {
//            PushInfo info = new PushInfo();
//            info.description = "账号验证失败，请重新登录!";
//            info.title = "提示";
//            info.login = 1;
//            Intent logoutIntent = new Intent(Constants.Logout_ACTION);
//            logoutIntent.putExtra("pushInfo", info);
//            AppContext.getContext().sendBroadcast(logoutIntent);
//
//            mList = new ResultModel();
//            mList.statue = Statue.DEVICEID_ERROR;
//            return mList;
//        }
        return mList;
    }
}
