package com.yonbor.mydicapp.activity.app.home.contacts;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.ButterKnife;

import com.yonbor.mydicapp.R;

@Route(path = "/home/contacts/contacts")
public class ContactsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        findView();

    }

    @Override
    public void findView() {

    }
}
