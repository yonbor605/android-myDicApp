package com.yonbor.mydicapp.activity.app.home.player;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.app.ConstantsHttp;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ApiExtendsNormalActivity extends BaseActivity {

    private JZVideoPlayerStandard mJzVideoPlayerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_extends_normal);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("ExTendsNormal");
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

        mJzVideoPlayerStandard = findViewById(R.id.videoplayer);
        mJzVideoPlayerStandard.setUp(ConstantsHttp.videoUrlList[0], JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子不信");
        Glide.with(this).load(ConstantsHttp.videoThumbList[0]).into(mJzVideoPlayerStandard.thumbImageView);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
