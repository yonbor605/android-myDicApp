package com.yonbor.baselib.utils;

import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;

/**
 * Created by Administrator on 2016/12/13.
 */
public class EffectUtil {

    /**
     * 添加点击效果
     * @param v    需要触发的View
     * @return view自身
     */
    public static View addClickEffect(View v){
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:{
                        ObjectAnimator obj= ObjectAnimator.ofFloat(v, "alpha",0.3f,1.0f);
                        obj.start();
                        if(!ExitUtil.canClick(v)) {//多次点击拦截事件传递
//                                obj.addListener(new AnimatorListenerAdapter() {
//                                    @Override
//                                    public void onAnimationEnd(Animator animation) {
//                                        v.performClick();
//                                    }
//                                });
                            return true;
                        }
                        
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                        ObjectAnimator.ofFloat(v, "alpha",0.3f,1.0f).start();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator.ofFloat(v, "alpha",1.0f,0.3f).start();
                        break;
                }
                return false;
            }
        });
        return v;
    }
    
    @Deprecated
    public static void addClickEffectRx(final View v, final Listener listener){
        ConnectableObservable<MotionEvent> motionEventObservable = RxView.touches(v)
                .publish();
        
        // Capture down events
        motionEventObservable
                .filter(new Predicate<MotionEvent>() {
                    @Override
                    public boolean test(@NonNull MotionEvent motionEvent) throws Exception {
                        return motionEvent.getAction() == MotionEvent.ACTION_DOWN;
                    }
                }).subscribe(new Consumer<MotionEvent>() {
                    @Override
                    public void accept(@NonNull MotionEvent motionEvent) throws Exception {
                        ObjectAnimator.ofFloat(v, "alpha",1.0f,0.3f).start();
                        if (listener != null) {
                            listener.onDownLisener(v, motionEvent);
                        }
                    }
                });
        // Capture up events
        motionEventObservable
                .filter(new Predicate<MotionEvent>() {
                    @Override
                    public boolean test(@NonNull MotionEvent motionEvent) throws Exception {
                        return motionEvent.getAction() == MotionEvent.ACTION_UP;
                    }
                }).subscribe(new Consumer<MotionEvent>() {
            @Override
            public void accept(@NonNull MotionEvent motionEvent) throws Exception {
                ObjectAnimator.ofFloat(v, "alpha",0.3f,1.0f).start();
                if(listener != null){
                    listener.onUpLisener(v, motionEvent);
                }
            }
        });
        //Capture cancel events
        motionEventObservable
                .filter(new Predicate<MotionEvent>() {
                    @Override
                    public boolean test(@NonNull MotionEvent motionEvent) throws Exception {
                        return motionEvent.getAction() == MotionEvent.ACTION_CANCEL;
                    }
                }).subscribe(new Consumer<MotionEvent>() {
            @Override
            public void accept(@NonNull MotionEvent motionEvent) throws Exception {
                ObjectAnimator.ofFloat(v, "alpha",0.3f,1.0f).start();
                if (listener != null) {
                    listener.onCancelLisener(v, motionEvent);
                }
            }
        });
        //Capture move events
        motionEventObservable
                .filter(new Predicate<MotionEvent>() {
                    @Override
                    public boolean test(@NonNull MotionEvent motionEvent) throws Exception {
                        return motionEvent.getAction() == MotionEvent.ACTION_MOVE;
                    }
                }).subscribe(new Consumer<MotionEvent>() {
            @Override
            public void accept(@NonNull MotionEvent motionEvent) throws Exception {
                if (listener != null) {
                    listener.onMoveLisener(v, motionEvent);
                }
            }
        });
        motionEventObservable.connect();
    }



    public interface Listener{
        void onDownLisener(View v, MotionEvent event);

        void onUpLisener(View v, MotionEvent event);

        void onCancelLisener(View v, MotionEvent event);

        void onMoveLisener(View v, MotionEvent event);
    }
}
