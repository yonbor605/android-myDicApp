package com.yonbor.mydicapp.activity.app.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.service.project.ProjectsAdapter;
import com.yonbor.mydicapp.activity.base.BaseFragment;
import com.yonbor.mydicapp.activity.common.WebActivity;
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

        refreshLayout.setPrimaryColorsId(R.color.white, android.R.color.white);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getData(pageNum, cid);
                refreshLayout.finishRefresh(1000);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData(pageNum, cid);
                if (pageNum == pageCount) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    refreshLayout.finishLoadMore(1000);
                }
            }
        });
    }

    private void getData(int curPage, int cid) {
        NetClient.get(baseActivity, HostType.BASE_URL_SECOND, "project/list/" + curPage + "/json?cid=" + cid, null, ProjectListVo.class, new NetClient.Listener2<ProjectListVo>() {
            @Override
            public void onPrepare() {
                showLoadingView();
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
        public void onItemViewClick(View view, ViewHolder holder, ProjectVo projectVo, int position, int tPos) {

        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<ProjectVo> datas, int position) {
            return false;
        }
    };
}
