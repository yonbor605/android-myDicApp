package com.yonbor.mydicapp.activity.app.home.order;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.ButterKnife;

@Route(path = "/home/order/takeoutOrder")
public class TakeoutOrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeout_order);
        ButterKnife.bind(this);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("外卖点单");
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
