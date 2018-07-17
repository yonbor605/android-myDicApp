package com.yonbor.mydicapp.activity.app.home.code;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.mydicapp.R;

@Route(path = "/home/code/codeTool")
public class CodeToolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_tool);
    }
}
