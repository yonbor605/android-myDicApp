package com.yonbor.mydicapp.beauty.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ServiceFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.actionbar)
    AppActionBar actionbar;
    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView recyclerview;

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
        mainView = inflater.inflate(R.layout.fragment_service, container, false);
        unbinder = ButterKnife.bind(this, mainView);
        return mainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();

    }

    private void findView() {
        actionbar.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.actionbar_bg));
        actionbar.setTitle("健康资讯");
        actionbar.addAction(new AppActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return "查看全部";
            }

            @Override
            public void performAction(View view) {

            }
        });
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
        unbinder.unbind();
        super.onDestroyView();
    }


}
