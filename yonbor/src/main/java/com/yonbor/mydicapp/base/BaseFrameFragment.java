package com.yonbor.mydicapp.base;

import android.view.View;
import android.widget.Toast;

import com.yonbor.baselib.utils.DensityUtil;
import com.yonbor.baselib.widget.loading.LoadViewHelper;
import com.yonbor.mydicapp.R;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Administrator on 2016/7/20.
 */
public abstract class BaseFrameFragment extends
        BaseFragment {
    public PtrFrameLayout frame;
    String loadingText = "Loading...";
    protected static final int FIRST_PAGE = 1;
    protected int pageNo = FIRST_PAGE;
    protected int pageSize = 20;

    public void initPtrFrameLayout( View mainView){
        frame = (PtrFrameLayout) mainView.findViewById(R.id.ptrFrameLayout);

        if(frame != null) {
            final StoreHouseHeader header = new StoreHouseHeader(baseContext);
            header.setPadding(0, DensityUtil.dip2px(baseContext, 15), 0, 0);
            header.setTextColor(R.color.gray);

            /**
             * using a string, support: A-Z 0-9 - .
             * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
             */
            header.initWithString(loadingText);

            frame.addPtrUIHandler(new PtrUIHandler() {


                @Override
                public void onUIReset(PtrFrameLayout frame) {
                    header.initWithString(loadingText);
                }

                @Override
                public void onUIRefreshPrepare(PtrFrameLayout frame) {


                }

                @Override
                public void onUIRefreshBegin(PtrFrameLayout frame) {

                }

                @Override
                public void onUIRefreshComplete(PtrFrameLayout frame) {

                }

                @Override
                public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

                }
            });

            frame.setHeaderView(header);
            frame.addPtrUIHandler(header);
//        frame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                frame.autoRefresh(false);
//            }
//        }, 50);

            frame.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }

                @Override
                public void onRefreshBegin(final PtrFrameLayout frame) {
                    onRefresh();
                }
            });
        }
        View base=mainView.findViewById(R.id.loadLay);
        if (base!=null){
            viewHelper = new LoadViewHelper(base);
            viewHelper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        }
    }

    public void setEnable(boolean flag){
        if(frame != null)
            frame.setEnabled(flag);
    }

    public void refreshComplete(){
        if(frame != null)
            frame.refreshComplete();
    }

    public boolean isFirstPage(){
        return pageNo == FIRST_PAGE;
    }

    public void showErrorView() {
        if(pageNo != FIRST_PAGE){
            pageNo --;
        }
        if(isEmpty()){
            super.showErrorView();
        }else{
            Toast.makeText(baseContext, "加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void showErrorView(String error) {
        if(pageNo != FIRST_PAGE){
            pageNo --;
        }
        if(isEmpty()){
            super.showErrorView(error);
        }else{
            Toast.makeText(baseContext,error, Toast.LENGTH_SHORT).show();
        }
    }

    public void showEmptyView() {
        if(isEmpty()){
            super.showEmptyView();
        }else{
            Toast.makeText(baseContext,"数据为空", Toast.LENGTH_SHORT).show();
        }
    }
    public void showEmptyView(View view){
        if(isEmpty()){
            super.showEmptyView(view);
        }else{
            Toast.makeText(baseContext,"数据为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void showLoadingView() {
        if(isEmpty()){
            super.showLoadingView();
        }
    }

    //刷新操作
    public abstract void onRefresh();
    //加载更多操作
    protected void onLoadMore(){};
    //是否已经有数据
    public abstract boolean isEmpty();
}
