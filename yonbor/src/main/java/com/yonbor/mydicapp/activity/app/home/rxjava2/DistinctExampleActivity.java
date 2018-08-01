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

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * distinct() suppresses duplicate items emitted by the source Observable.
 * distinct会过滤掉源Observable已经发射过的数据，只有判断该数据没有发射过，才会递交给下游的Observer。
 */
@Route(path = "/home/rxjava2/distinctExample")
public class DistinctExampleActivity extends BaseActivity {

    private static final String TAG = DistinctExampleActivity.class.getSimpleName();
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
        actionBar.setTitle("Distinct");
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

        getObservable()
                .distinct()
                .subscribe(getObserver());

    }

    private Observable<Integer> getObservable() {
        return Observable.just(1, 2, 1, 1, 2, 3, 4, 6, 4);
    }

    private Observer<Integer> getObserver() {
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
}
