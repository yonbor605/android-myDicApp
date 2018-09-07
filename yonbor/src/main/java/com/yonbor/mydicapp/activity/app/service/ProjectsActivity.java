package com.yonbor.mydicapp.activity.app.service;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.model.WanAndroidVo;
import com.yonbor.mydicapp.model.service.project.ProjectsTagVo;
import com.yonbor.mydicapp.net.http.HostType;
import com.yonbor.mydicapp.net.http.NetClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * WanAndroid--项目
 */
public class ProjectsActivity extends BaseActivity {

    @BindView(R.id.actionbar)
    AppActionBar actionBar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private TabAdapter adapter;

    List<ProjectsTagVo> tagVos;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        ButterKnife.bind(this);
        findView();
        taskGetTag();
    }

    @Override
    public void findView() {
        actionBar.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.actionbar_bg));
        actionBar.setTitle("项目");
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


    private void taskGetTag() {
        NetClient.get(baseActivity, HostType.BASE_URL_SECOND, "project/tree/json", null, ProjectsTagVo.class, new NetClient.Listener2<ArrayList<ProjectsTagVo>>() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSuccess(WanAndroidVo<ArrayList<ProjectsTagVo>> result) {
                if (result.isSuccess()) {
                    if (!result.isEmpty()) {
                        initProjectsTag(result.data);
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

    private void initProjectsTag(ArrayList<ProjectsTagVo> tags) {

        tagVos = new ArrayList<>();
        // 动态添加标签
        for (ProjectsTagVo projectsTagVo : tags) {
            tagVos.add(projectsTagVo);
            ProjectsFragment oneFragment = new ProjectsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("cid", projectsTagVo.id);
            oneFragment.setArguments(bundle);
            fragments.add(oneFragment);
        }
        adapter = new TabAdapter(getSupportFragmentManager(), fragments);
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
            return tagVos.get(position).name;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
