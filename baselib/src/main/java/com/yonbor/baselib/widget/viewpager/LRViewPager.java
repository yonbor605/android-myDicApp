package com.yonbor.baselib.widget.viewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * 滑动控件，封装ViewPager
 * 
 * @author Administrator
 * 
 */
public class LRViewPager extends ViewPager {
	private boolean left = false;
	private boolean right = false;
	private boolean isScrolling = false;
	private int lastValue = -1;
	private ChangeViewCallback changeViewCallback = null;

	private static final boolean API_11;
	static {
		API_11 = Build.VERSION.SDK_INT >= 11;
	}

	public LRViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LRViewPager(Context context) {
		super(context);
		init();
	}

	/**
	 * init method .
	 */
	private void init() {
		setOnPageChangeListener(listener);
	}

	/**
	 * listener ,to get move direction .
	 */
	public OnPageChangeListener listener = new OnPageChangeListener() {
		@Override
		public void onPageScrollStateChanged(int paramInt) {
			if (paramInt == 1) {
				isScrolling = true;
			} else {
				isScrolling = false;
			}

			if (paramInt == 2) {
				// notify ....
				// if (changeViewCallback != null) {
				// changeViewCallback.changeView(left, right);
				// }
				right = left = false;
			}

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			if (isScrolling) {
				if (lastValue > positionOffsetPixels) {
					// 递减，向右侧滑动
					right = true;
					left = false;
				} else if (lastValue < positionOffsetPixels) {
					// 递减，向右侧滑动
					right = false;
					left = true;
				} else if (lastValue == positionOffsetPixels) {
					right = left = false;
				}
			} else {
				if (changeViewCallback != null) {
					changeViewCallback.getCurrentPageIndex(position);
				}
			}
			float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;
			changeViewCallback.changeView(left, right, position, effectOffset);
			lastValue = positionOffsetPixels;
			if (effectOffset == 0) {
				disableHardwareLayer();
			}
		}

		@Override
		public void onPageSelected(int position) {
			if (changeViewCallback != null) {
				changeViewCallback.getCurrentPageIndex(position);
			}
		}
	};

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void disableHardwareLayer() {
		if (!API_11)
			return;
		View v;
		for (int i = 0; i < getChildCount(); i++) {
			v = getChildAt(i);
			if (v.getLayerType() != View.LAYER_TYPE_NONE)
				v.setLayerType(View.LAYER_TYPE_NONE, null);
		}
	}

	private boolean isSmall(float positionOffset) {
		return Math.abs(positionOffset) < 0.0001;
	}

	/**
	 * 得到是否向右侧滑动
	 * 
	 * @return true 为右滑动
	 */
	public boolean getMoveRight() {
		return right;
	}

	/**
	 * 得到是否向左侧滑动
	 * 
	 * @return true 为左做滑动
	 */
	public boolean getMoveLeft() {
		return left;
	}

	/**
	 * 滑动状态改变回调
	 * 
	 * @author zxy
	 * 
	 */
	public interface ChangeViewCallback {
		/**
		 * 切换视图 ？决定于left和right 。
		 * 
		 * @param left
		 * @param right
		 */
		public void changeView(boolean left, boolean right, int position,
                               float positionOffset);

		public void getCurrentPageIndex(int index);
	}

	/**
	 * set ...
	 * 
	 * @param callback
	 */
	public void setChangeViewCallback(ChangeViewCallback callback) {
		changeViewCallback = callback;
	}
}
