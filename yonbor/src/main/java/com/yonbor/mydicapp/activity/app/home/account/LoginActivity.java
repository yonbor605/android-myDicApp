package com.yonbor.mydicapp.activity.app.home.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.baselib.utils.ScaleAnimation;
import com.yonbor.baselib.utils.StringUtil;
import com.yonbor.baselib.widget.AdjustSizeLinearLayout;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.utils.RxBd;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

@Route(path = "/home/account/login")
public class LoginActivity extends BaseActivity {

    @BindView(R.id.actionbar)
    AppActionBar actionbar;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.iv_userclear)
    ImageView ivUserclear;
    @BindView(R.id.rl_dele_text)
    RelativeLayout rlDeleText;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    @BindView(R.id.iv_secret)
    ImageView ivSecret;
    @BindView(R.id.cb_ifcansee)
    CheckBox cbIfcansee;
    @BindView(R.id.rl_secret_text)
    RelativeLayout rlSecretText;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.rl_secret)
    RelativeLayout rlSecret;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.asll_login)
    AdjustSizeLinearLayout asllLogin;
    @BindView(R.id.mainView)
    LinearLayout mainView;

    private String digitsTxt, digitsNumber;
    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
        ButterKnife.bind(this);
        digitsTxt = getString(R.string.digits_idcard);
        digitsNumber = getString(R.string.digits_number);
        findView();
        setClick();

    }


    @Override
    public void findView() {
        actionbar.setBackAction(new AppActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }

            @Override
            public void performAction(View view) {
                back();
            }
        });

        actionbar.addAction(new AppActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return getString(R.string.register);
            }

            @Override
            public void performAction(View view) {
                hideKeyboard();
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
            }
        });

        etUser.setKeyListener(DigitsKeyListener.getInstance(digitsNumber));

        ivLogo.post(new Runnable() {
            @Override
            public void run() {
                scaleAnimation = new ScaleAnimation(2, ivLogo.getHeight(), ivLogo.getWidth(), new AccelerateInterpolator(), 250);
            }
        });

    }

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    private void setClick() {

        EffectUtil.addClickEffect(tvForgetPwd);
        tvForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etUser.getText().toString().length() == 0) {
                    ivUserclear.setVisibility(View.GONE);
                } else {
                    ivUserclear.setVisibility(View.VISIBLE);
                }
            }
        });

        ivUserclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUser.setText("");
                etUser.requestFocus();
                showKeyboard(etUser);
            }
        });

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cbIfcansee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbIfcansee.isChecked()) {
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etPwd.requestFocus();
                etPwd.setSelection(etPwd.getText().length());
            }
        });


        RxBd.click(tvLogin).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (StringUtil.isEmpty(etUser.getText().toString().trim())) {
                    etUser.requestFocus();
                    showToast(R.string.user_name_null);
                } else if (StringUtil.isEmpty(etPwd.getText().toString())) {
                    etPwd.requestFocus();
                    showToast(R.string.pwd_null);
                } else if (!StringUtil.isMobilPhoneNumber(etUser.getText().toString().trim())) {
                    etUser.requestFocus();
                    showToast("手机号码不合法");
                } else {
                    hideKeyboard();
                    login(etUser.getText().toString(), etPwd.getText().toString());
                }
            }
        });


        mainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (null != getCurrentFocus() && null != getCurrentFocus().getWindowToken()) {
                    hideKeyboard();
                }
                return false;
            }
        });


        asllLogin.setSoftKeyBoardListener(new AdjustSizeLinearLayout.SoftkeyBoardListener() {
            @Override
            public void keyBoardVisable(int move) {
                if (scaleAnimation != null) {
                    scaleAnimation.startAnimationIn(ivLogo);
                }
            }

            @Override
            public void keyBoardInvisable(int move) {
                if (scaleAnimation != null) {
                    scaleAnimation.startAnimationOut(ivLogo);
                }
            }
        });


    }

    private void login(String s, String s1) {

    }

}
