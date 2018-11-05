package com.yonbor.baselib.utils;

import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/8/9.
 */

public class PopupWindowCompats {
    public static void showAsDropDown(PopupWindow popupWindow, View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//            popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, location[1]+view.getHeight());
            Rect visibleFrame = new Rect();
            view.getGlobalVisibleRect(visibleFrame);
            int height = view.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(view, 0, 0);
        }else{
            popupWindow.showAsDropDown(view);
        }
    }
    public static void showAsDropDown3(PopupWindow popupWindow, View view, int x) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, location[1] + view.getHeight());
    }
}
