package com.yonbor.mydicapp.activity.app.home.rxjava2;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

@Route(path = "/home/rxjava2/rxjava2Sample")
public class Rxjava2SampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2_sample);
        findView();
    }


    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("Rxjava2使用示例");
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


    public void startOperatorsActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/operators").navigation();
    }
}
