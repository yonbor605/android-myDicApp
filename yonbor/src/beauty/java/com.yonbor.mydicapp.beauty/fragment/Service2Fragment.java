package com.yonbor.mydicapp.beauty.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonbor.baselib.recyclerviewadapter.MultiItemTypeAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.baselib.recyclerviewadapter.wrapper.HeaderAndFooterWrapper;
import com.yonbor.baselib.widget.loading.LoadViewHelper;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.service.article.ArticlesAdapter;
import com.yonbor.mydicapp.activity.app.service.ProjectsActivity;
import com.yonbor.mydicapp.activity.base.BaseFragment;
import com.yonbor.mydicapp.activity.common.SearchActivity;
import com.yonbor.mydicapp.activity.common.WebActivity;
import com.yonbor.mydicapp.beauty.activity.MainTabActivity;
import com.yonbor.mydicapp.model.WanAndroidVo;
import com.yonbor.mydicapp.model.home.banner.BannerVo;
import com.yonbor.mydicapp.model.service.article.ArticleListVo;
import com.yonbor.mydicapp.model.service.article.ArticleVo;
import com.yonbor.mydicapp.net.http.HostType;
import com.yonbor.mydicapp.net.http.NetClient;
import com.yonbor.mydicapp.view.NetworkImageHolderView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * WanAndroid
 */
public class Service2Fragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private ArticlesAdapter adapter;
    ArrayList<BannerVo> bannerList = new ArrayList<>();
    private ArrayList<String> networkImages = new ArrayList<>();
    private int pageNum = 0;
    private int pageCount;
    private ArrayList<ArticleVo> articleList = new ArrayList<>();
    HeaderAndFooterWrapper headerAndFooterWrapper;
    View headerView;
    private ConvenientBanner convenientBanner;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_service2, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        setData();
        getBannerData();
        getArticleData(pageNum);


    }

    private void findView() {
        ((MainTabActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((MainTabActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        tvToolbarTitle.setText("玩Android");
        setHasOptionsMenu(true);

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

        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.rv_header_banner, null);
        convenientBanner = headerView.findViewById(R.id.convenientBanner);
        //设置高度是屏幕1/3
        convenientBanner.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getActivity().getWindowManager().getDefaultDisplay().getHeight() / 3));
    }

    private void setData() {
        adapter = new ArticlesAdapter();
        adapter.setOnItemClickListener(adapterListener);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        headerAndFooterWrapper.addHeaderView(headerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(ContextCompat.getColor(baseContext, R.color.transparent))
                        .sizeResId(R.dimen.dp0_6)
                        .marginResId(R.dimen.dp0, R.dimen.dp0)
                        .build());
        recyclerview.setAdapter(headerAndFooterWrapper);

        refreshLayout.setPrimaryColorsId(R.color.actionbar_bg, android.R.color.white);
        onRefresh();
        onLoadMore();
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
                getBannerData();
                pageNum = 0;
                getArticleData(pageNum);
                refreshLayout.finishRefresh(1000);
            }
        });
    }


    private void getBannerData() {
        NetClient.get(baseActivity, HostType.BASE_URL_SECOND, "banner/json", null, BannerVo.class, new NetClient.Listener2<ArrayList<BannerVo>>() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSuccess(WanAndroidVo<ArrayList<BannerVo>> result) {
                if (result.isSuccess()) {
                    if (!result.isEmpty()) {
                        bannerList.clear();
                        bannerList.addAll(result.data);
                        initBanner();
                    }
                } else {
                    onFaile(null);
                    showToast(result.getToast());
                }
            }

            @Override
            public void onFaile(Throwable t) {

            }
        });
    }

    private void initBanner() {

        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetworkImageHolderView createHolder(View itemView) {
                return new NetworkImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner;
            }
        }, bannerList)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent web = new Intent(baseContext, WebActivity.class);
                        web.putExtra("title", bannerList.get(position).getTitle());
                        web.putExtra("url", bannerList.get(position).getUrl());
                        startActivity(web);
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        // 开始自动翻页
        convenientBanner.startTurning();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止翻页
        convenientBanner.stopTurning();
    }


    private void getArticleData(int curPage) {
        NetClient.get(baseActivity, HostType.BASE_URL_SECOND, "article/list/" + curPage + "/json", null, ArticleListVo.class, new NetClient.Listener2<ArticleListVo>() {
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
                        headerAndFooterWrapper.notifyDataSetChanged();
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


    MultiItemTypeAdapter.OnItemClickListener adapterListener = new MultiItemTypeAdapter.OnItemClickListener<ArticleVo>() {
        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<ArticleVo> datas, int position) {
            Intent web = new Intent(baseContext, WebActivity.class);
            web.putExtra("title", datas.get(position - 1).getTitle());
            web.putExtra("url", datas.get(position - 1).getLink());
            startActivity(web);
        }

        @Override
        public void onItemViewClick(View view, ViewHolder holder, ArticleVo item, int position, int tPos) {

        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<ArticleVo> datas, int position) {
            return false;
        }
    };


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_service_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(baseActivity, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.action_category:
                Intent intent2 = new Intent(baseActivity, ProjectsActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}
