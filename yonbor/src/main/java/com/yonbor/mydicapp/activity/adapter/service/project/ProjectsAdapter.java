package com.yonbor.mydicapp.activity.adapter.service.project;

import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonbor.baselib.recyclerviewadapter.CommonAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.app.GlideApp;
import com.yonbor.mydicapp.model.service.project.ProjectVo;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/9/7 14:23
 */
public class ProjectsAdapter extends CommonAdapter<ProjectVo> {

    public ProjectsAdapter() {
        super(R.layout.item_project, null);
    }

    @Override
    protected void convert(final ViewHolder holder, final ProjectVo item, final int position) {

        ImageView iv_project_pic = holder.getView(R.id.iv_project_pic);
        TextView tv_project_title = holder.getView(R.id.tv_project_title);
        TextView tv_project_desc = holder.getView(R.id.tv_project_desc);
        TextView tv_project_author = holder.getView(R.id.tv_project_author);
        TextView tv_project_time = holder.getView(R.id.tv_project_time);
        ImageView iv_project_like = holder.getView(R.id.iv_project_like);

        if (Build.VERSION.SDK_INT >= 24) {
            tv_project_title.setText(Html.fromHtml(item.getTitle(), Html.FROM_HTML_MODE_COMPACT));
            tv_project_desc.setText(Html.fromHtml(item.getDesc(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_project_title.setText(Html.fromHtml(item.getTitle()));
            tv_project_desc.setText(Html.fromHtml(item.getDesc()));
        }

        tv_project_time.setText(item.getNiceDate());
        tv_project_author.setText(item.getAuthor());

        if (item.isCollect())
            iv_project_like.setImageResource(R.drawable.ic_like);
        else
            iv_project_like.setImageResource(R.drawable.ic_like_not);

        GlideApp.with(holder.getContext())
                .load(item.getEnvelopePic())
                .placeholder(R.drawable.pic_default)
                .error(R.drawable.pic_default)
                .into(iv_project_pic);


        EffectUtil.addClickEffect(iv_project_like);
        iv_project_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemViewClick(v, holder, item, position, -1);
            }
        });


        EffectUtil.addClickEffect(holder.getConvertView());
    }
}
