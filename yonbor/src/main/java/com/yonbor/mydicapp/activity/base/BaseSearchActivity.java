package com.yonbor.mydicapp.activity.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.baselib.widget.LinearLineWrapLayout;
import com.yonbor.mydicapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/5/9.
 */

public abstract class BaseSearchActivity extends BaseFrameActivity {
    
    public final static String HINT = "hint";
    public final static String DATA_LIST = "data_list";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.et_search)
    protected EditText etSearch;
    @BindView(R.id.layContent)
    LinearLineWrapLayout layContent;
    @BindView(R.id.layClear)
    LinearLayout layClear;
    @BindView(R.id.layHis)
    LinearLayout layHis;
    @BindView(R.id.recyclerview)
    protected RecyclerView recyclerview;

    
    protected boolean hasHis;//是否有历史
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_search);
        ButterKnife.bind(this);
        initPtrFrameLayout();
        String hint = getIntent().getStringExtra(HINT);
        if(!TextUtils.isEmpty(hint)) {
            etSearch.setHint(hint);
        }
        setListener();
    }

    @Override
    public void findView() {
        hasHis = createHis(layContent);
        layHis.setVisibility(hasHis ? View.VISIBLE : View.GONE);
        
    }

    

    private void setListener(){
        EffectUtil.addClickEffect(ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        EffectUtil.addClickEffect(ivClear);
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setText("");
            }
        });
        EffectUtil.addClickEffect(layClear);
        layClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layContent.removeAllViews();
                hasHis = false;
                layHis.setVisibility(View.GONE);
                clearHis();
            }
        });
        
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = etSearch.getText().toString().trim();
                if(!TextUtils.isEmpty(key)){
                    ivClear.setVisibility(View.VISIBLE);
                }else{
                    ivClear.setVisibility(View.GONE);
                    showHisLay();
                }
                
            }
        });
        
        recyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//要加上这句，如果不加，会调用两次
                    String key = etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(key)) {
                        doSearch(key);
                    }else{
                        showToast("搜索内容不能为空");
                        etSearch.requestFocus();
                    }
                }
                return false;
            }
        });
        
        
        
    }

    protected void showHisLay(){
        if(hasHis){
            layHis.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }
    }
    protected void showResultLay(){
        layHis.setVisibility(View.GONE);
        recyclerview.setVisibility(View.VISIBLE);
    }
    

    protected abstract boolean createHis(LinearLineWrapLayout layContent);

    protected abstract void clearHis();

    protected abstract void doSearch(String key);

    protected void createHis(){
        hasHis = createHis(layContent);
    }


    //刷新操作
    public void onRefresh(){}

    //是否已经有数据
    public boolean isEmpty(){
        return false;
    }
    
}
