package com.yonbor.mydicapp.activity.app.home.code;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.rxtool.RxDataTool;
import com.yonbor.baselib.rxtool.RxToast;
import com.yonbor.baselib.utils.SharedPreferencesUtil;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.app.AppConstant;
import com.yonbor.mydicapp.utils.RxBarCode;
import com.yonbor.mydicapp.utils.RxQRCode;
import com.yonbor.mydicapp.view.ticker.RxTickerUtils;
import com.yonbor.mydicapp.view.ticker.RxTickerView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

@Route(path = "/home/code/codeTool")
public class CodeToolActivity extends BaseActivity {

    private static final char[] NUMBER_LIST = RxTickerUtils.getDefaultNumberList();
    private EditText mEtQrCode;
    private ImageView mIvCreateQrCode;
    private ImageView mIvQrCode;
    private LinearLayout mActivityCodeTool;
    private LinearLayout mLlCode;
    private LinearLayout mLlQrRoot;
    private EditText mEtBarCode;
    private ImageView mIvCreateBarCode;
    private ImageView mIvBarCode;
    private LinearLayout mLlBarCode;
    private LinearLayout mLlBarRoot;
    private LinearLayout mLlScaner;
    private LinearLayout mLlQr;
    private LinearLayout mLlBar;
    private RxTickerView mRxTickerViewMade;
    private RxTickerView mRxTickerViewScan;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_tool);
        findView();
        setListener();
    }

    private void setListener() {
        mIvCreateQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEtQrCode.getText().toString();
                if (RxDataTool.isNullString(str)) {
                    RxToast.error("二维码文字内容不能为空！");
                } else {
                    mLlCode.setVisibility(View.VISIBLE);

                    //二维码生成方式一  推荐此方法
                    RxQRCode.builder(str).
                            backColor(0xFFFFFFFF).
                            codeColor(0xFF000000).
                            codeSide(600).
                            into(mIvQrCode);

                    //二维码生成方式二 默认宽和高都为800 背景为白色 二维码为黑色
                    // RxQRCode.createQRCode(str,mIvQrCode);

                    mIvQrCode.setVisibility(View.VISIBLE);

                    RxToast.success("二维码已生成!");

                    SharedPreferencesUtil.getInstance().setStringData(AppConstant.SP_KEY_MADE_CODE, RxDataTool.stringToInt(SharedPreferencesUtil.getInstance().getStringData(AppConstant.SP_KEY_MADE_CODE)) + 1 + "");

                    updateMadeCodeCount();

                    nestedScrollView.computeScroll();
                }
            }
        });

        mIvCreateBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = mEtBarCode.getText().toString();
                if (RxDataTool.isNullString(str1)) {
                    RxToast.error("条形码文字内容不能为空！");
                } else {
                    mLlBarCode.setVisibility(View.VISIBLE);

                    //条形码生成方式一  推荐此方法
                    RxBarCode.builder(str1).
                            backColor(0x00000000).
                            codeColor(0xFF000000).
                            codeWidth(1000).
                            codeHeight(300).
                            into(mIvBarCode);

                    //条形码生成方式二  默认宽为1000 高为300 背景为白色 二维码为黑色
                    //mIvBarCode.setImageBitmap(RxBarCode.createBarCode(str1, 1000, 300));

                    mIvBarCode.setVisibility(View.VISIBLE);

                    RxToast.success("条形码已生成!");

                    SharedPreferencesUtil.getInstance().setStringData(AppConstant.SP_KEY_MADE_CODE, RxDataTool.stringToInt(SharedPreferencesUtil.getInstance().getStringData(AppConstant.SP_KEY_MADE_CODE)) + 1 + "");

                    updateMadeCodeCount();
                }
            }
        });

        mLlScaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions.request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent(baseContext, ScanActivity.class);
                                    startActivity(intent);
                                } else {
                                    showToast("获取相机权限失败");
                                }
                            }
                        });
            }
        });

        mLlQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlQrRoot.setVisibility(View.VISIBLE);
                mLlBarRoot.setVisibility(View.GONE);
            }
        });

        mLlBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlBarRoot.setVisibility(View.VISIBLE);
                mLlQrRoot.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("扫码工具");
        actionBar.setBackAction(new AppActionBar.Action() {

            @Override
            public void performAction(View view) {
                back();
            }

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }
        });


        mEtQrCode = findViewById(R.id.et_qr_code);
        mIvCreateQrCode = findViewById(R.id.iv_create_qr_code);
        mIvQrCode = findViewById(R.id.iv_qr_code);
        mActivityCodeTool = findViewById(R.id.activity_code_tool);
        mLlCode = findViewById(R.id.ll_code);
        mLlQrRoot = findViewById(R.id.ll_qr_root);

        nestedScrollView = findViewById(R.id.nestedScrollView);

        mEtBarCode = findViewById(R.id.et_bar_code);
        mIvCreateBarCode = findViewById(R.id.iv_create_bar_code);
        mIvBarCode = findViewById(R.id.iv_bar_code);
        mLlBarCode = findViewById(R.id.ll_bar_code);
        mLlBarRoot = findViewById(R.id.ll_bar_root);
        mLlScaner = findViewById(R.id.ll_scaner);
        mLlQr = findViewById(R.id.ll_qr);
        mLlBar = findViewById(R.id.ll_bar);

        mRxTickerViewScan = findViewById(R.id.ticker_scan_count);
        mRxTickerViewScan.setCharacterList(NUMBER_LIST);

        mRxTickerViewMade = findViewById(R.id.ticker_made_count);
        mRxTickerViewMade.setCharacterList(NUMBER_LIST);
        updateMadeCodeCount();
    }

    private void updateScanCodeCount() {
        mRxTickerViewScan.setText(RxDataTool.stringToInt(SharedPreferencesUtil.getInstance().getStringData(AppConstant.SP_KEY_SCAN_CODE)) + "", true);
    }

    private void updateMadeCodeCount() {
        mRxTickerViewMade.setText(RxDataTool.stringToInt(SharedPreferencesUtil.getInstance().getStringData(AppConstant.SP_KEY_MADE_CODE)) + "", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScanCodeCount();
    }
}
