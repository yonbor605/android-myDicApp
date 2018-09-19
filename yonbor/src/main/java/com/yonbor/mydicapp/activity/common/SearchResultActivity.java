package com.yonbor.mydicapp.activity.common;

import android.os.Bundle;

import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity {

    private String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        searchKey = getIntent().getStringExtra("searchKey");
        findView();
    }

    @Override
    public void findView() {

    }
}
