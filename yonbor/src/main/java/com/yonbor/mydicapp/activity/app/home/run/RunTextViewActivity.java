package com.yonbor.mydicapp.activity.app.home.run;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.rxtool.RxToast;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.view.run.RxRunTextView;
import com.yonbor.mydicapp.view.run.RxTextViewVertical;
import com.yonbor.mydicapp.view.run.RxTextViewVerticalMore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/home/run/runTextView")
public class RunTextViewActivity extends BaseActivity {

    @BindView(R.id.tv_runtitle)
    RxRunTextView mTvRuntitle;
    @BindView(R.id.text)
    RxTextViewVertical mRxVText;
    @BindView(R.id.upview1)
    RxTextViewVerticalMore mUpview1;
    @BindView(R.id.activity_run_text_view)
    LinearLayout mActivityRunTextView;

    private ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_textview);
        ButterKnife.bind(this);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("RunTextView");
        actionBar.setBackAction(new AppActionBar.Action() {

            @Override
            public void performAction(View view) {
                back();
            }

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }
        });


        titleList.add("你是天上最受宠的一架钢琴");
        titleList.add("我是丑人脸上的鼻涕");
        titleList.add("你发出完美的声音");
        titleList.add("我被默默揩去");
        titleList.add("你冷酷外表下藏着诗情画意");
        titleList.add("我已经够胖还吃东西");
        titleList.add("你踏着七彩祥云离去");
        titleList.add("我被留在这里");
        mRxVText.setTextList(titleList);
        mRxVText.setText(26, 5, 0xff766156);//设置属性
        mRxVText.setTextStillTime(3000);//设置停留时长间隔
        mRxVText.setAnimTime(300);//设置进入和退出的时间间隔
        mRxVText.setOnItemClickListener(new RxTextViewVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RxToast.success(baseContext, "点击了 : " + titleList.get(position), Toast.LENGTH_SHORT, true).show();
            }
        });

        List<View> views = new ArrayList<>();
        setUPMarqueeView(views, 11);
        mUpview1.setViews(views);
    }

    private void setUPMarqueeView(List<View> views, int size) {
        for (int i = 0; i < size; i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(baseContext).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);
            TextView tv2 = moreView.findViewById(R.id.tv2);

            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            //进行对控件赋值
            tv1.setText("五一欢乐与您共享，ＸＸ节能高清惊喜大促销。");
            if (size > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText("五一充值送机，你准备好了吗？");
            } else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRxVText.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRxVText.stopAutoScroll();
    }


}
