package com.yonbor.mydicapp.beauty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonbor.baselib.model.http.ResultModel;
import com.yonbor.baselib.recyclerviewadapter.MultiItemTypeAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.app.service.HealthyNewsActivity;
import com.yonbor.mydicapp.activity.common.WebActivity;
import com.yonbor.mydicapp.app.AppConstant;
import com.yonbor.mydicapp.app.ConstantsHttp;
import com.yonbor.mydicapp.beauty.adapter.HealthNewsAdapter;
import com.yonbor.mydicapp.model.service.HealthyNewsVo;
import com.yonbor.mydicapp.net.http.NetClient;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ServiceFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.actionbar)
    AppActionBar actionbar;
    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView recyclerview;

    private HealthNewsAdapter adapter;

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
        mainView = inflater.inflate(R.layout.fragment_service, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        setData();
        getNewsData();

    }

    private void getNewsData() {

        ArrayMap<String, String> head = new ArrayMap<>();
        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Health_News_Service);
        head.put(ConstantsHttp.Head_Method, "listNews");
        head.put(ConstantsHttp.Head_Token, "");
        ArrayList body = new ArrayList();
        body.add(0);
        body.add(0);
        body.add(5);
        NetClient.post(baseActivity, ConstantsHttp.Json_Request, head, body, HealthyNewsVo.class,
                new NetClient.Listener<ArrayList<HealthyNewsVo>>() {
                    @Override
                    public void onPrepare() {
                        actionbar.startTitleRefresh();
                        showLoadingView();
                    }

                    @Override
                    public void onSuccess(ResultModel<ArrayList<HealthyNewsVo>> result) {
                        actionbar.endTitleRefresh();
                        refreshComplete();
                        if (result.isSuccess()) {
                            if (!result.isEmpty()) {
                                restoreView();
                                adapter.setDatas(result.data);
                                Logger.e("result", result.data);
                            } else {
                                showEmptyView();
                            }
                        } else {
                            onFaile(null);
                            showToast(result.getToast());
                        }
                    }

                    @Override
                    public void onFaile(Throwable t) {
                        actionbar.endTitleRefresh();
                        refreshComplete();
                        showErrorView();
                    }
                });
    }

    private void setData() {
        adapter = new HealthNewsAdapter();
        adapter.setOnItemClickListener(adapterListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(ContextCompat.getColor(baseContext, R.color.transparent))
                        .sizeResId(R.dimen.dp0_6)
                        .marginResId(R.dimen.dp0, R.dimen.dp0)
                        .build());
        recyclerview.setAdapter(adapter);
    }

    private void findView() {
        actionbar.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.actionbar_bg));
        actionbar.setTitle("健康资讯");
        actionbar.addAction(new AppActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return "查看全部";
            }

            @Override
            public void performAction(View view) {
                Intent intent = new Intent(baseActivity, HealthyNewsActivity.class);
                startActivity(intent);
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


    MultiItemTypeAdapter.OnItemClickListener adapterListener = new MultiItemTypeAdapter.OnItemClickListener<HealthyNewsVo>() {
        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<HealthyNewsVo> datas, int position) {
            datas.get(position).readCount += 1;
            Intent web = new Intent(baseContext, WebActivity.class);
            web.putExtra("title", "资讯详情");
            web.putExtra("url", AppConstant.BASE_URL + "h5/healthnews.html?id=" + datas.get(position).id + "&token=1");
            startActivity(web);
        }

        @Override
        public void onItemViewClick(View view, ViewHolder holder, HealthyNewsVo item, int position, int tPos) {
        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<HealthyNewsVo> datas, int position) {
            return false;
        }
    };


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


}
