package com.yonbor.baselib.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.yonbor.baselib.R;
import com.yonbor.baselib.base.AppContext;


public class LoadingDialog {
    private Dialog dialog;
    private TextView tvMessage;
    private AVLoadingIndicatorView avi;
    
    private boolean isBack = true;
    
    public LoadingDialog(){
        
    }

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    public LoadingDialog build(final Context context){
        if (dialog == null) {
            dialog = new Dialog(context, R.style.alertDialogTheme);

            dialog.setContentView(R.layout.dialog_loading);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && isBack) {
                        if(context instanceof Activity){
                            dialogInterface.dismiss();
                            ((Activity)context).finish();
                        }
                    }
                    return false;
                }
            });
            avi = (AVLoadingIndicatorView) dialog.findViewById(R.id.avi);
            tvMessage = (TextView) dialog.findViewById(R.id.tv);
        }
        return this;
    }
    

    public void show() {
        show(null);
    }
    public void showL() {
        show(AppContext.getContext().getResources().getString(R.string.loading));
    }

    public void show(String message) {
        if(dialog != null && !dialog.isShowing()) {
            if (TextUtils.isEmpty(message)) {
                tvMessage.setVisibility(View.GONE);
            } else {
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(message);
            }
            avi.smoothToShow();
            dialog.show();
        }
    }

    public void setMassage(String msg) {
        if (dialog != null && dialog.isShowing()) {
            tvMessage.setText(msg);
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            avi.smoothToHide();
        }
    }
    
    public boolean isShowing(){
        if(dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }
    
}
