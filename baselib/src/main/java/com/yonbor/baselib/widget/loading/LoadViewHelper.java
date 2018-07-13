package com.yonbor.baselib.widget.loading;


import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.yonbor.baselib.R;


/**
 * 自定义要切换的布局，通过IVaryViewHelper实现真正的切换<br>
 * 使用者可以根据自己的需求，使用自己定义的布局样式
 */
public class LoadViewHelper {

    private IVaryViewHelper helper;
    OnClickListener clickListener;

    public LoadViewHelper(View view) {
        this(new VaryViewHelper(view));
    }

    public LoadViewHelper(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }
    
    public void showError(String errorText){
        showError(errorText, "重试");
    }

    public void showError(String errorText, String errorButton) {
        View layout = helper.inflate(R.layout.view_load_view_helper_error);
        Button button = (Button) layout.findViewById(R.id.buttonError);
        TextView tvMsg = (TextView) layout.findViewById(R.id.textViewMessage);
        if(TextUtils.isEmpty(errorText)){
            tvMsg.setVisibility(View.INVISIBLE);
        }else{
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(errorText);
        }
        if(TextUtils.isEmpty(errorButton)){
            button.setVisibility(View.INVISIBLE);
        }else{
            button.setVisibility(View.VISIBLE);
            button.setText(errorButton);
        }
        if (null != clickListener) {
            button.setOnClickListener(clickListener);
        }
        
        helper.showLayout(layout);
    }
    public void showError(View view){
        if(view != null)
            helper.showLayout(view);
    }
    public void showError(int layId){
        View layout = helper.inflate(layId);
        helper.showLayout(layout);
    }
    

    public void showEmpty() {
        showEmpty("没有内容");
    }
    public void showEmpty(String emptyText) {
        View layout = helper.inflate(R.layout.view_load_view_helper_empty);
        TextView emptyTextView= (TextView) layout.findViewById(R.id.textViewMessage);
        if (emptyTextView!=null&&!TextUtils.isEmpty(emptyText)){
            emptyTextView.setText(emptyText);
        }
        helper.showLayout(layout);
    }
    
    public void showEmpty(View view){
        if(view != null)
            helper.showLayout(view);
    }
    public void showEmpty(int layId){
        View layout = helper.inflate(layId);
        helper.showLayout(layout);
    }


    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.view_load_view_helper_loading);
        View loadingAnimationView = layout.findViewById(R.id.imageViewLoading);
        TextView tvMsg = (TextView) layout.findViewById(R.id.textViewMessage);
        if(!TextUtils.isEmpty(msg)){
            tvMsg.setText(msg);
        }
        loadingAnimationView.startAnimation(getRotateAnimation());
        helper.showLayout(layout);
    }
    public void showLoading() {
       showLoading("加载中...");
    }
    public void showLoading(View view){
        if(view != null)
            helper.showLayout(view);
    }
    public void showLoading(int layId){
        View layout = helper.inflate(layId);
        helper.showLayout(layout);
    }
    

    public void restore() {
        helper.restoreView();
    }


    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }


    private static Animation getRotateAnimation() {
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
                .5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }

}
