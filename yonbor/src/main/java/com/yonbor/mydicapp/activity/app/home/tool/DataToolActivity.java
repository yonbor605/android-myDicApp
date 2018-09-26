package com.yonbor.mydicapp.activity.app.home.tool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.rxtool.RxDataTool;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = "/home/tool/dataTool")
public class DataToolActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.btn_null)
    Button btnNull;
    @BindView(R.id.tv_null)
    TextView tvNull;
    @BindView(R.id.btn_null_obj)
    Button btnNullObj;
    @BindView(R.id.tv_null_obj)
    TextView tvNullObj;
    @BindView(R.id.btn_is_integer)
    Button btnIsInteger;
    @BindView(R.id.tv_is_integer)
    TextView tvIsInteger;
    @BindView(R.id.btn_is_double)
    Button btnIsDouble;
    @BindView(R.id.tv_is_double)
    TextView tvIsDouble;
    @BindView(R.id.btn_is_number)
    Button btnIsNumber;
    @BindView(R.id.tv_is_number)
    TextView tvIsNumber;
    @BindView(R.id.et_month)
    EditText etMonth;
    @BindView(R.id.et_day)
    EditText etDay;
    @BindView(R.id.btn_astro)
    Button btnAstro;
    @BindView(R.id.tv_astro)
    TextView tvAstro;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.btn_hind_mobile)
    Button btnHindMobile;
    @BindView(R.id.tv_hind_mobile)
    TextView tvHindMobile;
    @BindView(R.id.et_bank_card)
    EditText etBankCard;
    @BindView(R.id.btn_format_bank_card)
    Button btnFormatBankCard;
    @BindView(R.id.tv_format_bank_card)
    TextView tvFormatBankCard;
    @BindView(R.id.btn_format_bank_card_4)
    Button btnFormatBankCard4;
    @BindView(R.id.tv_format_bank_card_4)
    TextView tvFormatBankCard4;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.btn_getAmountValue)
    Button btnGetAmountValue;
    @BindView(R.id.tv_getAmountValue)
    TextView tvGetAmountValue;
    @BindView(R.id.btn_getRoundUp)
    Button btnGetRoundUp;
    @BindView(R.id.tv_getRoundUp)
    TextView tvGetRoundUp;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.btn_string_to_int)
    Button btnStringToInt;
    @BindView(R.id.tv_string_to_int)
    TextView tvStringToInt;
    @BindView(R.id.btn_string_to_long)
    Button btnStringToLong;
    @BindView(R.id.tv_string_to_long)
    TextView tvStringToLong;
    @BindView(R.id.btn_string_to_double)
    Button btnStringToDouble;
    @BindView(R.id.tv_string_to_double)
    TextView tvStringToDouble;
    @BindView(R.id.btn_string_to_float)
    Button btnStringToFloat;
    @BindView(R.id.tv_string_to_float)
    TextView tvStringToFloat;
    @BindView(R.id.btn_string_to_two_number)
    Button btnStringToTwoNumber;
    @BindView(R.id.tv_string_to_two_number)
    TextView tvStringToTwoNumber;
    @BindView(R.id.et_string)
    EditText etString;
    @BindView(R.id.btn_upperFirstLetter)
    Button btnUpperFirstLetter;
    @BindView(R.id.tv_upperFirstLetter)
    TextView tvUpperFirstLetter;
    @BindView(R.id.btn_lowerFirstLetter)
    Button btnLowerFirstLetter;
    @BindView(R.id.tv_lowerFirstLetter)
    TextView tvLowerFirstLetter;
    @BindView(R.id.btn_reverse)
    Button btnReverse;
    @BindView(R.id.tv_reverse)
    TextView tvReverse;
    @BindView(R.id.btn_toDBC)
    Button btnToDBC;
    @BindView(R.id.tv_toDBC)
    TextView tvToDBC;
    @BindView(R.id.btn_toSBC)
    Button btnToSBC;
    @BindView(R.id.tv_toSBC)
    TextView tvToSBC;
    @BindView(R.id.btn_oneCn2ASCII)
    Button btnOneCn2ASCII;
    @BindView(R.id.tv_oneCn2ASCII)
    TextView tvOneCn2ASCII;
    @BindView(R.id.btn_oneCn2PY)
    Button btnOneCn2PY;
    @BindView(R.id.tv_oneCn2PY)
    TextView tvOneCn2PY;
    @BindView(R.id.btn_getPYFirstLetter)
    Button btnGetPYFirstLetter;
    @BindView(R.id.tv_getPYFirstLetter)
    TextView tvGetPYFirstLetter;
    @BindView(R.id.btn_cn2PY)
    Button btnCn2PY;
    @BindView(R.id.tv_cn2PY)
    TextView tvCn2PY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_tool);
        ButterKnife.bind(this);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("数据处理工具");
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
    }


    @OnClick({R.id.btn_null, R.id.btn_null_obj, R.id.btn_is_integer, R.id.btn_is_double, R.id.btn_is_number, R.id.btn_astro, R.id.btn_hind_mobile, R.id.btn_format_bank_card, R.id.btn_format_bank_card_4, R.id.btn_getAmountValue, R.id.btn_getRoundUp, R.id.btn_string_to_int, R.id.btn_string_to_long, R.id.btn_string_to_double, R.id.btn_string_to_float, R.id.btn_string_to_two_number, R.id.btn_upperFirstLetter, R.id.btn_lowerFirstLetter, R.id.btn_reverse, R.id.btn_toDBC, R.id.btn_toSBC, R.id.btn_oneCn2ASCII, R.id.btn_oneCn2PY, R.id.btn_getPYFirstLetter, R.id.btn_cn2PY})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_null:
                tvNull.setText(RxDataTool.isNullString(mEditText.getText().toString()) + "");
                break;
            case R.id.btn_null_obj:
                tvNullObj.setText(RxDataTool.isEmpty(mEditText.getText().toString()) + "");
                break;
            case R.id.btn_is_integer:
                tvIsInteger.setText(RxDataTool.isInteger(mEditText.getText().toString()) + "");
                break;
            case R.id.btn_is_double:
                tvIsDouble.setText(RxDataTool.isDouble(mEditText.getText().toString()) + "");
                break;
            case R.id.btn_is_number:
                tvIsNumber.setText(RxDataTool.isNumber(mEditText.getText().toString()) + "");
                break;
            case R.id.btn_astro:
                tvAstro.setText(RxDataTool.getAstro(RxDataTool.stringToInt(etMonth.getText().toString()), RxDataTool.stringToInt(etDay.getText().toString())));
                break;
            case R.id.btn_hind_mobile:
                tvHindMobile.setText(RxDataTool.hideMobilePhone4(etMobile.getText().toString()));
                break;
            case R.id.btn_format_bank_card:
                tvFormatBankCard.setText(RxDataTool.formatCard(etBankCard.getText().toString()));
                break;
            case R.id.btn_format_bank_card_4:
                tvFormatBankCard4.setText(RxDataTool.formatCardEnd4(etBankCard.getText().toString()));
                break;
            case R.id.btn_getAmountValue:
                tvGetAmountValue.setText(RxDataTool.getAmountValue(etMoney.getText().toString()));
                break;
            case R.id.btn_getRoundUp:
                tvGetRoundUp.setText(RxDataTool.getRoundUp(etMoney.getText().toString(), 2));
                break;
            case R.id.btn_string_to_int:
                tvStringToInt.setText(RxDataTool.stringToInt(etText.getText().toString()) + "");
                break;
            case R.id.btn_string_to_long:
                tvStringToLong.setText(RxDataTool.stringToLong(etText.getText().toString()) + "");
                break;
            case R.id.btn_string_to_double:
                tvStringToDouble.setText(RxDataTool.stringToDouble(etText.getText().toString()) + "");
                break;
            case R.id.btn_string_to_float:
                tvStringToFloat.setText(RxDataTool.stringToFloat(etText.getText().toString()) + "");
                break;
            case R.id.btn_string_to_two_number:
                tvStringToTwoNumber.setText(RxDataTool.format2Decimals(etText.getText().toString()) + "");
                break;
            case R.id.btn_upperFirstLetter:
                tvUpperFirstLetter.setText(RxDataTool.upperFirstLetter(etString.getText().toString()));
                break;
            case R.id.btn_lowerFirstLetter:
                tvLowerFirstLetter.setText(RxDataTool.lowerFirstLetter(etString.getText().toString()));
                break;
            case R.id.btn_reverse:
                tvReverse.setText(RxDataTool.reverse(etString.getText().toString()));
                break;
            case R.id.btn_toDBC:
                tvToDBC.setText(RxDataTool.toDBC(etString.getText().toString()));
                break;
            case R.id.btn_toSBC:
                tvToSBC.setText(RxDataTool.toSBC(etString.getText().toString()));
                break;
            case R.id.btn_oneCn2ASCII:
                tvOneCn2ASCII.setText(RxDataTool.oneCn2ASCII(etString.getText().toString()) + "");
                break;
            case R.id.btn_oneCn2PY:
                tvOneCn2PY.setText(RxDataTool.oneCn2PY(etString.getText().toString()));
                break;
            case R.id.btn_getPYFirstLetter:
                tvGetPYFirstLetter.setText(RxDataTool.getPYFirstLetter(etString.getText().toString()));
                break;
            case R.id.btn_cn2PY:
                tvCn2PY.setText(RxDataTool.cn2PY(etString.getText().toString()));
                break;
        }
    }

}
