package com.yonbor.mydicapp.beauty.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yonbor.mydicapp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 首頁
 */
public class HomeFragment extends BaseFragment {

    Unbinder unbinder;


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
        mainView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }



}
