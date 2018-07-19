package com.yonbor.mydicapp.activity.app.home.toast;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.rxtool.RxToast;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/home/toast/rxToast")
public class RxToastActivity extends BaseActivity {

    @BindView(R.id.button_error_toast)
    Button buttonErrorToast;
    @BindView(R.id.button_success_toast)
    Button buttonSuccessToast;
    @BindView(R.id.button_info_toast)
    Button buttonInfoToast;
    @BindView(R.id.button_warning_toast)
    Button buttonWarningToast;
    @BindView(R.id.button_normal_toast_wo_icon)
    Button buttonNormalToastWoIcon;
    @BindView(R.id.button_normal_toast_w_icon)
    Button buttonNormalToastWIcon;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_toast);
        ButterKnife.bind(this);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("RxToast");
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


    @OnClick({R.id.button_error_toast, R.id.button_success_toast, R.id.button_info_toast, R.id.button_warning_toast, R.id.button_normal_toast_wo_icon, R.id.button_normal_toast_w_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_error_toast:
                RxToast.error(this, "这是一个提示错误的Toast！", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button_success_toast:
                RxToast.success(this, "这是一个提示成功的Toast!", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button_info_toast:
                RxToast.info(this, "这是一个提示信息的Toast.", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button_warning_toast:
                RxToast.warning(this, "这是一个提示警告的Toast.", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button_normal_toast_wo_icon:
                RxToast.normal(this, "这是一个普通的没有ICON的Toast").show();
                break;
            case R.id.button_normal_toast_w_icon:
                Drawable icon = getResources().getDrawable(R.drawable.set);
                RxToast.normal(this, "这是一个普通的包含ICON的Toast", icon).show();
                break;
        }
    }
}
