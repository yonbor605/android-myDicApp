package com.yonbor.mydicapp.activity.app.home.rxjava2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.app.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

/**
 * ReplaySubject emits to any observer all of the items that were emitted
 * by the source Observable, regardless of when the observer subscribes.
 * ReplaySubject使得无论订阅者在何时订阅，它们都能收到被订阅者发射序列中的所有数据。
 */
@Route(path = "/home/rxjava2/replaySubjectExample")
public class ReplaySubjectExampleActivity extends BaseActivity {

    private static final String TAG = ReplaySubjectExampleActivity.class.getSimpleName();
    private Button btn;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        findView();
        setListener();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("ReplaySubject");
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

        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);
    }

    private void setListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomeWork();
            }
        });
    }

    private void doSomeWork() {

        ReplaySubject<Integer> source = ReplaySubject.create();

        source.subscribe(getFirstObserver()); // it will get 1, 2, 3, 4

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        /**
         * it will emit 1, 2, 3, 4 for second observer also as we have used replay
         */
        source.subscribe(getSecondObserver());

    }

    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                textView.append("---First onSubscribe---" + d.isDisposed());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---First onSubscribe---" + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                textView.append("---First onNext---Value is " + integer);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---First onNext---Value is " + integer);
            }

            @Override
            public void onError(Throwable e) {
                textView.append("---First onError---" + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---First onError---" + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append("---First onComplete---");
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---First onComplete---");
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                textView.append("---Second onSubscribe---" + d.isDisposed());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---Second onSubscribe---" + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                textView.append("---Second onNext---Value is " + integer);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---Second onNext---Value is " + integer);
            }

            @Override
            public void onError(Throwable e) {
                textView.append("---Second onError---" + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---Second onError---" + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append("---Second onComplete---");
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---Second onComplete---");
            }
        };
    }


}
