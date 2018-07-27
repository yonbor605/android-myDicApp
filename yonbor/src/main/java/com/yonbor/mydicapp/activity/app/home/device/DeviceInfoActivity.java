package com.yonbor.mydicapp.activity.app.home.device;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.rxtool.RxDeviceTool;
import com.yonbor.baselib.utils.EffectUtil;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/home/device/deviceInfo")
public class DeviceInfoActivity extends BaseActivity {

    @BindView(R.id.btn_get_phone_info)
    Button btnGetPhoneInfo;
    @BindView(R.id.tv_device_is_phone)
    TextView tvDeviceIsPhone;
    @BindView(R.id.tv_device_software_phone_type)
    TextView tvDeviceSoftwarePhoneType;
    @BindView(R.id.tv_device_density)
    TextView tvDeviceDensity;
    @BindView(R.id.tv_device_manu_facturer)
    TextView tvDeviceManuFacturer;
    @BindView(R.id.tv_device_width)
    TextView tvDeviceWidth;
    @BindView(R.id.tv_device_height)
    TextView tvDeviceHeight;
    @BindView(R.id.tv_device_version_name)
    TextView tvDeviceVersionName;
    @BindView(R.id.tv_device_version_code)
    TextView tvDeviceVersionCode;
    @BindView(R.id.tv_device_imei)
    TextView tvDeviceImei;
    @BindView(R.id.tv_device_imsi)
    TextView tvDeviceImsi;
    @BindView(R.id.tv_device_software_version)
    TextView tvDeviceSoftwareVersion;
    @BindView(R.id.tv_device_mac)
    TextView tvDeviceMac;
    @BindView(R.id.tv_device_software_mcc_mnc)
    TextView tvDeviceSoftwareMccMnc;
    @BindView(R.id.tv_device_software_mcc_mnc_name)
    TextView tvDeviceSoftwareMccMncName;
    @BindView(R.id.tv_device_software_sim_country_iso)
    TextView tvDeviceSoftwareSimCountryIso;
    @BindView(R.id.tv_device_sim_operator)
    TextView tvDeviceSimOperator;
    @BindView(R.id.tv_device_sim_serial_number)
    TextView tvDeviceSimSerialNumber;
    @BindView(R.id.tv_device_sim_state)
    TextView tvDeviceSimState;
    @BindView(R.id.tv_device_sim_operator_name)
    TextView tvDeviceSimOperatorName;
    @BindView(R.id.tv_device_subscriber_id)
    TextView tvDeviceSubscriberId;
    @BindView(R.id.tv_device_voice_mail_number)
    TextView tvDeviceVoiceMailNumber;
    @BindView(R.id.tv_device_adnroid_id)
    TextView tvDeviceAdnroidId;
    @BindView(R.id.tv_device_build_brand_model)
    TextView tvDeviceBuildBrandModel;
    @BindView(R.id.tv_device_build_manu_facturer)
    TextView tvDeviceBuildManuFacturer;
    @BindView(R.id.tv_device_build_brand)
    TextView tvDeviceBuildBrand;
    @BindView(R.id.tv_device_serial_number)
    TextView tvDeviceSerialNumber;
    @BindView(R.id.tv_device_iso)
    TextView tvDeviceIso;
    @BindView(R.id.tv_device_phone)
    TextView tvDevicePhone;
    @BindView(R.id.ll_info_root)
    LinearLayout llInfoRoot;

    private static final String TAG = "DeviceInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);
        findView();
        setListener();
    }


    private void setListener() {
        EffectUtil.addClickEffect(btnGetPhoneInfo);
        btnGetPhoneInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llInfoRoot.setVisibility(View.VISIBLE);
                getPhoneInfo();
                btnGetPhoneInfo.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("设备信息");
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

    private void getPhoneInfo() {
        if (RxDeviceTool.isPhone(this)) {
            tvDeviceIsPhone.setText("是");
        } else {
            tvDeviceIsPhone.setText("否");
        }

        tvDeviceSoftwarePhoneType.setText(RxDeviceTool.getPhoneType(this) + "");
        tvDeviceDensity.setText(RxDeviceTool.getScreenDensity(this) + "");
        tvDeviceManuFacturer.setText(RxDeviceTool.getUniqueSerialNumber() + "");
        tvDeviceWidth.setText(RxDeviceTool.getScreenWidth(this) + " ");
        tvDeviceHeight.setText(RxDeviceTool.getScreenHeight(this) + " ");
        tvDeviceVersionName.setText(RxDeviceTool.getAppVersionName(this) + "");
        tvDeviceVersionCode.setText(RxDeviceTool.getAppVersionNo(this) + "");
        tvDeviceImei.setText(RxDeviceTool.getDeviceIdIMEI(this) + "");
        tvDeviceImsi.setText(RxDeviceTool.getIMSI(this) + "");
        tvDeviceSoftwareVersion.setText(RxDeviceTool.getDeviceSoftwareVersion(this) + "");
        tvDeviceMac.setText(RxDeviceTool.getMacAddress(this));
        tvDeviceSoftwareMccMnc.setText(RxDeviceTool.getNetworkOperator(this) + "");
        tvDeviceSoftwareMccMncName.setText(RxDeviceTool.getNetworkOperatorName(this) + "");
        tvDeviceSoftwareSimCountryIso.setText(RxDeviceTool.getNetworkCountryIso(this) + "");
        tvDeviceSimOperator.setText(RxDeviceTool.getSimOperator(this) + "");
        tvDeviceSimSerialNumber.setText(RxDeviceTool.getSimSerialNumber(this) + "");
        tvDeviceSimState.setText(RxDeviceTool.getSimState(this) + "");
        tvDeviceSimOperatorName.setText(RxDeviceTool.getSimOperatorName(this) + "");
        tvDeviceSubscriberId.setText(RxDeviceTool.getSubscriberId(this) + "");
        tvDeviceVoiceMailNumber.setText(RxDeviceTool.getVoiceMailNumber(this) + "");
        tvDeviceAdnroidId.setText(RxDeviceTool.getAndroidId(this) + "");
        tvDeviceBuildBrandModel.setText(RxDeviceTool.getBuildBrandModel() + "");
        tvDeviceBuildManuFacturer.setText(RxDeviceTool.getBuildMANUFACTURER() + "");
        tvDeviceBuildBrand.setText(RxDeviceTool.getBuildBrand() + "");
        tvDeviceSerialNumber.setText(RxDeviceTool.getSerialNumber() + "");
        tvDeviceIso.setText(RxDeviceTool.getNetworkCountryIso(this) + "");
        tvDevicePhone.setText(RxDeviceTool.getLine1Number(this) + "");
    }





}
