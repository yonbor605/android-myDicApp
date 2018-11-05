package com.yonbor.baselib.utils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * Created by zhaozhijun on STEP_ADD17/3/8.
 */

public class ScaleAnimation {
    private float scale;
    private float height;
    private float width;
    private Interpolator floaterpolator;
    private float duration;
    private View view;
    private float changeHeight;
    private float changeWidth;
    private float timeScale;
    private boolean isDone = true;
    private float STEP_ADD = 20;
    private float step = STEP_ADD;
    private float bgHeight;
    private float bgWidth;
    private long startTime;

    private boolean animationIn;
    private boolean animationOut;

    public ScaleAnimation(float scale, float height, float width, Interpolator floaterpolator, float duration){
        this.scale = scale;
        this.height = bgHeight = height;
        this.width = bgWidth = width;
        this.duration = duration;
        timeScale = (float) (1.0/duration);
        this.floaterpolator = floaterpolator;
    }

    public void startAnimation(View view){
        if(view==null){return;}//view==null||!isDone
        this.view = view;
        step = STEP_ADD;
        isDone = false;
        this.view = view;
        bgHeight = view.getHeight();
        bgWidth = view.getWidth();
        if(view.getHeight()>=height){
            changeHeight = -(height - view.getHeight()/scale);
            changeWidth = -(width - view.getWidth()/scale);
        }else {
            changeWidth = width - view.getWidth();
            changeHeight = height - view.getHeight();
        }
        startTime = AnimationUtils.currentAnimationTimeMillis();
        handler.sendEmptyMessage(1);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(cumputeCurrentPostion()){
                handler.sendEmptyMessage(1);
            }
            super.handleMessage(msg);
        }
    };

    private boolean cumputeCurrentPostion(){
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        step+=STEP_ADD;
        float change = 0;
        if((AnimationUtils.currentAnimationTimeMillis() - startTime)<duration){
//            change = step*timeScale;
            change = (AnimationUtils.currentAnimationTimeMillis() - startTime)*timeScale;
            lp.width = (int)bgHeight + Math.round(change*changeWidth);
            lp.height = (int)bgWidth +  Math.round(changeHeight*change);
            view.setLayoutParams(lp);
            return true;
        }else {
            isDone = true;
            lp.width = (int)bgHeight + Math.round(changeWidth);
            lp.height = (int)bgWidth +  Math.round(changeHeight);
            view.setLayoutParams(lp);
            return false;
        }
    }

    public void startAnimationIn(View view){
        if(view==null){return;}//view==null||!isDone
        this.view = view;
        if(animationIn||view.getHeight()<height){return;}
        animationIn = true;
        animationOut = false;
        step = STEP_ADD;
        isDone = false;
        this.view = view;
        bgHeight = view.getHeight();
        bgWidth = view.getWidth();
        startTime = AnimationUtils.currentAnimationTimeMillis();
        changeHeight = -(height - view.getHeight()/scale);
        changeWidth = -(width - view.getWidth()/scale);
        handlerIn.sendEmptyMessage(1);
    }

    private Handler handlerIn = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(cumputeCurrentPostionIn()){
                handlerIn.sendEmptyMessage(1);
            }
            super.handleMessage(msg);
        }
    };

    private boolean cumputeCurrentPostionIn(){
        if(!animationIn){return false;}
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        step+=STEP_ADD;
        float change = 0;
        if((AnimationUtils.currentAnimationTimeMillis() - startTime)<duration){
            change = (AnimationUtils.currentAnimationTimeMillis() - startTime)*timeScale;
            lp.width = (int)bgHeight + Math.round(change*changeWidth);
            lp.height = (int)bgWidth +  Math.round(changeHeight*change);
            if(lp.width<=width+changeHeight){
                lp.width = (int) width;
                lp.height = (int) height;
                return true;
            }
            view.setLayoutParams(lp);
            return true;
        }else {
            isDone = true;
            lp.width = (int)bgHeight + Math.round(changeWidth);
            lp.height = (int)bgWidth +  Math.round(changeHeight);
            view.setLayoutParams(lp);
            return false;
        }
    }

    public void startAnimationOut(View view){
        if(view==null){return;}//view==null||!isDone
        this.view = view;
        if(animationOut){return;}
        animationIn = false;
        animationOut = true;
        step = STEP_ADD;
        isDone = false;
        this.view = view;
        bgHeight = view.getHeight();
        bgWidth = view.getWidth();
        startTime = AnimationUtils.currentAnimationTimeMillis();
        changeWidth = width - view.getWidth();
        changeHeight = height - view.getHeight();
        handlerOut.sendEmptyMessage(1);
    }

    private boolean cumputeCurrentPostionOut(){
        if(!animationOut){return false;}
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        step+=STEP_ADD;
        float change = 0;
        if((AnimationUtils.currentAnimationTimeMillis() - startTime)<duration){
            change = (AnimationUtils.currentAnimationTimeMillis() - startTime)*timeScale;
            lp.width = (int)bgHeight + Math.round(change*changeWidth);
            lp.height = (int)bgWidth +  Math.round(changeHeight*change);
            if(lp.width>width){
                lp.width = (int) width;
                lp.height = (int) height;
                return true;}
            view.setLayoutParams(lp);
            return true;
        }else {
            isDone = true;
            lp.width = (int)bgHeight + Math.round(changeWidth);
            lp.height = (int)bgWidth +  Math.round(changeHeight);
            view.setLayoutParams(lp);
            return false;
        }
    }

    private Handler handlerOut = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(cumputeCurrentPostionOut()){
                handlerOut.sendEmptyMessage(1);
            }
            super.handleMessage(msg);
        }
    };
}
