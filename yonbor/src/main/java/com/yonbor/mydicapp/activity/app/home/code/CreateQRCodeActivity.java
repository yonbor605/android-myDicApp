package com.yonbor.mydicapp.activity.app.home.code;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.utils.RxBarCode;
import com.yonbor.mydicapp.utils.RxQRCode;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/home/code/creatQRCode")
public class CreateQRCodeActivity extends BaseActivity {

    private static Handler Handler = new Handler();
    private static Runnable mRunnable = null;
    private int second = 60;

    @BindView(R.id.iv_linecode)
    ImageView ivLinecode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tv_time_second)
    TextView tvTimeSecond;
    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 60000:
                    setData();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qrcode);
        ButterKnife.bind(this);
        findView();
        setData();
        setListener();
        AuthCode(tvTimeSecond, second);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Handler.removeCallbacks(mRunnable);
    }

    private void AuthCode(final TextView view, final int timeSecond) {

        mRunnable = new Runnable() {
            int mSumNum = timeSecond;

            @Override
            public void run() {
                Handler.postDelayed(mRunnable, 1000);
                view.setText(mSumNum + "");
                view.setEnabled(false);
                mSumNum--;
                if (mSumNum < 0) {
                    view.setText(0 + "");
                    view.setEnabled(true);
                    Message message = new Message();
                    message.what = 60000;
                    mHandler.sendMessage(message);
                    // 干掉这个定时器，下次减不会累加
                    Handler.removeCallbacks(mRunnable);
                    AuthCode(tvTimeSecond, second);
                }
            }

        };
        Handler.postDelayed(mRunnable, 1000);
    }

    private void setData() {
        RxQRCode.createQRCode("时间戳:" + System.currentTimeMillis(), 800, 800, ivCode);
        ivLinecode.setImageBitmap(RxBarCode.createBarCode("" + System.currentTimeMillis(), 1000, 300));
    }

    private void setListener() {
        EffectUtil.addClickEffect(llRefresh);
        llRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler.removeCallbacks(mRunnable);
                setData();
                tvTimeSecond.setText(second + "");
                AuthCode(tvTimeSecond, second);
            }
        });
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("动态生成码");
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
    }
}
