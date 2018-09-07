package com.yonbor.mydicapp.model.service.healthNews;


import com.yonbor.mydicapp.model.BaseVo;

/**
 * Created by xiey on 2017/5/6.
 */

public class TagVo extends BaseVo {

//    {"tagId":3,"tenantId":"hcn.zhongshan","typeId":2,"typeCode":"healthNewsType",
// "tagCode":"1","tagName":"高血压","isEnable":"1"}

    public int isEnable = 1;//是否可用,0 不可用
    public String tagCode;//标签代码
    public int tagId;//标签id
    public String tenantId;
    public int typeId;
    public String tagName;//标签名称
    public String typeCode;//标签类型代码

    public TagVo(String tagCode, int tagId, String tagName, String tenantId, String typeCode, int typeId) {
        this.tagCode = tagCode;
        this.tagId = tagId;
        this.tagName = tagName;
        this.tenantId = tenantId;
        this.typeCode = typeCode;
        this.typeId = typeId;
    }

    public TagVo() {
    }
}
