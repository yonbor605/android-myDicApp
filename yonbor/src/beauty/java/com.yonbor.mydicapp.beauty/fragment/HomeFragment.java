package com.yonbor.mydicapp.beauty.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yonbor.baselib.rxtool.RxImageTool;
import com.yonbor.baselib.rxtool.RxRecyclerViewDividerTool;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.app.home.code.CodeToolActivity;
import com.yonbor.mydicapp.activity.app.home.code.CreateQRCodeActivity;
import com.yonbor.mydicapp.activity.app.home.tool.DataToolActivity;
import com.yonbor.mydicapp.activity.app.home.device.DeviceInfoActivity;
import com.yonbor.mydicapp.activity.app.home.order.TakeoutOrderActivity;
import com.yonbor.mydicapp.activity.app.home.player.VideoPlayerActivity;
import com.yonbor.mydicapp.activity.app.home.run.RunTextViewActivity;
import com.yonbor.mydicapp.activity.app.home.rxjava2.Rxjava2SampleActivity;
import com.yonbor.mydicapp.activity.app.home.toast.RxToastActivity;
import com.yonbor.mydicapp.activity.app.home.tool.TextToolActivity;
import com.yonbor.mydicapp.beauty.adapter.HomeAdapter;
import com.yonbor.mydicapp.model.home.HomeItemVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 首頁
 */
public class HomeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.actionbar)
    AppActionBar actionbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<HomeItemVo> mData;
    private int mColumnCount = 3;


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
        mainView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        initData();
        initView();
    }

    private void initView() {
        if (mColumnCount <= 1) {
            recyclerview.setLayoutManager(new LinearLayoutManager(baseContext));
        } else {
            recyclerview.setLayoutManager(new GridLayoutManager(baseContext, mColumnCount));
        }

        recyclerview.addItemDecoration(new RxRecyclerViewDividerTool(RxImageTool.dp2px(5f)));
        HomeAdapter adapter = new HomeAdapter(mData);
        recyclerview.setAdapter(adapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add(new HomeItemVo("二维码与条形码的扫描与生成", R.drawable.circle_dynamic_generation_code, CodeToolActivity.class, "/home/code/codeTool"));
        mData.add(new HomeItemVo("动态生成码", R.drawable.circle_qr_code, CreateQRCodeActivity.class, "/home/code/creatQRCode"));
        mData.add(new HomeItemVo("设备信息", R.drawable.circle_device_info, DeviceInfoActivity.class, "/home/device/deviceInfo"));

        mData.add(new HomeItemVo("RxToast的使用", R.drawable.circle_toast, RxToastActivity.class, "/home/toast/rxToast"));
        mData.add(new HomeItemVo("Rxjava2在Android中的使用", R.drawable.circle_rxjava, Rxjava2SampleActivity.class, "/home/rxjava2/rxjava2Sample"));
        mData.add(new HomeItemVo("多媒体播放器", R.drawable.circle_video_player, VideoPlayerActivity.class, "/home/player/videoPlayer"));

        mData.add(new HomeItemVo("外卖点单", R.drawable.circle_takeout_order, TakeoutOrderActivity.class, "/home/order/takeoutOrder"));
        mData.add(new HomeItemVo("RunTextView的使用",R.drawable.circle_wrap_text, RunTextViewActivity.class,"/home/run/runTextView"));
        mData.add(new HomeItemVo("数据处理工具",R.drawable.circle_data, DataToolActivity.class,"/home/tool/dataTool"));

        mData.add(new HomeItemVo("文本处理工具",R.drawable.circle_text, TextToolActivity.class,"/home/tool/textTool"));




    }

    private void findView() {
        actionbar.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.actionbar_bg));
        actionbar.setTitle("首页");
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
