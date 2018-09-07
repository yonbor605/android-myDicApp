package com.yonbor.mydicapp.model.service.healthNews;

import android.support.annotation.NonNull;

import com.yonbor.mydicapp.model.BaseVo;


public class HealthyNewsVo extends BaseVo {
    public long id;//资讯ID
    public String title;//资讯标题
    public String source;//来源
    public Long created;//提交时间
    public String listpic;//列表图片
    public String newsdesc;//资讯内容
    public int readCount;//阅读量
    public String category;//类别
    public long collectCount;//收藏数
    public String newsStatus;//状态
    public long praiseCount;//点赞数
    public long shareCount;//分享数
    public String peopleClassifyIds;//人群分类

    public String getReadCount() {
        return getNumString(readCount);
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getCollectCount() {
        return getNumString(collectCount);
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public String getPraiseCount() {
        return getNumString(praiseCount);
    }

    public void setPraiseCount(long praiseCount) {
        this.praiseCount = praiseCount;
    }

    @NonNull
    private String getNumString(long count) {
        if (count > 9999) {
            long a = count / 1000;
            long left = count % 1000;
            if (left > 99) {
                long point = left / 100;
                return a + "." + point + "k";
            }
            return a + "k";
        }
        return count + "";
    }
}
