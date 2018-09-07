package com.yonbor.mydicapp.activity.app.service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseFragment;


public class ProjectsFragment extends BaseFragment {

    private SmartRefreshLayout refreshLayout;
    private SwipeMenuRecyclerView recyclerview;
    private int cid;

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
        getData();
    }

    private void findView() {
        refreshLayout = mainView.findViewById(R.id.refreshLayout);
        recyclerview = mainView.findViewById(R.id.recyclerview);


    }

    private void getData() {

    }
}
