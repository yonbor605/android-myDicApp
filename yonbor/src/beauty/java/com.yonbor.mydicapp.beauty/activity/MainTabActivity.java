package com.yonbor.mydicapp.beauty.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.yonbor.baselib.utils.ExitUtil;
import com.yonbor.baselib.widget.viewpager.LRViewPager;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.app.AppApplication;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.beauty.fragment.HomeFragment;
import com.yonbor.mydicapp.beauty.fragment.ServiceFragment;
import com.yonbor.mydicapp.beauty.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class MainTabActivity extends BaseActivity {

    public static boolean isForeground = false;
    LRViewPager mViewPager;
    RelativeLayout lay1, lay2, lay3;
    MyPagerAdapter myPagerAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    ArrayList<ImageView> normalFoots = new ArrayList<ImageView>();
    ArrayList<ImageView> selectedFoots = new ArrayList<ImageView>();
    ArrayList<TextView> normalFoots2 = new ArrayList<TextView>();
    ArrayList<TextView> selectedFoots2 = new ArrayList<TextView>();

    AppApplication application;
    LayoutInflater mInflater;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainTabActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        isForeground = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        this.mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        application = (AppApplication) getApplication();
        findView();
        requestNecessaryPermissions();
//        changeIndex(2);
//        mViewPager.setCurrentItem(2, false);
    }


    @Override
    public void findView() {
        mViewPager = (LRViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        lay1 = (RelativeLayout) findViewById(R.id.lay1);
        lay2 = (RelativeLayout) findViewById(R.id.lay2);
        lay3 = (RelativeLayout) findViewById(R.id.lay3);
        TopTabClickListener clickListener = new TopTabClickListener();
        lay1.setOnClickListener(clickListener);
        lay2.setOnClickListener(clickListener);
        lay3.setOnClickListener(clickListener);

        normalFoots.add((ImageView) findViewById(R.id.f1_n));
        normalFoots.add((ImageView) findViewById(R.id.f2_n));
        normalFoots.add((ImageView) findViewById(R.id.f3_n));

        normalFoots2.add((TextView) findViewById(R.id.f1_t_n));
        normalFoots2.add((TextView) findViewById(R.id.f2_t_n));
        normalFoots2.add((TextView) findViewById(R.id.f3_t_n));

        selectedFoots.add((ImageView) findViewById(R.id.f1_p));
        selectedFoots.add((ImageView) findViewById(R.id.f2_p));
        selectedFoots.add((ImageView) findViewById(R.id.f3_p));

        selectedFoots2.add((TextView) findViewById(R.id.f1_t_p));
        selectedFoots2.add((TextView) findViewById(R.id.f2_t_p));
        selectedFoots2.add((TextView) findViewById(R.id.f3_t_p));

        ServiceFragment messgaeFragment = new ServiceFragment();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(messgaeFragment);
        mFragmentList.add(new MyFragment());


        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setChangeViewCallback(new LRViewPager.ChangeViewCallback() {

            @Override
            public void getCurrentPageIndex(int index) {
                changeIndex(index);
            }

            @Override
            public void changeView(boolean left, boolean right, int position,
                                   float positionOffset) {
                if (positionOffset > 0) {
                    ViewHelper.setAlpha(normalFoots.get(position),
                            positionOffset);
                    ViewHelper.setAlpha(selectedFoots.get(position),
                            1 - positionOffset);
                    ViewHelper.setAlpha(normalFoots.get(position + 1),
                            1 - positionOffset);
                    ViewHelper.setAlpha(selectedFoots.get(position + 1),
                            positionOffset);

                    ViewHelper.setAlpha(normalFoots2.get(position),
                            positionOffset);
                    ViewHelper.setAlpha(selectedFoots2.get(position),
                            1 - positionOffset);
                    ViewHelper.setAlpha(normalFoots2.get(position + 1),
                            1 - positionOffset);
                    ViewHelper.setAlpha(selectedFoots2.get(position + 1),
                            positionOffset);
                }
            }
        });
    }

    private class TopTabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.lay1) {
                mViewPager.setCurrentItem(0, false);
            } else if (view.getId() == R.id.lay2) {
                mViewPager.setCurrentItem(1, false);
            } else if (view.getId() == R.id.lay3) {
                mViewPager.setCurrentItem(2, false);
            }
            changeIndex(mViewPager.getCurrentItem());
        }
    }


    private void changeIndex(int index) {
        for (int i = 0; i < mFragmentList.size(); i++) {
            if (i == index) {
                ViewHelper.setAlpha(normalFoots.get(index), 0);
                ViewHelper.setAlpha(selectedFoots.get(index), 1);

                ViewHelper.setAlpha(normalFoots2.get(index), 0);
                ViewHelper.setAlpha(selectedFoots2.get(index), 1);
            } else {
                ViewHelper.setAlpha(normalFoots.get(i), 1);
                ViewHelper.setAlpha(selectedFoots.get(i), 0);

                ViewHelper.setAlpha(normalFoots2.get(i), 1);
                ViewHelper.setAlpha(selectedFoots2.get(i), 0);
            }
        }
    }

    static private class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
            super(fm);
            this.mFragmentList = mFragmentList;
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            return (mFragmentList == null || mFragmentList.size() == 0) ? null
                    : mFragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragmentList == null ? 0 : mFragmentList.size();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land do nothing is ok
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port do nothing is ok
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mViewPager.getCurrentItem() != 0) {
                mViewPager.setCurrentItem(0, false);
            } else {
                ExitUtil.ExitApp(this);
            }
            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        isForeground = false;
        System.gc();
        super.onDestroy();
    }

    private void requestNecessaryPermissions() {
        rxPermissions
                .requestEach(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // `permission.name` is granted !
                            Logger.d(permission.name + " is granted !");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                            Logger.d(permission.name + " is denied without ask never again !");
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            Logger.d(permission.name + " is denied with ask never again !");
                            showDialog("提示", "请到应用信息-权限中手动开启权限", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 应用信息页面
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            },true);
                        }
                    }
                });
    }
}
