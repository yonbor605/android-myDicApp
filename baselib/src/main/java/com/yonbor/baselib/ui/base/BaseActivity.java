package com.yonbor.baselib.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yonbor.baselib.R;
import com.yonbor.baselib.base.AppContext;
import com.yonbor.baselib.utils.StringUtil;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.baselib.widget.AutoCardView;
import com.yonbor.baselib.widget.dialog.LoadingDialog;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by Administrator on 2018/7/12.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    LoadingDialog loadingDialog;

    public AppActionBar actionBar;
    public Context baseContext;

    protected RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.baseContext = this;
        loadingDialog = new LoadingDialog();
        rxPermissions = new RxPermissions(this);

        if (!StringUtil.isEmpty(getCloseAction())
                || !StringUtil.isEmpty(getSSOLoginAction())) {
            IntentFilter filter = new IntentFilter();
            if (!StringUtil.isEmpty(getCloseAction())) {
                filter.addAction(getCloseAction());
            }
            if (!StringUtil.isEmpty(getSSOLoginAction())) {
                filter.addAction(getSSOLoginAction());
            }
            if (!StringUtil.isEmpty(getHttpHeaderAction())) {
                filter.addAction(getHttpHeaderAction());
            }
            this.registerReceiver(this.endbroadcastReceiver, filter);
        }
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals("FrameLayout")) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals("LinearLayout")) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals("RelativeLayout")) {
            view = new AutoRelativeLayout(context, attrs);
        }
        if (name.equals("android.support.v7.widget.CardView")) {
            view = new AutoCardView(context, attrs);
        }

        return (View) (view != null ? view : super.onCreateView(name, context, attrs));
    }

    // 写一个广播的内部类，当收到动作时，结束activity
    private BroadcastReceiver endbroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(getCloseAction(), intent.getAction())) {
                finish();
            } else if (TextUtils.equals(getSSOLoginAction(), intent.getAction())) {
                loginDiglog(intent);
            } else if (TextUtils.equals(getHttpHeaderAction(), intent.getAction())) {
                handleHttpHeader(intent);
            }
        }
    };

    public abstract int getActionBarBg();

    /**
     * 关闭页面
     *
     * @return
     */
    public abstract String getCloseAction();

    /**
     * 单点登录
     *
     * @return
     */
    public abstract String getSSOLoginAction();

    public abstract String getHttpHeaderAction();

    public abstract void loginDiglog(Intent intent);

    public abstract void handleHttpHeader(Intent intent);

    public void findActionBar() {
        actionBar = (AppActionBar) findViewById(R.id.actionbar);
        // actionBar.setBackGround(getResources().getColor(getActionBarBg()));
        actionBar.setBackGround(getResources().getColor(getActionBarBg()));
    }

    public void showToast(String text) {
        if (text != null && !text.trim().equals("")) {
            Toast.makeText(AppContext.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public void showToast(@StringRes int resId) {
        Toast.makeText(AppContext.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        if (!StringUtil.isEmpty(getCloseAction())
                || !StringUtil.isEmpty(getSSOLoginAction())) {
            if (null != this.endbroadcastReceiver) {
                this.unregisterReceiver(this.endbroadcastReceiver);
                endbroadcastReceiver = null;
            }
        }

        super.onDestroy();
    }


    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    public void showLoadingDialog(String msg) {
        loadingDialog.build(this).show(msg);
    }

    public void dismissLoadingDialog() {
        loadingDialog.dismiss();
    }

    public boolean isLoadingDialogShowing() {
        return loadingDialog.isShowing();
    }

}
