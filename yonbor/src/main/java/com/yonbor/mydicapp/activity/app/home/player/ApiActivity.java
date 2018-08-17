package com.yonbor.mydicapp.activity.app.home.player;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.app.AppApplication;
import com.yonbor.mydicapp.app.ConstantsHttp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ApiActivity extends BaseActivity implements View.OnClickListener {

    private JZVideoPlayerStandard mJzVideoPlayerStandard;
    private Button mOrientation, mExtendsNormalActivity;
    private SensorManager mSensorManager;
    JZVideoPlayer.JZAutoFullscreenListener mSensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        findView();
        setListener();

    }


    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("Api");
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
        mOrientation = findViewById(R.id.orientation);
        mExtendsNormalActivity = findViewById(R.id.extends_normal_activity);
    }

    private void setListener() {
        mOrientation.setOnClickListener(this);
        mExtendsNormalActivity.setOnClickListener(this);
        LinkedHashMap map = new LinkedHashMap();
        String proxyUrl = AppApplication.getProxy(this).getProxyUrl(ConstantsHttp.videoUrls[0][9]);
        map.put("高清", proxyUrl);
        map.put("标清", ConstantsHttp.videoUrls[0][6]);
        map.put("普清", ConstantsHttp.videoUrlList[0]);
        Object[] objects = new Object[3];
        objects[0] = map;
        objects[1] = true; // looping
        objects[2] = new HashMap<>();
        ((HashMap) objects[2]).put("key", "value"); // header
        mJzVideoPlayerStandard.setUp(objects, 2, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子不信");
        Glide.with(this).load(ConstantsHttp.videoThumbList[0]).into(mJzVideoPlayerStandard.thumbImageView);
        mJzVideoPlayerStandard.seekToInAdvance = 20000;

        //JZVideoPlayer.SAVE_PROGRESS = false;

        /** Play video in local path, eg:record by system camera **/
//        cpAssertVideoToLocalPath();
//        mJzVideoPlayerStandard.setUp(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/local_video.mp4"
//                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子不信");
//        Glide.with(this).load(ConstantsHttp.videoThumbs[0][1]).into(mJzVideoPlayerStandard.thumbImageView);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new JZVideoPlayer.JZAutoFullscreenListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orientation:
                startActivity(new Intent(ApiActivity.this, ApiOrientationActivity.class));
                break;
            case R.id.extends_normal_activity:
                startActivity(new Intent(ApiActivity.this, ApiExtendsNormalActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        // home back
        JZVideoPlayer.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        JZVideoPlayer.clearSavedProgress(this, null);
        // home back
        JZVideoPlayer.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    public void cpAssertVideoToLocalPath() {
        try {
            InputStream myInput;
            OutputStream myOutput = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/local_video.mp4");
            myInput = this.getAssets().open("local_video.mp4");
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }

            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
