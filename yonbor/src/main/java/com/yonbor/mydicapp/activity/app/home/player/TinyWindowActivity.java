
package com.yonbor.mydicapp.activity.app.home.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.home.player.RecyclerViewVideoAdapter;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class TinyWindowActivity extends BaseActivity implements View.OnClickListener {
    private JZVideoPlayerStandard mJzVideoPlayerStandard;
    private Button mBtnTinyWindow, mBtnTinyWindowRecycle, mBtnTinyWindowRecycleMultiHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_window);
        findView();
        setListener();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("TinyWindow");
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

        mJzVideoPlayerStandard = findViewById(R.id.jz_video);
        mBtnTinyWindow = findViewById(R.id.tiny_window);
        mBtnTinyWindowRecycle = findViewById(R.id.auto_tiny_recycleview);
        mBtnTinyWindowRecycleMultiHolder = findViewById(R.id.auto_tiny_recycleview_multiholder);

    }


    private void setListener() {
        mBtnTinyWindow.setOnClickListener(this);
        mBtnTinyWindowRecycle.setOnClickListener(this);
        mBtnTinyWindowRecycleMultiHolder.setOnClickListener(this);

        mJzVideoPlayerStandard.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Glide.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(mJzVideoPlayerStandard.thumbImageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tiny_window:
                mJzVideoPlayerStandard.startWindowTiny();
                break;
            case R.id.auto_tiny_recycleview:
                startActivity(new Intent(TinyWindowActivity.this, TinyWindowRecyclerViewActivity.class));
                break;
            case R.id.auto_tiny_recycleview_multiholder:
                startActivity(new Intent(TinyWindowActivity.this, TinyWindowRecyclerViewMultiHolderActivity.class));
                break;
        }
    }
}
