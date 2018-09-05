package com.yonbor.mydicapp.model.service;

import com.yonbor.mydicapp.model.BaseVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/4 16:55
 */
public class ArticleListVo extends BaseVo {

    private int curPage;
    private int offset;
    private int pageCount;
    private int size;
    private int total;
    private ArrayList<ArticleVo> datas;


    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<ArticleVo> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ArticleVo> datas) {
        this.datas = datas;
    }
}
