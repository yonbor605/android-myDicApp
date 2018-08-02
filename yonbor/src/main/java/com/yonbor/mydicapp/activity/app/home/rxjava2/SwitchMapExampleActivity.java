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

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * whenever a new item is emitted by the source Observable, it will unsubscribe to and stop
 * mirroring the Observable that was generated from the previously-emitted item,
 * and begin only mirroring the current one.
 *
 * Result: 5x
 */
@Route(path = "/home/rxjava2/switchMapExample")
public class SwitchMapExampleActivity extends BaseActivity {

    private static final String TAG = SwitchMapExampleActivity.class.getSimpleName();
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
        actionBar.setTitle("SwitchMap");
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
                .switchMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        int delay = new Random().nextInt(2);

                        return Observable.just(integer.toString() + "x")
                                .delay(delay, TimeUnit.SECONDS, Schedulers.io());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());

    }

    private Observable<Integer> getObservable() {
        return Observable.just(1, 2, 3, 4, 5);
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                textView.append("---onSubscribe---" + d.isDisposed());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onSubscribe---" + d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                textView.append("---onNext---" + s);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onNext---" + s);
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
