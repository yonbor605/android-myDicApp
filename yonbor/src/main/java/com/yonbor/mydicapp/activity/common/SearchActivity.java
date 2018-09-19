package com.yonbor.mydicapp.activity.common;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonbor.baselib.recyclerviewadapter.MultiItemTypeAdapter;
import com.yonbor.baselib.recyclerviewadapter.base.ViewHolder;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.adapter.search.SearchHistoryAdapter;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.model.WanAndroidVo;
import com.yonbor.mydicapp.model.search.HotKeyVo;
import com.yonbor.mydicapp.model.search.SearchHistoryVo;
import com.yonbor.mydicapp.net.http.HostType;
import com.yonbor.mydicapp.net.http.NetClient;
import com.yonbor.mydicapp.utils.CommonUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.litepal.LitePal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.hot_search_flow_layout)
    TagFlowLayout hotSearchFlowLayout;
    @BindView(R.id.search_history_clear_all_tv)
    TextView searchHistoryClearAllTv;
    @BindView(R.id.rv_history_search)
    RecyclerView rvHistorySearch;
    @BindView(R.id.search_scroll_view)
    NestedScrollView searchScrollView;

    private ArrayList<HotKeyVo> hotKeyList = new ArrayList<>();
    private SearchHistoryAdapter adapter;
    private ArrayList<SearchHistoryVo> searchHistoryList = new ArrayList<>();
    private View mEmptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        findView();
        setListener();
        getHotKeyData();
    }


    @Override
    public void findView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        mEmptyLayout = findViewById(R.id.empty);
        adapter = new SearchHistoryAdapter();
        adapter.setOnItemClickListener(adapterListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHistorySearch.setLayoutManager(linearLayoutManager);
        rvHistorySearch.setAdapter(adapter);
    }

    private void setListener() {
        hotSearchFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (hotKeyList.size() > 0) {
                    HotKeyVo hotKeyVo = hotKeyList.get(position);
                    goToSearchList(hotKeyVo.getName());
                    return true;
                }
                return false;
            }
        });


        searchHistoryClearAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHistoryList.clear();
                adapter.setDatas(searchHistoryList);
                LitePal.deleteAll(SearchHistoryVo.class);
                rvHistorySearch.setVisibility(View.GONE);
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        querySearchHistory();
    }

    private void querySearchHistory() {
        List<SearchHistoryVo> all = LitePal.findAll(SearchHistoryVo.class);
        Collections.reverse(all);
        searchHistoryList = (ArrayList<SearchHistoryVo>) all;
        if (searchHistoryList.size() > 0) {
            adapter.setDatas(searchHistoryList);
            rvHistorySearch.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.GONE);
        } else {
            rvHistorySearch.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.VISIBLE);
        }
    }


    private void getHotKeyData() {
        NetClient.get(baseActivity, HostType.BASE_URL_SECOND, "hotkey/json", null, HotKeyVo.class, new NetClient.Listener2<ArrayList<HotKeyVo>>() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSuccess(WanAndroidVo<ArrayList<HotKeyVo>> result) {
                if (result.isSuccess()) {
                    if (!result.isEmpty()) {
                        hotKeyList = result.data;
                        showHotKeyData(hotKeyList);
                    }
                } else {
                    result.getToast();
                }
            }

            @Override
            public void onFaile(Throwable t) {

            }
        });

    }

    private void showHotKeyData(ArrayList<HotKeyVo> hotKeyList) {
        hotSearchFlowLayout.setAdapter(new TagAdapter<HotKeyVo>(hotKeyList) {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyVo hotKeyVo) {
                TextView tv = (TextView) LayoutInflater.from(baseActivity).inflate(R.layout.flow_layout_tv, parent, false);
                if (hotKeyVo != null) {
                    String name = hotKeyVo.getName();
                    tv.setText(name);
                    setItemBackground(tv);
                }
                return tv;
            }

        });
    }

    private void setItemBackground(TextView tv) {
        tv.setBackgroundColor(CommonUtil.randomTagColor());
        tv.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();
        searchView.setQueryHint(getString(R.string.search_tint));
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setSubmitButtonEnabled(true);

        try {
            Field field = searchView.getClass().getDeclaredField("mGoButton");
            field.setAccessible(true);
            ImageView mGoButton = (ImageView) field.get(searchView);
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return super.onCreateOptionsMenu(menu);
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            goToSearchList(query.toString());
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private void goToSearchList(String key) {
        saveSearchKey(key);
        // 跳转到搜索结果列表页面
        Intent intent = new Intent(baseActivity, SearchResultActivity.class);
        intent.putExtra("searchKey", key);
        startActivity(intent);
    }

    private void saveSearchKey(String key) {
        SearchHistoryVo searchHistoryVo = new SearchHistoryVo();
        searchHistoryVo.setKey(key.trim());
        List<SearchHistoryVo> searchHistoryVos = LitePal.where("key = ?", key.trim()).find(SearchHistoryVo.class);
        if (searchHistoryVos.size() == 0) {
            searchHistoryVo.save();
        } else {
            LitePal.delete(SearchHistoryVo.class, searchHistoryVos.get(0).getId());
            searchHistoryVo.save();
        }
    }


    MultiItemTypeAdapter.OnItemClickListener adapterListener = new MultiItemTypeAdapter.OnItemClickListener<SearchHistoryVo>() {

        @Override
        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<SearchHistoryVo> datas, int position) {
            if (datas.size() > 0) {
                SearchHistoryVo searchHistoryVo = datas.get(position);
                goToSearchList(searchHistoryVo.getKey());
            }

        }

        @Override
        public void onItemViewClick(View view, ViewHolder holder, SearchHistoryVo searchHistoryVo, int position, int tPos) {
            switch (view.getId()) {
                case R.id.iv_clear:
                    LitePal.delete(SearchHistoryVo.class, searchHistoryVo.getId());
                    adapter.remove(position);
                    break;
            }

        }

        @Override
        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<SearchHistoryVo> datas, int position) {
            return false;
        }
    };
}
