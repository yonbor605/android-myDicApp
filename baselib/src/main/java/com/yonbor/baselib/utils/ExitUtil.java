package com.yonbor.baselib.utils;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class ExitUtil {

	private static long exitTime = 0;

	public static void ExitApp(Activity activity) {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(activity.getApplication(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			activity.finish();
		}

	}
	
	
	
	private static long clickTime = 0;
	private static View clickView;

	public static synchronized boolean canClick(View v) {
		if(clickView != v){
			clickView = v;
			return true;
		}
		if ((System.currentTimeMillis() - clickTime) > 500) {
            clickTime = System.currentTimeMillis();
			return true;
		} else {
			return false;
		}

	}
	
	public static void clear(){
        clickView = null;
    }

}
