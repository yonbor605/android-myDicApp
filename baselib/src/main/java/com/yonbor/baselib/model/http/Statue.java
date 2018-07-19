package com.yonbor.baselib.model.http;

/**
 * 状态编码
 */

public class Statue {

    //网络错误
    public static final int NET_ERROR = 0;
    //返回成功
    public static final int SUCCESS = 1;
    //解析失败parser
    public static final int PARSER_ERROR = 2;
    //失败
    public static final int ERROR = 3;
    //设备登陆(单设备登陆)
    public static final int DEVICEID_ERROR = 4;
    //无权访问，对应code=-6
    public static final int FORBID = -6;

}
