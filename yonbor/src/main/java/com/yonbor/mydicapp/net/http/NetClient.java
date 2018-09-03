package com.yonbor.mydicapp.net.http;


import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yonbor.baselib.model.http.ResultModel;
import com.yonbor.baselib.ui.base.BaseActivity;
import com.yonbor.baselib.utils.MD5;
import com.yonbor.baselib.utils.NetworkUtil;
import com.yonbor.mydicapp.app.AppConstant;
import com.yonbor.mydicapp.app.ConstantsHttp;
import com.yonbor.mydicapp.model.WanAndroidVo;

import org.reactivestreams.Subscription;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/1.
 */
public class NetClient {

    public static ArrayMap<String, Integer> signMap = new ArrayMap<>();

    static {
//        signMap.put(ConstantsHttp.Person_Service, 1);


    }

    public static void post(final BaseActivity activity, int hostType, String url,
                            ArrayMap<String, String> header, Object body,
                            final Class clazz,
                            final Listener listener) {
        post(activity, hostType, url, header, body, clazz, Parser.DATA, true, listener);

    }

    public static void post(final BaseActivity activity, int hostType, String url,
                            ArrayMap<String, String> header, Object body,
                            final Class clazz, final String dataId, boolean needCode,
                            final Listener listener) {

        if (!NetworkUtil.isNetworkAvailable()) {
            activity.showToast("网络未打开");
            if (listener != null) listener.onFaile(null);
            return;
        }
        if (header != null && signMap.containsKey(header.get(ConstantsHttp.Head_Id))) {
            String accessToken = header.get(ConstantsHttp.Head_Token);
            if (!TextUtils.isEmpty(accessToken) && accessToken.length() >= 8) {
                String salt = accessToken.substring(4, 8);
                String[] signs = new String[]{JSON.toJSONString(body), salt};
                Arrays.sort(signs);
                String digest = MD5.getMD5(signs[0] + signs[1]).toLowerCase();
                header.put("X-Signature", digest);
            }
        }

        header = addProductName(header);

        handelFlowable(activity, clazz, dataId, needCode, listener,
                RetrofitClient.getInstance(hostType).post2(url, header, body));
    }

    @android.support.annotation.NonNull
    private static ArrayMap<String, String> addProductName(ArrayMap<String, String> header) {
        if (header == null) header = new ArrayMap<>();
        header.put("B-Product-Code", AppConstant.Product_Name);
        return header;
    }


    public static void uploadHead(final BaseActivity activity, int hostType, String url,
                                  String filePath, String serviceFileName,
                                  ArrayMap<String, String> header,
                                  final Class clazz, final String dataId,
                                  final Listener listener) {

        if (!NetworkUtil.isNetworkAvailable()) {
            activity.showToast("网络未打开");
            if (listener != null) listener.onFaile(null);
            return;
        }
        header = addProductName(header);
        handelFlowable(activity, clazz, dataId, true, listener,
                RetrofitClient.getInstance(hostType).postHeader2(url, filePath, serviceFileName, header));
    }


    public static void uploadFile(final BaseActivity activity, int hostType, String url, ArrayMap<String, String> maps, List<String> filePaths,
                                  ArrayMap<String, String> header,
                                  final Class clazz, final String dataId,
                                  final Listener listener) {

        if (!NetworkUtil.isNetworkAvailable()) {
            activity.showToast("网络未打开");
            if (listener != null) listener.onFaile(null);
            return;
        }
        header = addProductName(header);
        handelFlowable(activity, clazz, dataId, true, listener,
                RetrofitClient.getInstance(hostType).postFiles(url, maps, filePaths, header));
    }

    public static void uploadFile(final BaseActivity activity, int hostType, String url, ArrayMap<String, String> maps, List<String> filePaths,
                                  ArrayMap<String, String> header,
                                  final Class clazz,
                                  final Listener listener) {

        uploadFile(activity, hostType, url, maps, filePaths, header, clazz, Parser.DATA, listener);
    }


    private static void handelFlowable(final BaseActivity activity, final Class clazz, final String dataId, final boolean needCode, final Listener listener, Flowable<String> stringFlowable) {
        stringFlowable.map(new Function<String, ResultModel>() {
            @Override
            public ResultModel apply(@NonNull String s) throws Exception {
                return Parser.getInstance().parserSpData(s, clazz, dataId, needCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {//subscribe()调用后而且在事件发送前执行(默认情况下subcribe发生的线程决定，可以通过最近的跟在后面的subscribeOn指定)
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        if (listener != null)
                            listener.onPrepare();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//指定doOnSubscribe的线程
                .compose(RxLifecycle.bindUntilEvent(activity.lifecycle(), ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Object>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Object o) {
                        ResultModel result = (ResultModel) o;
                        if (listener != null)
                            listener.onSuccess(result);

                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        if (t instanceof SocketTimeoutException) {
                            activity.showToast("请求超时");
                        } else {
                            activity.showToast("请求失败");
                        }
                        if (listener != null)
                            listener.onFaile(t);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void get(final BaseActivity activity, int hostType, String url,
                           ArrayMap<String, String> header,
                           final Class clazz,
                           final Listener listener) {
        get(activity, hostType, url, header, clazz, Parser.DATA, true, listener);
    }

    public static void get(final BaseActivity activity, int hostType, String url,
                           ArrayMap<String, String> header,
                           final Class clazz, final String dataId, boolean needCode,
                           final Listener listener) {

        if (!NetworkUtil.isNetworkAvailable()) {
            activity.showToast("网络未打开");
            if (listener != null) listener.onFaile(null);
            return;
        }
        header = addProductName(header);
        handelFlowable(activity, clazz, dataId, needCode, listener,
                RetrofitClient.getInstance(hostType).get2(url, header));
    }

    public interface Listener<T> {
        void onPrepare();

        void onSuccess(ResultModel<T> result);

        void onFaile(Throwable t);
    }


    //---------------------------WanAndroid开始-------------------------------------

    private static void handelFlowable2(final BaseActivity activity, final Class clazz, final String dataId, final boolean needCode, final Listener2 listener, Flowable<String> stringFlowable) {
        stringFlowable.map(new Function<String, WanAndroidVo>() {
            @Override
            public WanAndroidVo apply(@NonNull String s) throws Exception {
                return Parser2.getInstance().parserSpData(s, clazz, dataId, needCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {//subscribe()调用后而且在事件发送前执行(默认情况下subcribe发生的线程决定，可以通过最近的跟在后面的subscribeOn指定)
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        if (listener != null)
                            listener.onPrepare();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//指定doOnSubscribe的线程
                .compose(RxLifecycle.bindUntilEvent(activity.lifecycle(), ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Object>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Object o) {
                        WanAndroidVo result = (WanAndroidVo) o;
                        if (listener != null)
                            listener.onSuccess(result);

                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        if (t instanceof SocketTimeoutException) {
                            activity.showToast("请求超时");
                        } else {
                            activity.showToast("请求失败");
                        }
                        if (listener != null)
                            listener.onFaile(t);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public interface Listener2<T> {
        void onPrepare();

        void onSuccess(WanAndroidVo<T> result);

        void onFaile(Throwable t);
    }


    public static void get(final BaseActivity activity, int hostType, String url,
                           ArrayMap<String, String> header,
                           final Class clazz,
                           final Listener2 listener) {
        get(activity, hostType, url, header, clazz, Parser2.DATA, true, listener);
    }

    public static void get(final BaseActivity activity, int hostType, String url,
                           ArrayMap<String, String> header,
                           final Class clazz, final String dataId, boolean needCode,
                           final Listener2 listener) {

        if (!NetworkUtil.isNetworkAvailable()) {
            activity.showToast("网络未打开");
            if (listener != null) listener.onFaile(null);
            return;
        }

        handelFlowable2(activity, clazz, dataId, needCode, listener,
                RetrofitClient.getInstance(hostType).get2(url, header));
    }

    //---------------------------WanAndroid结束-------------------------------------
}
