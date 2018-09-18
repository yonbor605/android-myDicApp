package com.yonbor.mydicapp.activity.common;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.model.WanAndroidVo;
import com.yonbor.mydicapp.model.search.HotKeyVo;
import com.yonbor.mydicapp.net.http.HostType;
import com.yonbor.mydicapp.net.http.NetClient;
import com.yonbor.mydicapp.utils.CommonUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        findView();
        setListener();
        getHotKeyData();
    }

    private void setListener() {
        hotSearchFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {


                return true;
            }
        });
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

    private void goToSearchList(String s) {

    }
}
