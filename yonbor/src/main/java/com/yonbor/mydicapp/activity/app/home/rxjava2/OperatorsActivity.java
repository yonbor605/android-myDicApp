package com.yonbor.mydicapp.activity.app.home.rxjava2;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;

@Route(path = "/home/rxjava2/operators")
public class OperatorsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);
        findView();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("操作符");
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

    public void startSimpleActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/simpleExample").navigation();
    }

    public void startMapActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/mapExample").navigation();
    }

    public void startZipActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/zipExample").navigation();
    }

    public void startDisposableActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/disposableExample").navigation();
    }

    public void startTakeActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/takeExample").navigation();
    }

    public void startTimerActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/timerExample").navigation();
    }

    public void startIntervalActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/intervalExample").navigation();
    }

    public void startSingleObserverActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/singleObserverExample").navigation();
    }

    public void startCompletableObserverActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/completableObserverExample").navigation();
    }

    public void startFlowableActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/flowableExample").navigation();
    }

    public void startReduceActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/reduceExample").navigation();
    }

    public void startBufferActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/bufferExample").navigation();
    }

    public void startFilterActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/filterExample").navigation();
    }

    public void startSkipActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/skipExample").navigation();
    }

    public void startScanActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/scanExample").navigation();
    }

    public void startReplayActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/replayExample").navigation();
    }

    public void startConcatActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/concatExample").navigation();
    }

    public void startMergeActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/mergeExample").navigation();
    }

    public void startDeferActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/deferExample").navigation();
    }

    public void startDistinctActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/distinctExample").navigation();
    }

    public void startLastOperatorActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/lastOperatorExample").navigation();
    }

    public void startReplaySubjectActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/replaySubjectExample").navigation();
    }

    public void startPublishSubjectActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/publishSubjectExample").navigation();
    }

    public void startBehaviorSubjectActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/behaviorSubjectExample").navigation();
    }

    public void startAsyncSubjectActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/asyncSubjectExample").navigation();
    }

    public void startThrottleFirstActivity(View view) {
        ARouter.getInstance().build("/home/rxjava2/throttleFirstExample").navigation();
    }

    public void startThrottleLastActivity(View view) {
//        ARouter.getInstance().build("/home/rxjava2/throttleLastExample").navigation();
    }

    public void startDebounceActivity(View view) {
//        ARouter.getInstance().build("/home/rxjava2/debounceExample").navigation();
    }

    public void startWindowActivity(View view) {
//        ARouter.getInstance().build("/home/rxjava2/windowExample").navigation();
    }

    public void startDelayActivity(View view) {
//        ARouter.getInstance().build("/home/rxjava2/delayExample").navigation();
    }

    public void startSwitchMapActivity(View view) {
//        ARouter.getInstance().build("/home/rxjava2/switchMapExample").navigation();
    }
}
