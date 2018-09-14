package com.yonbor.mydicapp.activity.adapter.service.article;

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
import com.yonbor.mydicapp.model.service.article.ArticleVo;

/**
 * 文章列表
 */
public class ArticlesAdapter extends CommonAdapter<ArticleVo> {

    public ArticlesAdapter() {
        super(R.layout.item_article, null);
    }

    @Override
    protected void convert(final ViewHolder holder, final ArticleVo item, final int position) {

        TextView tv_fresh = holder.getView(R.id.tv_article_fresh);
        TextView tv_author = holder.getView(R.id.tv_article_author);
        TextView tv_date = holder.getView(R.id.tv_article_date);
        ImageView iv_thumbnail = holder.getView(R.id.iv_article_thumbnail);
        TextView tv_title = holder.getView(R.id.tv_article_title);
        TextView tv_chapterName = holder.getView(R.id.tv_article_chapterName);


        if (Build.VERSION.SDK_INT >= 24)
            tv_title.setText(Html.fromHtml(item.getTitle(),Html.FROM_HTML_MODE_COMPACT));
        else
            tv_title.setText(Html.fromHtml(item.getTitle()));

        tv_author.setText(item.getAuthor());
        tv_date.setText(item.getNiceDate());



        if (!item.getChapterName().isEmpty()) {
            tv_chapterName.setText(item.getChapterName());
            tv_chapterName.setVisibility(View.VISIBLE);
        } else {
            tv_chapterName.setVisibility(View.INVISIBLE);
        }

        if (!item.getEnvelopePic().isEmpty()) {
            iv_thumbnail.setVisibility(View.VISIBLE);
            GlideApp.with(holder.getContext())
                    .load(item.getEnvelopePic())
                    .placeholder(R.drawable.pic_default)
                    .into(iv_thumbnail);
        } else {
            iv_thumbnail.setVisibility(View.GONE);
        }

        if (item.isFresh()) {
            tv_fresh.setVisibility(View.VISIBLE);
        } else {
            tv_fresh.setVisibility(View.GONE);
        }





        EffectUtil.addClickEffect(holder.getConvertView());
    }


}
