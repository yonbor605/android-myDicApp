package com.yonbor.mydicapp.activity.app.home.player;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import cn.jzvd.JZVideoPlayerStandard;

public class ApiActivity extends BaseActivity implements View.OnClickListener {

    private JZVideoPlayerStandard mJzVideoPlayerStandard;
    private Button mOrientation, mExtendsNormalActivity;

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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orientation:

                break;
            case R.id.extends_normal_activity:

                break;
        }
    }
}
