package com.yonbor.mydicapp.activity.app.service;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yonbor.baselib.model.http.ResultModel;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.app.ConstantsHttp;
import com.yonbor.mydicapp.model.service.TagVo;
import com.yonbor.mydicapp.net.http.NetClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 健康资讯/健康活动
 */
public class HealthyNewsActivity extends BaseActivity {

    @BindView(R.id.actionbar)
    AppActionBar actionBar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private TabAdapter adapter;

    List<TagVo> tagVos;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_news);
        ButterKnife.bind(this);
        findView();
        getData();
    }


    @Override
    public void findView() {
        actionBar.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.actionbar_bg));
        actionBar.setTitle("健康资讯");
        actionBar.setBackAction(new AppActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }

            @Override
            public void performAction(View view) {
                back();
            }
        });
        tab.setupWithViewPager(viewpager);
        fragments = new ArrayList<>();
    }


    public void getData() {
        ArrayMap<String, String> head = new ArrayMap<>();
        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Health_News_Service);
        head.put(ConstantsHttp.Head_Method, "listTags");
        head.put(ConstantsHttp.Head_Token, "");
        ArrayList body = new ArrayList();
        body.add("healthNewsType");
        NetClient.post(baseActivity, ConstantsHttp.Json_Request, head, body, TagVo.class,
                new NetClient.Listener<ArrayList<TagVo>>() {
                    @Override
                    public void onPrepare() {

                    }

                    @Override
                    public void onSuccess(ResultModel<ArrayList<TagVo>> result) {
                        if (result.isSuccess()) {
                            if (null != result.data && result.data.size() > 0) {
                                initTags(result.data);
                                return;
                            } else {
                                initTags(new ArrayList<TagVo>());
                            }
                        } else {
                            showToast(result.getToast());
                        }

                    }

                    @Override
                    public void onFaile(Throwable t) {

                    }
                });
    }


    private void initTags(List<TagVo> tags) {
        TagVo all = new TagVo("0", 0, "最新", "hcn.zhongshan", "healthNewsType", -1);
        tags.add(0, all);


        tagVos = new ArrayList<>();
        //动态添加标签
        for (TagVo tagVo : tags) {
            tagVos.add(tagVo);
            HealthyNewsFragment oneFragment = new HealthyNewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tagCode", tagVo.tagCode);
            oneFragment.setArguments(bundle);
            fragments.add(oneFragment);
        }
        adapter = new TabAdapter(getSupportFragmentManager(), fragments);
        int size = fragments.size();
        if (size == 1) {//暂时未能解决一个tab占满屏幕
            tab.setVisibility(View.GONE);
            findViewById(R.id.default_tab).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.top)).setText(tagVos.get(0).tagName);
        } else {
            if (size <= 6) {
                tab.setTabMode(TabLayout.MODE_FIXED);
            } else {
                tab.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
        }
        viewpager.setOffscreenPageLimit(fragments.size());
        viewpager.setAdapter(adapter);
    }

    public class TabAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public TabAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tagVos.get(position).tagName;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
