package com.yonbor.mydicapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import com.yonbor.baselib.model.indicator.TabInfo;
import com.yonbor.baselib.widget.CountView;
import com.yonbor.baselib.widget.TitleIndicator;
import com.yonbor.mydicapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 标签页 可滑动
 *
 * @author Administrator
 */
public abstract class IndicatorFragmentActivity extends BaseActivity
        implements OnPageChangeListener {
    private static final String TAG = "DxFragmentActivity";

    public static final String EXTRA_TAB = "tab";
    public static final String EXTRA_QUIT = "extra.quit";

    protected int mCurrentTab = 0;
    protected int mLastTab = -1;

    // 默认显示第几页
    public int index = 0;

    // 存放选项卡信息的列表
    protected ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

    // viewpager adapter
    protected MyAdapter myAdapter = null;

    // viewpager
    protected ViewPager mPager;

    // 选项卡控件
    protected TitleIndicator mIndicator;

    public TitleIndicator getIndicator() {
        return mIndicator;
    }


    static class MyAdapter extends FragmentPagerAdapter {
        ArrayList<TabInfo> tabs = null;

        public MyAdapter(FragmentManager fm,
                         ArrayList<TabInfo> tabs) {
            super(fm);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment fragment = null;
            if (tabs != null && pos < tabs.size()) {
                TabInfo tab = tabs.get(pos);
                if (tab == null)
                    return null;
                fragment = tab.createFragment();
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            if (tabs != null && tabs.size() > 0)
                return tabs.size();
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabInfo tab = tabs.get(position);
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            tab.fragment = fragment;
            return fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        index = getIntent().getIntExtra("index", 0);
        setContentView(getMainViewResId());
        initViews();


        // //设置viewpager内部页面之间的间距
        // mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
        // //设置viewpager内部页面间距的drawable
        // mPager.setPageMarginDrawable(R.color.page_viewer_margin_color);
    }

    public int getCurrentItem() {
        return mPager.getCurrentItem();
    }

    @Override
    public void onDestroy() {
        mTabs.clear();
        mTabs = null;
        myAdapter.notifyDataSetChanged();
        myAdapter = null;
        mPager.setAdapter(null);
        mPager = null;
        mIndicator = null;

        super.onDestroy();
    }

    private final void initViews() {
        // 这里初始化界面
        mCurrentTab = supplyTabs(mTabs);
        Intent intent = getIntent();
        if (intent != null) {
            mCurrentTab = intent.getIntExtra(EXTRA_TAB, mCurrentTab);
        }
        Log.d(TAG, "mTabs.size() == " + mTabs.size() + ", cur: " + mCurrentTab);
        myAdapter = new MyAdapter(getSupportFragmentManager(), mTabs);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(myAdapter);
        mPager.setOnPageChangeListener(this);
        mPager.setOffscreenPageLimit(mTabs.size());

        mIndicator = (TitleIndicator) findViewById(R.id.pagerindicator);
        mIndicator.init(mCurrentTab, mTabs, mPager);

        mPager.setCurrentItem(mCurrentTab);
        mLastTab = mCurrentTab;
    }

    /**
     * 添加一个选项卡
     *
     * @param tab
     */
    public void addTabInfo(TabInfo tab) {
        mTabs.add(tab);
        myAdapter.notifyDataSetChanged();
        mIndicator.init(mCurrentTab, mTabs, mPager);
    }

    /**
     * 从列表添加选项卡
     *
     * @param tabs
     */
    public void addTabInfos(ArrayList<TabInfo> tabs) {
        mTabs.addAll(tabs);
        myAdapter.notifyDataSetChanged();
        mIndicator.init(mCurrentTab, mTabs, mPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        mIndicator.onScrolled((mPager.getWidth() + mPager.getPageMargin())
                * position + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.onSwitched(position);
        mCurrentTab = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mLastTab = mCurrentTab;
        }
    }

    protected TabInfo getFragmentById(int tabId) {
        if (mTabs == null)
            return null;
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            TabInfo tab = mTabs.get(index);
            if (tab.getId() == tabId) {
                return tab;
            }
        }
        return null;
    }

    /**
     * 跳转到任意选项卡
     *
     * @param tabId 选项卡下标
     */
    public void navigate(int tabId) {
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            if (mTabs.get(index).getId() == tabId) {
                mPager.setCurrentItem(index);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 返回layout id
     *
     * @return layout id
     */
    protected abstract int getMainViewResId();

    /**
     * 在这里提供要显示的选项卡数据
     */
    protected abstract int supplyTabs(List<TabInfo> tabs);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // for fix a known issue in support library
        // https://code.google.com/p/android/issues/detail?id=19917
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY",
                "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public CountView getCountView(int position) {
        return mIndicator.getCountView(position);
    }

    public ArrayList<TabInfo> getTabs() {
        return mTabs;
    }

}
