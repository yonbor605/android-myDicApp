package com.yonbor.mydicapp.activity.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.baselib.utils.ExitUtil;
import com.yonbor.baselib.widget.StatusBarCompat;
import com.yonbor.baselib.widget.loading.LoadViewHelper;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.app.AppApplication;
import com.yonbor.mydicapp.app.AppConstant;


/**
 * Activity 基类
 */
public abstract class BaseActivity extends com.yonbor.baselib.ui.base.BaseActivity {

    public AppApplication application;
    public LoadViewHelper viewHelper;
    public BaseActivity baseActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        this.baseActivity = this;
        this.application = (AppApplication) getApplication();
        rxPermissions.setLogging(true);

    }

    public String getSSOLoginAction() {
        return AppConstant.Logout_ACTION;
    }


    @Override
    public String getHttpHeaderAction() {
        return AppConstant.Http_Header_ACTION;
    }

    @Override
    public void handleHttpHeader(Intent data) {
//        if (data == null) return;
//        String code = data.getStringExtra("code");
//        if ("503".equals(code)) {
//            Intent intent = new Intent(getSSOLoginAction());
//            intent.putExtra("des", "您的账号在其他设备上登陆,请重新登录!");
//            intent.putExtra("title", "提示");
//            sendBroadcast(intent);
//        } else if ("409".equals(code)) {
//            //提示令牌失效
//            Intent intent = new Intent(getSSOLoginAction());
//            intent.putExtra("des", "您的账号登录信息已过期,请重新登录!");
//            intent.putExtra("title", "提示");
//            sendBroadcast(intent);
//        } else if ("404".equals(code)) {
//            //提示令牌失效
//            Intent intent = new Intent(getSSOLoginAction());
//            intent.putExtra("des", "您的账号登录信息已过期,请重新登录!");
//            intent.putExtra("title", "提示");
//            sendBroadcast(intent);
//
//        } else if ("504".equals(code)) {
//            //加密失败
//            showToast(R.string.request_fail);
//        }
    }

    // 单点登录
    @Override
    public void loginDiglog(Intent intent) {
//        PushInfo info = new PushInfo();
//        info.description = intent.getStringExtra("des");
//        info.title = intent.getStringExtra("title");
//        logout(info);
    }

//    void logout(PushInfo pushInfo) {
//        if (pushInfo == null) return;
//        showDialog(pushInfo.title, pushInfo.description, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                application.clearInfo();
//                sendBroadcast(new Intent(Constants.CLOSE_ACTION));
//                Intent intent = new Intent(baseContext,
//                        LoginActivity.class);
//                startActivity(intent);
//            }
//        }, false);
//
//    }

    public abstract void findView();


    public int getActionBarBg() {
        return R.color.actionbar_bg;
    }

    public String getCloseAction() {
        return AppConstant.CLOSE_ACTION;
    }

    public void back() {
        finish();
        hideKeyboard();
    }


    /**
     * 提示框（统一风格）
     */
    private Dialog alertDialog;

    public void showDialog(String title, String content, String confirmStr, String cancelstr, final View.OnClickListener confirmListener, final View.OnClickListener cancellistener) {
        showDialog(baseContext, title, content, null, confirmStr, cancelstr, confirmListener, cancellistener, false);
    }

    public void showDialog(String title, String content, final View.OnClickListener confirmListener, final View.OnClickListener cancellistener) {
        showDialog(baseContext, title, content, null, "确认", "取消", confirmListener, cancellistener, false);
    }

    public void showDialog(String title, String content, final View.OnClickListener confirmListener) {
        showDialog(baseContext, title, content, null, "确认", "取消", confirmListener, null, false);
    }

    public void showDialog(String title, String content, final View.OnClickListener confirmListener, boolean isShowCancel) {
        showDialog(baseContext, title, content, null, "确认", isShowCancel ? "取消" : "", confirmListener, null, false);
    }

    public void showDialog(String title, String content, String info, final View.OnClickListener confirmListener, boolean isShowCancel) {
        showDialog(baseContext, title, content, info, "确认", isShowCancel ? "取消" : "", confirmListener, null, false);
    }

    public void showDialog(Context baseContext, String title, String content, String info,
                           String confirmStr, String cancelstr,
                           final View.OnClickListener confirmListener,
                           final View.OnClickListener cancellistener,
                           boolean isCancelable) {
        if (alertDialog != null && alertDialog.isShowing()) return;
        alertDialog = new Dialog(baseContext, R.style.alertDialogTheme);
        View viewDialog = LayoutInflater.from(baseContext).inflate(R.layout.dialog_alert, null);
        // 设置对话框的宽高
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                AppApplication.getWidthPixels() * 85 / 100, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.setCancelable(isCancelable);
        alertDialog.setCanceledOnTouchOutside(isCancelable);
        alertDialog.setContentView(viewDialog, layoutParams);

        //确定按钮
        TextView tv_confirm = (TextView) viewDialog.findViewById(R.id.tv_confirm);
        //取消按钮，默认隐藏
        TextView tv_cancel = (TextView) viewDialog.findViewById(R.id.tv_cancel);
        EffectUtil.addClickEffect(tv_confirm);
        EffectUtil.addClickEffect(tv_cancel);

        if (!TextUtils.isEmpty(confirmStr) && !TextUtils.isEmpty(cancelstr)) {
            viewDialog.findViewById(R.id.tv_divider).setVisibility(View.VISIBLE);
//			tv_confirm.setBackgroundResource(R.drawable.item_selected_white);
//			tv_cancel.setBackgroundResource(R.drawable.item_selected_white);
        }
        if (!TextUtils.isEmpty(confirmStr)) {
            tv_confirm.setText(confirmStr);
        }
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                    dismissAlertDialog();
                } else {
                    dismissAlertDialog();
                }
            }
        });

        if (!TextUtils.isEmpty(cancelstr)) {
            tv_cancel.setVisibility(View.VISIBLE);
            tv_cancel.setText(cancelstr);
        } else {
            tv_cancel.setVisibility(View.GONE);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancellistener != null) {
                    cancellistener.onClick(v);
                    dismissAlertDialog();
                } else {
                    dismissAlertDialog();
                }
            }
        });

        TextView tv_content = (TextView) alertDialog.findViewById(R.id.tv_content);
        //标题默认隐藏
        if (!TextUtils.isEmpty(title)) {
            TextView tv_title = (TextView) alertDialog.findViewById(R.id.tv_title);
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(info)) {
            TextView tvInfo = (TextView) alertDialog.findViewById(R.id.tv_info);
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setText(info);
        }
        if (!TextUtils.isEmpty(content)) {
            tv_content.setText(content);
        }
        alertDialog.show();
    }

    public void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        dismissAlertDialog();
        ExitUtil.clear();
        super.onDestroy();
    }

    public int getDialogWidth() {
        return AppApplication.getWidthPixels() * 75 / 100;
    }

    public void showErrorView() {
        showErrorView("加载失败，点击重试");
    }


    public void showErrorView(String error) {
        if (null != viewHelper) {
            viewHelper.showError(error);
        }
    }

    public void showEmptyView() {
        if (null != viewHelper) {
            viewHelper.showEmpty();
        }
    }

    public void showEmptyView(View view) {
        if (null != viewHelper) {
            viewHelper.showEmpty(view);
        }
    }

    public void showEmptyView(int layId) {
        if (null != viewHelper) {
            viewHelper.showEmpty(layId);
        }
    }

    public void showLoadingView() {
        if (null != viewHelper) {
            viewHelper.showLoading();
        }
    }

    public void restoreView() {
        if (null != viewHelper) {
            viewHelper.restore();
        }
    }


    public void hideKeyboard() {
        if (null != getCurrentFocus()
                && null != getCurrentFocus().getWindowToken()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
    }

    public void showKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(editText.getWindowToken(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isLoadingDialogShowing()) {
                finish();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 默认着色状态栏
        SetStatusBarColor();
    }


    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.actionbar_bg1));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


}
