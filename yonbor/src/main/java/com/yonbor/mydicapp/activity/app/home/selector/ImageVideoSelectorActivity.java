package com.yonbor.mydicapp.activity.app.home.selector;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.ButterKnife;

@Route(path = "/home/selector/imageVideoSelector")
public class ImageVideoSelectorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_video_selector);
        ButterKnife.bind(this);
        findView();
    }

    @Override
    public void findView() {

    }
}
