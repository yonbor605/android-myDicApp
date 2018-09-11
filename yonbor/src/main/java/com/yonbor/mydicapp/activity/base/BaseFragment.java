package com.yonbor.mydicapp.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yonbor.baselib.widget.loading.LoadViewHelper;
import com.yonbor.mydicapp.app.AppApplication;
import com.yonbor.mydicapp.model.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public abstract class BaseFragment extends com.yonbor.baselib.ui.base.BaseFragment {
    public View mainView;
    public boolean isCreated = false;//界面是否加载完成
    public boolean isLoaded = false;//数据是否加载过了
    public boolean isReceiver = false;//是否可见
    public AppApplication application;
    public Context baseContext;
    public BaseActivity baseActivity;
    public LoadViewHelper viewHelper;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isReceiver = isVisibleToUser;
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            startHint();
        } else {
            endHint();
        }
    }

    public abstract void startHint();

    public abstract void endHint();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseContext = getActivity();
        baseActivity = (BaseActivity) getActivity();
        application = (AppApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCreated = true;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showErrorView() {
        showErrorView("加载失败，点击重试");
    }

    public void showErrorView(String error) {
        if (null != viewHelper) {
            viewHelper.showError(error);
        }
    }

    public void showEmptyView() {
        if (null != viewHelper) {
            viewHelper.showEmpty();
        }
    }

    public void showEmptyView(View view) {
        if (null != viewHelper) {
            viewHelper.showEmpty(view);
        }
    }

    public void showLoadingView() {
        if (null != viewHelper) {
            viewHelper.showLoading();
        }
    }

    public void restoreView() {
        if (null != viewHelper) {
            viewHelper.restore();
        }
    }


    public void hideKeyboard() {
        if (null != getActivity().getCurrentFocus()
                && null != getActivity().getCurrentFocus().getWindowToken()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getActivity().getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
    }

    public void showToast(String text) {
        if (text != null && !text.trim().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }


}
