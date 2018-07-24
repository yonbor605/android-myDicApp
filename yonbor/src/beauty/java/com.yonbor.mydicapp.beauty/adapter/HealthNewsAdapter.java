package com.yonbor.mydicapp.beauty.adapter;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yonbor.baselib.recyclerviewadapter.CommonAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.baselib.utils.ImageUtil;
import com.yonbor.baselib.utils.StringUtil;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.app.AppConstant;
import com.yonbor.mydicapp.model.service.HealthyNewsVo;
import com.yonbor.mydicapp.utils.DateUtil;
import com.yonbor.mydicapp.utils.ImageSizeUtil;
import com.yonbor.mydicapp.utils.ImageUrlUtil;

/**
 * Created by Administrator on 2017/9/13.
 * 健康资讯
 */

public class HealthNewsAdapter extends CommonAdapter<HealthyNewsVo> {

    public HealthNewsAdapter() {
        super(R.layout.item_healthy_news, null);
    }

    @Override
    protected void convert(final ViewHolder holder, final HealthyNewsVo item, final int position) {

        TextView title = holder.getView(R.id.title);
        TextView date = holder.getView(R.id.date);
        TextView author = holder.getView(R.id.author);
        TextView views = holder.getView(R.id.views);
        SimpleDraweeView img = holder.getView(R.id.img);

        title.setText(StringUtil.getTextLimit(item.title, 10));

        if(ImageUrlUtil.getUrl(AppConstant.HTTP_IMG_URL, item.listpic) != null) {
            img.setVisibility(View.VISIBLE);
            ImageUtil.showNetWorkImage(img,
                    ImageSizeUtil.getHeaderUrl(
                            ImageUrlUtil.getUrl(AppConstant.HTTP_IMG_URL, item.listpic),
                            img.getLayoutParams().width), R.drawable.pic_default);
        }else {
            img.setVisibility(View.INVISIBLE);
        }
        title.setText(item.title);
        views.setText("阅读量 " + item.getReadCount());
        author.setText(item.source);
        if (item.created != null) {
            date.setVisibility(View.VISIBLE);
            date.setText(DateUtil.getDateTime("yyyy-MM-dd", item.created));
        }else{
            date.setVisibility(View.INVISIBLE);
        }


        EffectUtil.addClickEffect(holder.getConvertView());
    }


}
