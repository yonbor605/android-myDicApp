package com.yonbor.mydicapp.model.home;

import com.yonbor.mydicapp.model.BaseVo;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/7/17 11:05
 */
public class HomeItemVo extends BaseVo {

    private String title;
    private int icon;
    private Class activity;
    private String actPath;

    public HomeItemVo(String title, int icon, Class activity, String actPath) {
        this.title = title;
        this.icon = icon;
        this.activity = activity;
        this.actPath = actPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public String getActPath() {
        return actPath;
    }

    public void setActPath(String actPath) {
        this.actPath = actPath;
    }
}
