package com.yonbor.mydicapp.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.app.GlideApp;
import com.yonbor.mydicapp.model.home.banner.BannerVo;

/**
 * 网络图片加载例子
 */
public class NetworkImageHolderView extends Holder<BannerVo> {
    private ImageView imageView;
    private Context context;

    public NetworkImageHolderView(View itemView) {
        super(itemView);
    }


    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.ivBanner);
        context = itemView.getContext();
    }


    @Override
    public void updateUI(BannerVo data) {
        GlideApp.with(context)
                .load(data.getImagePath())
                .placeholder(R.drawable.banner_default)
                .into(imageView);
    }
}
