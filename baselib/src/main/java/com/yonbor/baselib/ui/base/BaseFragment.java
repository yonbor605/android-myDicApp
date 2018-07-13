package com.yonbor.baselib.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.yonbor.baselib.base.AppContext;

/**
 * Created by Administrator on 2018/7/12.
 */
public class BaseFragment extends RxFragment {
    protected RxPermissions rxPermissions;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(getActivity());
        
    }

    public void showToast(String text) {
        if (text != null && !text.trim().equals("")) {
            Toast.makeText(AppContext.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
    public void showToast(@StringRes int resId) {
        Toast.makeText(AppContext.getContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
