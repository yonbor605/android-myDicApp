package com.yonbor.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * AdjustSize 键盘监听
 */
public class AdjustSizeLinearLayout extends LinearLayout {
    private int mChangeSize = 100;//程度因子
    private SoftkeyBoardListener boardListener;
    public AdjustSizeLinearLayout(Context context) {
        super(context);
    }

    public AdjustSizeLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AdjustSizeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldw == 0 || oldh == 0)
            return;
        if (boardListener != null) {
            if (oldw != 0 && h - oldh < -mChangeSize) {
                boardListener.keyBoardVisable(Math.abs(h - oldh));
            }

            if (oldw != 0 && h - oldh > mChangeSize) {
                boardListener.keyBoardInvisable(Math.abs(h - oldh));
            }
        }
    }

    public interface SoftkeyBoardListener {
        void keyBoardVisable(int move);
        void keyBoardInvisable(int move);
    }

    public void setSoftKeyBoardListener(SoftkeyBoardListener boardListener) {
        this.boardListener = boardListener;
    }
}