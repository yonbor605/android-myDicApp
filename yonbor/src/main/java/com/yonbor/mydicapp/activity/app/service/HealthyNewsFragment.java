package com.yonbor.mydicapp.activity.app.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonbor.baselib.model.http.ResultModel;
import com.yonbor.baselib.recyclerviewadapter.MultiItemTypeAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.recyclerviewadapter.wrapper.LoadMoreView;
import com.yonbor.baselib.recyclerviewadapter.wrapper.LoadMoreWrapper;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseFrameFragment;
import com.yonbor.mydicapp.activity.common.WebActivity;
import com.yonbor.mydicapp.app.AppConstant;
import com.yonbor.mydicapp.app.ConstantsHttp;
import com.yonbor.mydicapp.beauty.adapter.HealthNewsAdapter;
import com.yonbor.mydicapp.model.service.HealthyNewsVo;
import com.yonbor.mydicapp.net.http.HostType;
import com.yonbor.mydicapp.net.http.NetClient;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * 健康资讯列表
 */
public class HealthyNewsFragment extends BaseFrameFragment {

    SwipeMenuRecyclerView recyclerview;

    HealthNewsAdapter adapter;
    LayoutInflater mLayoutInflater;

    LoadMoreWrapper mLoadMoreWrapper;
    LoadMoreView loadView;

    ArrayList<HealthyNewsVo> dataList = new ArrayList<>();

    private String tagCode;

    public String getTagCode() {
        return tagCode;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_healthy_news_list, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tagCode = bundle.getString("tagCode");
        }
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated start");
        this.mLayoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        findView();
        if ("0".equals(tagCode)) {
            getData();
        }
    }


    @Override
    public void startHint() {
        if (isLoaded) {
            return;
        }
        if (!"0".equals(tagCode)) {
            getData();
        }
        isLoaded = true;
    }

    @Override
    public void endHint() {

    }


    public void findView() {
        loadView = new LoadMoreView(getActivity());
        loadView.setErrorListener(new LoadMoreView.OnErrorListener() {
            @Override
            public void onErrorListener() {
                getData();
            }
        });

        recyclerview = (SwipeMenuRecyclerView) mainView.findViewById(R.id.recyclerview);

        initPtrFrameLayout(mainView);

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
        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        mLoadMoreWrapper.setLoadMoreView(loadView);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (loadView.canLoad()) {
                    pageNo++;
                    getData();
                }
            }
        });
        recyclerview.setAdapter(mLoadMoreWrapper);
    }


    MultiItemTypeAdapter.OnItemClickListener adapterListener = new MultiItemTypeAdapter.OnItemClickListener<HealthyNewsVo>() {
        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<HealthyNewsVo> datas, int position) {
            datas.get(position).readCount += 1;
            mLoadMoreWrapper.notifyItemChanged(position);
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

    public void getData() {
        ArrayMap<String, String> head = new ArrayMap<>();
        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Health_News_Service);
        head.put(ConstantsHttp.Head_Method, "listNews");
        head.put(ConstantsHttp.Head_Token, "");
        ArrayList body = new ArrayList();
        body.add(tagCode);
        body.add(pageNo);
        body.add(pageSize);
        NetClient.post(baseActivity, HostType.BASE_URL_FIRST, ConstantsHttp.Json_Request, head, body, HealthyNewsVo.class,
                new NetClient.Listener<ArrayList<HealthyNewsVo>>() {
                    @Override
                    public void onPrepare() {
                        showLoadingView();
                    }

                    @Override
                    public void onSuccess(ResultModel<ArrayList<HealthyNewsVo>> result) {
                        frame.refreshComplete();
                        if (result.isSuccess()) {
                            if (!result.isEmpty()) {
                                viewHelper.restore();
                                loadView.setState(result.data.size() < pageSize ?
                                        LoadMoreView.END : LoadMoreView.LOAD);
                                if (pageNo == FIRST_PAGE) {
                                    dataList = result.data;
                                } else {
                                    dataList.addAll(result.data);
                                }
                                adapter.setDatas(dataList);

                                mLoadMoreWrapper.notifyDataSetChanged();
                            } else {
                                loadView.setState(LoadMoreView.END);
                                showEmptyView();
                            }
                        } else {
                            onFaile(null);
                        }
                    }

                    @Override
                    public void onFaile(Throwable t) {
                        frame.refreshComplete();
                        showErrorView();
                        loadView.setState(LoadMoreView.ERROR);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        pageNo = FIRST_PAGE;
        getData();
    }

    @Override
    public boolean isEmpty() {
        return adapter.getDatas() == null || adapter.getDatas().isEmpty();
    }
}
