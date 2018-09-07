package com.yonbor.mydicapp.model.service.project;

import com.yonbor.mydicapp.model.BaseVo;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/7 14:42
 */
public class ProjectListVo extends BaseVo {


    private int curPage;
    private int offset;
    private int pageCount;
    private int size;
    private int total;
    private ArrayList<ProjectVo> datas;


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

    public ArrayList<ProjectVo> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<ProjectVo> datas) {
        this.datas = datas;
    }
}
