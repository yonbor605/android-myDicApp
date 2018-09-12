package com.yonbor.mydicapp.beauty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.app.my.MyCollectionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyFragment extends BaseFragment {


    Unbinder unbinder;
    // 设置
    @BindView(R.id.iv_seting)
    ImageView ivSeting;
    // 二维码
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    // 头像
    @BindView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    // 姓名
    @BindView(R.id.tv_name)
    TextView tvName;
    // 立即登录
    @BindView(R.id.tv_login)
    TextView tvLogin;
    // 个人信息
    @BindView(R.id.rl_info)
    RelativeLayout rlInfo;
    // 卡管理
    @BindView(R.id.rl_card_manage)
    RelativeLayout rlCardManage;
    // 我的家庭
    @BindView(R.id.rl_myFamily)
    RelativeLayout rlMyFamily;
    // 我的档案
    @BindView(R.id.rl_myFile)
    RelativeLayout rlMyFile;
    // 门诊预约
    @BindView(R.id.rl_appointHistory)
    RelativeLayout rlAppointHistory;
    // 签约记录
    @BindView(R.id.rl_signhistory)
    RelativeLayout rlSignhistory;
    // 咨询记录
    @BindView(R.id.rl_consultation)
    RelativeLayout rlConsultation;
    // 服务记录
    @BindView(R.id.rl_service_history)
    RelativeLayout rlServiceHistory;
    // 健康指标
    @BindView(R.id.rl_healthy_indicators)
    RelativeLayout rlHealthyIndicators;
    // 我的收藏
    @BindView(R.id.rl_myCollect)
    RelativeLayout rlMyCollect;
    // 评价记录
    @BindView(R.id.rl_evaluationHistory)
    RelativeLayout rlEvaluationHistory;
    // 意见反馈
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    // 设置
    @BindView(R.id.rl_set)
    RelativeLayout rlSet;

    @Override
    public void startHint() {

    }

    @Override
    public void endHint() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListener();
    }

    private void setListener() {

        //头像
        EffectUtil.addClickEffect(ivAvatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //二维码
        EffectUtil.addClickEffect(ivQrCode);
        ivQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //个人信息
        EffectUtil.addClickEffect(rlInfo);
        rlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //卡管理
        EffectUtil.addClickEffect(rlCardManage);
        rlCardManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //我的家庭
        EffectUtil.addClickEffect(rlMyFamily);
        rlMyFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //我的档案
        EffectUtil.addClickEffect(rlMyFile);
        rlMyFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //我的收藏
        EffectUtil.addClickEffect(rlMyCollect);
        rlMyCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCollectionActivity.class);
                startActivity(intent);
            }
        });

        //预约记录
        EffectUtil.addClickEffect(rlAppointHistory);
        rlAppointHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //评价记录
        EffectUtil.addClickEffect(rlEvaluationHistory);
        rlEvaluationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //签约记录
        EffectUtil.addClickEffect(rlSignhistory);
        rlSignhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //咨询记录
        EffectUtil.addClickEffect(rlConsultation);
        rlConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //服务记录
        EffectUtil.addClickEffect(rlServiceHistory);
        rlServiceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //健康指标
        EffectUtil.addClickEffect(rlHealthyIndicators);
        rlHealthyIndicators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //意见反馈
        EffectUtil.addClickEffect(rlFeedback);
        rlFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //设置
        EffectUtil.addClickEffect(ivSeting);
        ivSeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
