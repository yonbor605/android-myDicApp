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

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * simple example using reduce to add all the number
 */
@Route(path = "/home/rxjava2/reduceExample")
public class ReduceExampleActivity extends BaseActivity {

    private static final String TAG = ReduceExampleActivity.class.getSimpleName();
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
        actionBar.setTitle("Reduce");
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
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(getObserver());

    }

    private Observable<Integer> getObservable() {
        return Observable.just(1, 2, 3, 4);
    }

    private MaybeObserver<Integer> getObserver() {
        return new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                textView.append("---onSubscribe---" + d.isDisposed());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onSubscribe---" + d.isDisposed());

            }

            @Override
            public void onSuccess(Integer integer) {
                textView.append("---onSuccess---" + integer);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, "---onSuccess---" + integer);
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
