package com.yonbor.mydicapp.activity.app.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonbor.baselib.recyclerviewadapter.MultiItemTypeAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.baselib.widget.loading.LoadViewHelper;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.service.article.ArticlesAdapter;
import com.yonbor.mydicapp.activity.app.service.ProjectsActivity;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.activity.common.WebActivity;
import com.yonbor.mydicapp.model.WanAndroidVo;
import com.yonbor.mydicapp.model.service.article.ArticleListVo;
import com.yonbor.mydicapp.model.service.article.ArticleVo;
import com.yonbor.mydicapp.model.service.project.ProjectVo;
import com.yonbor.mydicapp.net.http.HostType;
import com.yonbor.mydicapp.net.http.NetClient;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectionActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.loadLay)
    LinearLayout loadLay;

    private ArticlesAdapter adapter;
    private int pageNum = 0;
    private int pageCount;
    private ArrayList<ArticleVo> articleList = new ArrayList<>();
    private static boolean isRefreshOrLoadMore = false; // 解决viewHelper的状态动画与SmartRefreshLayout的刷新、加载动画的冲突

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        findView();
        getArticleData(pageNum);
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("我的收藏");
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

        if (loadLay != null) {
            viewHelper = new LoadViewHelper(loadLay);
            viewHelper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        }

        adapter = new ArticlesAdapter();
        adapter.setOnItemClickListener(adapterListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(ContextCompat.getColor(baseContext, R.color.transparent))
                        .sizeResId(R.dimen.dp0_6)
                        .marginResId(R.dimen.dp0, R.dimen.dp0)
                        .build());
        recyclerview.setAdapter(adapter);

        onRefresh();
        onLoadMore();
    }


    private void getArticleData(int curPage) {
        ArrayMap<String, String> head = new ArrayMap<>();
        head.put("Cookie", "loginUserName=mydicapp;loginUserPassword=123qwe");

        NetClient.get(baseActivity, HostType.BASE_URL_SECOND, "lg/collect/list/" + curPage + "/json", head, ArticleListVo.class, new NetClient.Listener2<ArticleListVo>() {
            @Override
            public void onPrepare() {
                if (!isRefreshOrLoadMore) {
                    showLoadingView();
                }
                isRefreshOrLoadMore = false;
            }

            @Override
            public void onSuccess(WanAndroidVo<ArticleListVo> result) {
                if (result.isSuccess()) {
                    if (!result.isEmpty()) {
                        restoreView();
                        pageNum = result.data.getCurPage();
                        pageCount = result.data.getPageCount();
                        articleList = result.data.getDatas();
                        if (pageNum == 1) { // 第一页
                            adapter.setDatas(articleList);
                        } else {
                            adapter.addDatas(articleList);
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

    private void onLoadMore() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefreshOrLoadMore = true;
                if (pageNum == (pageCount - 1)) {
                    getArticleData(pageNum);
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    getArticleData(pageNum);
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
                pageNum = 0;
                getArticleData(pageNum);
                refreshLayout.finishRefresh(1000);
            }
        });
    }


    MultiItemTypeAdapter.OnItemClickListener adapterListener = new MultiItemTypeAdapter.OnItemClickListener<ArticleVo>() {

        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<ArticleVo> datas, int position) {
            Intent web = new Intent(baseContext, WebActivity.class);
            web.putExtra("title", datas.get(position).getTitle());
            web.putExtra("url", datas.get(position).getLink());
            startActivity(web);
        }

        @Override
        public void onItemViewClick(View view, ViewHolder holder, ArticleVo item, int position, int tPos) {
            switch (view.getId()) {
                case R.id.iv_like:
//                    boolean isCollect = item.isCollect();
//                    item.setCollect(!isCollect);
//                    adapter.update(item);
//                    if (isCollect) {
//                        taskCancelCollect(item.getId());
//                    } else {
//                        taskCollect(item.getId());
//                    }
                    break;
            }

        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<ArticleVo> datas, int position) {
            return false;
        }
    };
}
