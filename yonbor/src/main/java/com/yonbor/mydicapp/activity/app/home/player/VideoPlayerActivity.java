package com.yonbor.mydicapp.activity.app.home.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.view.player.MyJZVideoPlayerStandard;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


@Route(path = "/home/player/videoPlayer")
public class VideoPlayerActivity extends BaseActivity implements View.OnClickListener {

    private MyJZVideoPlayerStandard myJZVideoPlayerStandard;
    private Button mApi, mTinyWindow, mDirectFullscreen, mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        findView();
        setListener();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("JiaoZiVideoPlayer");
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

        myJZVideoPlayerStandard = findViewById(R.id.jz_video);
        mApi = findViewById(R.id.api);
        mTinyWindow = findViewById(R.id.tiny_window);
        mDirectFullscreen = findViewById(R.id.direct_play);
        mWebView = findViewById(R.id.webview);
    }

    private void setListener() {
        mApi.setOnClickListener(this);
        mTinyWindow.setOnClickListener(this);
        mDirectFullscreen.setOnClickListener(this);
        mWebView.setOnClickListener(this);

        myJZVideoPlayerStandard.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4",
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(myJZVideoPlayerStandard.thumbImageView);
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
            case R.id.api:
                startActivity(new Intent(VideoPlayerActivity.this, ApiActivity.class));
                break;
            case R.id.tiny_window:
                startActivity(new Intent(VideoPlayerActivity.this, TinyWindowActivity.class));
                break;
            case R.id.direct_play:

                break;
            case R.id.webview:

                break;
        }
    }
}
