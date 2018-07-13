package com.yonbor.baselib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.yonbor.baselib.base.AppContext;


/**
 * dp   px   转换
 * 
 * @author zkl
 * 
 */

public class DensityUtil {
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int dip2px(float dpValue) {
		return dip2px(AppContext.getContext(), dpValue);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 从DIP获取像素值
	 * 
	 * @param resource
	 * @param value
	 * @return
	 */
	public static int getPixelsFromDip(Resources resource, float value) {
        return (int) getPixelsFromDipf(resource, value);
    }
	
	/**
	 * 从DIP获取像素值
	 * 
	 * @param resource
	 * @param value
	 * @return
	 */
	public static float getPixelsFromDipf(Resources resource, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
        		resource.getDisplayMetrics());
    }
}
