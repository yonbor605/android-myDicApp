package com.yonbor.mydicapp.activity.app.home.player;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.home.player.RecyclerViewVideoMultiholderAdapter;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import cn.jzvd.JZVideoPlayer;

public class TinyWindowRecyclerViewMultiHolderActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecyclerViewVideoMultiholderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinywindow_recyclerview_multiholder);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("TinyWindowRecyclerViewMultiholder");
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

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewVideoMultiholderAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                JZVideoPlayer.onChildViewAttachedToWindow(view, R.id.videoplayer);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer.onChildViewDetachedFromWindow(view);
            }
        });
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
}
