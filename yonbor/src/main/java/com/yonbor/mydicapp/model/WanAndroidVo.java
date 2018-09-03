package com.yonbor.mydicapp.model;

import com.yonbor.baselib.model.http.Statue;

import java.util.Collection;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/3 10:49
 */
public class WanAndroidVo<T> {


    public T data;

    public int errorCode;

    public String errorMsg;


    public String json;//json数据
    // 状态值
    public int statue = Statue.ERROR;

    public boolean isSuccess() {
        return statue == Statue.SUCCESS;
    }

    public boolean isError() {
        return statue == Statue.ERROR;
    }

    public boolean isEmpty() {
        if (data instanceof Collection) {
            return data == null || ((Collection) data).isEmpty();
        }
        return data == null;
    }

    public String getToast() {
        switch (statue) {
            case Statue.NET_ERROR:
                return "网络加载失败";
            case Statue.ERROR:
                return (null != errorMsg && errorMsg.length() > 0) ? errorMsg : "请求失败";
            case Statue.PARSER_ERROR:
                return "解析失败";
            default:
                return "";
        }
    }


}
