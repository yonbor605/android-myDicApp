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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Using throttleFirst() -> if the source Observable has emitted no items since
 * the last time it was sampled, the Observable that results from this operator
 * will emit no item for that sampling period.
 * throttleFirst用来解决抖动的问题，我们可以设置一段时间，之后它会发射固定时间长度之内的第一个事件，而屏蔽其它的事件。
 */
@Route(path = "/home/rxjava2/throttleFirstExample")
public class ThrottleFirstExampleActivity extends BaseActivity {

    private static final String TAG = ThrottleFirstExampleActivity.class.getSimpleName();
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
        actionBar.setTitle("ThrottleFirst");
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
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());

    }

    private Observable<Integer> getObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // send events with simulated time wait
                Thread.sleep(0);
                emitter.onNext(1); // deliver
                emitter.onNext(2); // skip
                Thread.sleep(505);
                emitter.onNext(3); // deliver
                Thread.sleep(99);
                emitter.onNext(4); // skip
                Thread.sleep(100);
                emitter.onNext(5); // skip
                emitter.onNext(6); // skip
                Thread.sleep(305);
                emitter.onNext(7); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        });
    }

    private Observer<Integer> getObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                textView.append("---onSubscribe---" + d.isDisposed());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onSubscribe---" + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                textView.append("---onNext---");
                textView.append(AppConstant.LINE_SEPARATOR);
                textView.append("value is " + integer);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onNext---");
                Log.d(TAG, "value is " + integer);
            }

            @Override
            public void onError(Throwable e) {
                textView.append("---onError---" + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onError---" + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append("---onComplete---");
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onComplete---");
            }
        };
    }


}
