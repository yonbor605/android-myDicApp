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
import com.yonbor.mydicapp.cache.ModelCache;
import com.yonbor.mydicapp.model.home.rxjava2.ApiUser;
import com.yonbor.mydicapp.model.home.rxjava2.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Using replay operator, replay ensure that all observers see the same sequence
 * of emitted items, even if they subscribe after the Observable has begun emitting items
 * replay(n)，使得即使在未订阅时，被订阅者已经发射了数据，订阅者也可以收到被订阅者在订阅之前最多n个数据。
 */
@Route(path = "/home/rxjava2/replayExample")
public class ReplayExampleActivity extends BaseActivity {

    private static final String TAG = ReplayExampleActivity.class.getSimpleName();
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
        actionBar.setTitle("Replay");
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

        PublishSubject<Integer> source = PublishSubject.create();
        ConnectableObservable<Integer> connectableObservable = source.replay(3);// bufferSize = 3 to retain 3 values to replay
        connectableObservable.connect();// connecting the connectableObservable

        connectableObservable.subscribe(getFirstObserver());

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        /*
         * it will emit 2, 3, 4 as (count = 3), retains the 3 values for replay
         */
        connectableObservable.subscribe(getSecondObserver());

    }

    private Observer<Integer> getFirstObserver() {
        return  new Observer<Integer>() {
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
