package com.yonbor.mydicapp.activity.adapter.service.project;

import com.yonbor.baselib.recyclerviewadapter.CommonAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.model.service.project.ProjectVo;

import java.util.List;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/7 14:23
 */
public class ProjectsAdapter extends CommonAdapter<ProjectVo> {

    public ProjectsAdapter(int layoutId, List<ProjectVo> datas) {
        super(R.layout.item_project, null);
    }

    @Override
    protected void convert(ViewHolder holder, ProjectVo projectVo, int position) {



        EffectUtil.addClickEffect(holder.getConvertView());
    }
}
