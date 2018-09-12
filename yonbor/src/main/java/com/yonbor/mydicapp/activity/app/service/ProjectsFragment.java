package com.yonbor.mydicapp.activity.app.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonbor.baselib.recyclerviewadapter.MultiItemTypeAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.widget.loading.LoadViewHelper;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.service.project.ProjectsAdapter;
import com.yonbor.mydicapp.activity.base.BaseFragment;
import com.yonbor.mydicapp.activity.common.WebActivity;
import com.yonbor.mydicapp.model.NullModel;
import com.yonbor.mydicapp.model.WanAndroidVo;
import com.yonbor.mydicapp.model.service.project.ProjectListVo;
import com.yonbor.mydicapp.model.service.project.ProjectVo;
import com.yonbor.mydicapp.net.http.HostType;
import com.yonbor.mydicapp.net.http.NetClient;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class ProjectsFragment extends BaseFragment {

    private SmartRefreshLayout refreshLayout;
    private SwipeMenuRecyclerView recyclerview;
    private int cid;
    private ProjectsAdapter adapter;
    private int pageNum = 1;
    private int pageCount;
    private ArrayList<ProjectVo> projectList = new ArrayList<>();
    private static boolean isRefreshOrLoadMore = false; // 解决viewHelper的状态动画与SmartRefreshLayout的刷新、加载动画的冲突


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_project_list, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cid = bundle.getInt("cid");
        }
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        getData(pageNum, cid);
    }

    private void findView() {
        refreshLayout = mainView.findViewById(R.id.refreshLayout);
        recyclerview = mainView.findViewById(R.id.recyclerview);

        View base = mainView.findViewById(R.id.loadLay);
        if (base != null) {
            viewHelper = new LoadViewHelper(base);
            viewHelper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        }

        adapter = new ProjectsAdapter();
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

        onRefresh();
        onLoadMore();
    }

    private void onLoadMore() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefreshOrLoadMore = true;
                if (pageNum == pageCount) {
                    getData(pageNum, cid);
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    getData(pageNum, cid);
                    refreshLayout.finishLoadMore(1000);
                }
            }
        });
    }

    private void onRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefreshOrLoadMore = true;
                pageNum = 1;
                getData(pageNum, cid);
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    private void getData(int curPage, int cid) {
        NetClient.get(baseActivity, HostType.BASE_URL_SECOND, "project/list/" + curPage + "/json?cid=" + cid, null, ProjectListVo.class, new NetClient.Listener2<ProjectListVo>() {
            @Override
            public void onPrepare() {
                if (!isRefreshOrLoadMore) {
                    showLoadingView();
                }
                isRefreshOrLoadMore = false;
            }

            @Override
            public void onSuccess(WanAndroidVo<ProjectListVo> result) {
                if (result.isSuccess()) {
                    if (!result.isEmpty()) {
                        restoreView();
                        int curPage = result.data.getCurPage();
                        pageNum = curPage + 1;
                        pageCount = result.data.getPageCount();
                        projectList = result.data.getDatas();
                        if (curPage == 1) { // 第一页
                            adapter.setDatas(projectList);
                        } else {
                            adapter.addDatas(projectList);
                        }
                    } else {
                        showEmptyView();
                    }

                }
            }

            @Override
            public void onFaile(Throwable t) {
                showErrorView();
            }
        });
    }


    MultiItemTypeAdapter.OnItemClickListener adapterListener = new MultiItemTypeAdapter.OnItemClickListener<ProjectVo>() {

        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<ProjectVo> datas, int position) {
            Intent web = new Intent(baseContext, WebActivity.class);
            web.putExtra("title", datas.get(position).getTitle());
            web.putExtra("url", datas.get(position).getLink());
            startActivity(web);
        }

        @Override
        public void onItemViewClick(View view, ViewHolder holder, ProjectVo item, int position, int tPos) {
            switch (view.getId()) {
                case R.id.iv_project_like:
                    boolean isCollect = item.isCollect();
                    item.setCollect(!isCollect);
                    adapter.update(item);
                    if (isCollect) {
                        taskCancelCollect(item.getId());
                    } else {
                        taskCollect(item.getId());
                    }
                    break;
            }

        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<ProjectVo> datas, int position) {
            return false;
        }
    };

    private void taskCancelCollect(int id) {
        ArrayMap<String, String> head = new ArrayMap<>();
        head.put("Cookie", "loginUserName=mydicapp;loginUserPassword=123qwe");

        NetClient.post(baseActivity, HostType.BASE_URL_SECOND, "lg/uncollect_originId/" + id + "/json", head, "", NullModel.class, new NetClient.Listener2<NullModel>() {

            @Override
            public void onPrepare() {

            }

            @Override
            public void onSuccess(WanAndroidVo<NullModel> result) {
                if (result.isSuccess()) {
                    showToast("已取消收藏");
                } else {
                    showToast(result.getToast());
                    onFaile(null);
                }
            }

            @Override
            public void onFaile(Throwable t) {

            }
        });
    }

    /**
     * 注意所有收藏相关都需要登录操作，建议登录将返回的cookie（其中包含账号、密码）持久化到本地即可。
     *
     * @param id
     */
    private void taskCollect(int id) {

        ArrayMap<String, String> head = new ArrayMap<>();
        head.put("Cookie", "loginUserName=mydicapp;loginUserPassword=123qwe");

        NetClient.post(baseActivity, HostType.BASE_URL_SECOND, "lg/collect/" + id + "/json", head, "", NullModel.class, new NetClient.Listener2<NullModel>() {

            @Override
            public void onPrepare() {

            }

            @Override
            public void onSuccess(WanAndroidVo<NullModel> result) {
                if (result.isSuccess()) {
                    showToast("收藏成功");
                } else {
                    showToast(result.getToast());
                    onFaile(null);
                }
            }

            @Override
            public void onFaile(Throwable t) {

            }
        });

    }
}
