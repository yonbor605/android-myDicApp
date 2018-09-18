package com.yonbor.mydicapp.model.search;

import org.litepal.crud.LitePalSupport;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/18 14:56
 */
public class SearchHistoryVo extends LitePalSupport {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
