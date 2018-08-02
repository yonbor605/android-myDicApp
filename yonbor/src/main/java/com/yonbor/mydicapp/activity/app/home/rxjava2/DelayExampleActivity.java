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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * simple example using delay to emit after 2 second
 */
@Route(path = "/home/rxjava2/delayExample")
public class DelayExampleActivity extends BaseActivity {

    private static final String TAG = DelayExampleActivity.class.getSimpleName();
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
        actionBar.setTitle("Delay");
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
        getObservable().delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<String> getObservable() {
        return Observable.just("Amit");
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
