package com.yonbor.mydicapp.app;

import com.yonbor.baselib.base.BaseApplication;
import com.yonbor.baselib.base.AppContext;

public class AppApplication extends BaseApplication {

    public static AppApplication appApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initialize(getApplicationContext(), AppConstant.HttpApiUrl);
        appApplication = this;
    }
}
