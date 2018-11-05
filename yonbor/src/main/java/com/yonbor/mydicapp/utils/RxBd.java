package com.yonbor.mydicapp.utils;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class RxBd {
    public static Observable<Object> click(View v){
        return RxView.clicks(v).throttleFirst(300, TimeUnit.MILLISECONDS);
    }
}
