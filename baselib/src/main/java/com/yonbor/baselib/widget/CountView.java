package com.yonbor.baselib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.yonbor.baselib.R;
import com.yonbor.baselib.utils.DensityUtil;


@SuppressLint("AppCompatCustomView")
public class CountView extends android.support.v7.widget.AppCompatTextView {

    private int count;

    public CountView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CountView(Context context) {
        super(context);

        init();
    }

    public void setCount(int count) {
        if (count > 99) {
            setText("99+");
            setBackgroundResource(R.drawable.red_big_round_rect_3);
            setVisibility(View.VISIBLE);
        } else if (count <= 0) {
            setText("");
            setVisibility(View.GONE);
        } else if (count >= 10) {
            setText(String.valueOf(count));
            setBackgroundResource(R.drawable.red_big_round_rect);
            setVisibility(View.VISIBLE);
        } else {
            setBackgroundResource(R.drawable.red_round);
            setText(String.valueOf(count));
            setVisibility(View.VISIBLE);
        }

        invalidate();
    }

    private void init() {

        // 重心中间
        setGravity(Gravity.CENTER);
        setPadding(DensityUtil.getPixelsFromDip(getResources(),4.0f),
                DensityUtil.getPixelsFromDip(getResources(), 0f),
                DensityUtil.getPixelsFromDip(getResources(), 4.0f),
                DensityUtil.getPixelsFromDip(getResources(), 0f));
        // 白色
        setTextColor(Color.WHITE);
        setTextSize(10);
//		// 黑色阴影
//		setShadowLayer(1.0f, 1.0f, 1.0f, Color.BLACK);
        // 粗体
        setTypeface(null, Typeface.BOLD);
    }

}
