package com.yonbor.mydicapp.activity.app.home.player;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.app.ConstantsHttp;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ApiOrientationActivity extends BaseActivity {

    private JZVideoPlayerStandard mJzVideoPlayerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_orientation);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("Orientation");
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
        mJzVideoPlayerStandard.setUp(ConstantsHttp.videoUrlList[0], JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子不信");
        Glide.with(this).load(ConstantsHttp.videoThumbList[0]).into(mJzVideoPlayerStandard.thumbImageView);

        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

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

        // Change these two variables back
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }
}
