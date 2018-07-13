package com.yonbor.baselib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.yonbor.baselib.R;
import com.yonbor.baselib.utils.EffectUtil;


public class AppActionBar extends RelativeLayout implements OnClickListener {

    private LayoutInflater mInflater;
    private RelativeLayout mBarView;
    private TextView tvMainTitle;
    private TextView tvSubTitle;
    private ImageView backImageView;
    private ProgressBar titleRefresh;
    private ProgressBar actionRefresh;
    private LinearLayout layAction;
    private LinearLayout layTitle;
    private LinearLayout layMid;
    private ImageView arrow;

    public AppActionBar(Context context) {
        super(context);
        init(context);
    }

    public AppActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化界面
     *
     * @param context
     */
    void init(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBarView = (RelativeLayout) mInflater.inflate(R.layout.actionbar, null);
        addView(mBarView);
        tvMainTitle = (TextView) mBarView.findViewById(R.id.tvMainTitle);
        backImageView = (ImageView) mBarView.findViewById(R.id.backImageView);
        actionRefresh = (ProgressBar) mBarView.findViewById(R.id.actionRefresh);
        layAction = (LinearLayout) findViewById(R.id.layAction);
        titleRefresh = (ProgressBar) mBarView.findViewById(R.id.titleRefresh);
        layTitle = (LinearLayout) mBarView.findViewById(R.id.layTitle);
        layMid = (LinearLayout) mBarView.findViewById(R.id.layMid);
        arrow = (ImageView) mBarView.findViewById(R.id.arrow);
        tvSubTitle = (TextView) mBarView.findViewById(R.id.tvSubTitle);

//		layMid.setBackgroundColor(Color.RED);
//		layAction.setBackgroundColor(Color.BLUE);

        tvMainTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvMainTitle.setSingleLine(true);
        tvMainTitle.setSelected(true);
        tvMainTitle.setFocusable(true);
        tvMainTitle.setFocusableInTouchMode(true);
    }


    public void setBackGround(int color) {
        mBarView.setBackgroundColor(color);
    }

    public void setTitleColor(int color) {
        tvMainTitle.setTextColor(color);
        tvSubTitle.setTextColor(color);
    }


    // 设置返回按钮事件-Action
    public void setBackAction(Action action) {
        backImageView.setOnClickListener(this);
        backImageView.setTag(action);
        backImageView.setImageResource(action.getDrawable());
        backImageView.setVisibility(View.VISIBLE);
    }

    public void setTitle(int resId) {
        tvMainTitle.setText(resId);
        tvMainTitle.setVisibility(View.VISIBLE);
    }

    public void setTitleClick(CharSequence title,
                              OnClickListener onClickListener) {
        tvMainTitle.setText(title);
        tvMainTitle.setVisibility(View.VISIBLE);
        arrow.setVisibility(View.VISIBLE);
        layMid.setOnClickListener(onClickListener);
    }

    public void setTitleClick(OnClickListener onClickListener) {
        layMid.setOnClickListener(onClickListener);
    }

    public void setTitle(String topStr, String bottomStr) {
        tvMainTitle.setText(topStr);
        tvMainTitle.setVisibility(View.VISIBLE);
        tvSubTitle.setText(bottomStr);
        tvSubTitle.setVisibility(View.VISIBLE);
    }

    public void changTitleArrow(int resId) {
        arrow.setImageResource(resId);
    }

    public ImageView getArrow() {
        return arrow;
    }

    public void setTitle(CharSequence title) {
        tvMainTitle.setText(title);
        tvMainTitle.setVisibility(View.VISIBLE);
    }

    public void setTitle(CharSequence title, Action action) {
        tvMainTitle.setOnClickListener(this);
        tvMainTitle.setTag(action);
        tvMainTitle.setText(title);
        tvMainTitle.setVisibility(View.VISIBLE);
    }

    public TextView getTvMainTitle() {
        return tvMainTitle;
    }

    public void arrowAnim(final boolean show) {
        if (show) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(arrow, "rotation", 0, 180);
            anim.start();
        } else {
            ObjectAnimator.ofFloat(arrow, "rotation", 180, 0).start();
        }

    }


    // actionBar开始刷新
    public void startTitleRefresh() {
        titleRefresh.setVisibility(View.VISIBLE);
    }

    // actionBar停止刷新
    public void endTitleRefresh() {
        titleRefresh.setVisibility(View.INVISIBLE);
    }

    // actionBar开始刷新
    public void startActionRefresh() {
        layAction.setVisibility(View.GONE);
        actionRefresh.setVisibility(View.VISIBLE);
    }

    // actionBar停止刷新
    public void endActionRefresh() {
        layAction.setVisibility(View.VISIBLE);
        actionRefresh.setVisibility(View.GONE);
    }


    // 添加Action
    public void addAction(Action action) {
        int index = layAction.getChildCount();
        layAction.addView(inflateAction(action), index);
    }

    public void changeAction(Action action) {
        layAction.removeAllViews();
        int index = layAction.getChildCount();
        layAction.addView(inflateAction(action), index);
    }


    // 改变状态
    public void changeAction(int flg) {
        layAction.setVisibility(flg);
    }

    private View inflateAction(Action action) {
        View view = mInflater.inflate(R.layout.actionbar_item, null,
                false);

        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        if (action instanceof Action2) {
            ((Action2) action).postImageView(iv);
            ((Action2) action).postTextView(tv);
        }
        if (action.getDrawable() != 0) {
            iv.setVisibility(VISIBLE);
            iv.setImageResource(action.getDrawable());
        } else {
            iv.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(action.getText())) {
            tv.setVisibility(VISIBLE);
            tv.setText(action.getText());
        } else {
            tv.setVisibility(GONE);
        }

        view.setTag(action);
        EffectUtil.addClickEffect(view);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        doAction(v);

    }

    public void doAction(View v) {
        Object tag = v.getTag();
        if (tag instanceof Action) {
            Action action = (Action) tag;
            action.performAction(v);
        }
    }

    public interface Action {
        public int getDrawable();

        public String getText();

        public void performAction(View view);
    }

    public interface Action2 extends Action {
        void postImageView(ImageView imageView);

        void postTextView(TextView textView);
    }


}
