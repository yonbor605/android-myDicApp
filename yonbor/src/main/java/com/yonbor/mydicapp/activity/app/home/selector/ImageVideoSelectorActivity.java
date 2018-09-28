package com.yonbor.mydicapp.activity.app.home.selector;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.baselib.widget.PictureLay;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/home/selector/imageVideoSelector")
public class ImageVideoSelectorActivity extends BaseActivity {

    @BindView(R.id.zhihu)
    Button zhihu;
    @BindView(R.id.dracula)
    Button dracula;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layImg)
    PictureLay layImg;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_video_selector);
        ButterKnife.bind(this);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("图片&视频选择器");
        actionBar.setBackAction(new AppActionBar.Action() {

            @Override
            public void performAction(View view) {
                back();
            }

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }
        });
    }
}
