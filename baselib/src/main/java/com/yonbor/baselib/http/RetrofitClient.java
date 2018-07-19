package com.yonbor.baselib.http;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.yonbor.baselib.base.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * RetrofitClient
 * Created by Tamic on 2016-06-15.
 * {@link # https://github.com/NeglectedByBoss/RetrofitClient}
 */
public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 20;
    private BaseApiService apiService;
    private static OkHttpClient okHttpClient;
    private static String baseUrl = AppContext.getBaseUrl();
    private static Context mContext = AppContext.getContext();

    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(
                mContext, baseUrl);
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {
    }

    private RetrofitClient(Context context, String url) {

        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .retryOnConnectionFailure(false)//不重连
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为20s
                .connectionPool(new ConnectionPool(8, DEFAULT_TIMEOUT, TimeUnit.SECONDS))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        System.out.println("header===========" + response.headers().names());
                        System.out.println("header===========" + response.headers("X-Auth-Failed-Code"));
                        if (response.header("X-Auth-Failed-Code") != null) {
                            Intent intent = new Intent(AppContext.getHttpHeaderAction());
                            intent.putExtra("code", response.header("X-Auth-Failed-Code"));
                            AppContext.getContext().sendBroadcast(intent);
                        }
                        return response;
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

        createBaseApi();

    }

//   /**
//     * ApiBaseUrl
//     *
//     * @param newApiBaseUrl
//     */
//    public static void changeApiBaseUrl(String newApiBaseUrl) {
//        baseUrl = newApiBaseUrl;
//        builder = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(baseUrl);
//    }
//
//    /**
//     *addcookieJar
//     */
//    public static void addCookie() {
//        okHttpClient.newBuilder().cookieJar(new NovateCookieManger(mContext)).build();
//        retrofit = builder.client(okHttpClient).build();
//    }
//
    /**
     * ApiBaseUrl
     *
     * @param newApiHeaders
     */
//    public static void changeApiHeader(Map<String, String> newApiHeaders) {
//
//        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(newApiHeaders)).build();
//        builder.client(httpClient.build()).build();
//    }

    /**
     * create BaseApi  default ApiManager
     *
     * @return ApiManager
     */
    private RetrofitClient createBaseApi() {
        if (apiService == null)
            apiService = create(BaseApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public Flowable<String> post2(String url, ArrayMap<String, String> headers, Object body) {
        if (headers == null)
            return apiService.post2(url, body);
        return apiService.post2(url, headers, body);
    }

    public Flowable<String> get2(String url, ArrayMap<String, String> headers) {
        if (headers == null)
            return apiService.get2(url);
        return apiService.get2(url, headers);
    }

    public Call<String> post(String url, ArrayMap<String, String> headers, Object body) {
        if (headers == null)
            return apiService.post(url, body);
        return apiService.post(url, headers, body);
    }

    public Call<String> get(String url, ArrayMap<String, String> headers) {
        if (headers == null)
            return apiService.get(url);
        return apiService.get(url, headers);
    }

    public Call<String> postPicture(String url, String[] filePaths, String serviceFileName,
                                    ArrayMap<String, String> headers, ArrayMap<String, String> params) {
        ArrayMap<String, RequestBody> bodyMap = new ArrayMap<>();
        if (null != filePaths && filePaths.length > 0) {
            for (int i = 0; i < filePaths.length; i++) {
                if (!TextUtils.isEmpty(filePaths[i])) {
                    File file = new File(filePaths[i]);
                    RequestBody photo = RequestBody.create(MediaType.parse("image/*"), file);
                    bodyMap.put(serviceFileName + "\"; filename=\"" + file.getName() + "\"", photo);
                }
            }


        }
        if (params != null) {
            return apiService.uploadFiles(url, bodyMap, headers, params);
        } else {
            return apiService.uploadFiles(url, bodyMap, headers);
        }
    }

    public Call<String> postHeader(String url, String filePath, String serviceFileName,
                                   ArrayMap<String, String> headers, ArrayMap<String, String> params) {


//        RequestBody requestBody = null;
        MultipartBody.Part part = null;
        MultipartBody.Part part1 = null;
        MultipartBody.Part part2 = null;
        MultipartBody.Part part3 = null;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            RequestBody photo = RequestBody.create(MediaType.parse("image/*"), file);
            part = MultipartBody.Part.createFormData(serviceFileName, file.getName(), photo);
            part1 = MultipartBody.Part.createFormData("catalog", "avatar");
            part2 = MultipartBody.Part.createFormData("path", "avatar");
            part3 = MultipartBody.Part.createFormData("mode", "31");

        }

        return apiService.uploadHeader(url, part, part1, part2, part3, headers);
    }

    public Flowable<String> postHeader2(String url, String filePath, String serviceFileName,
                                        ArrayMap<String, String> headers) {
        MultipartBody.Part part = null;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            RequestBody photo = RequestBody.create(MediaType.parse("image/*"), file);
            part = MultipartBody.Part.createFormData(serviceFileName, file.getName(), photo);
        }

        return apiService.uploadHeader2(url, part, headers);
    }

    /**
     * 上传图片+表单
     *
     * @param url
     * @param maps      form表单数据
     * @param filePaths
     * @param headers
     * @return
     */
    public Flowable<String> postFiles(String url, ArrayMap<String, String> maps, List<String> filePaths,
                                      ArrayMap<String, String> headers) {
        ArrayMap<String, RequestBody> photos = new ArrayMap<>();
        if (filePaths != null && filePaths.size() > 0) {
            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                photos.put("file\"; filename=\"" + file.getName(), requestFile);
            }
        }
        ArrayMap<String, RequestBody> map = new ArrayMap<>();
        if (maps != null)
            for (String key : maps.keySet()) {
                if (maps.get(key) == null) continue;
                map.put(key, RequestBody.create(MediaType.parse("form-data"), maps.get(key)));
            }
        return apiService.uploadFile(url, map, photos, headers);
    }

    /**
     * 上传图片+表单
     *
     * @param url
     * @param maps            form表单数据
     * @param filePaths
     * @param serviceFileName 服务端接收文件名
     * @param headers
     * @return
     */
    public Flowable<String> postFiles(String url, ArrayMap<String, String> maps, List<String> filePaths,
                                      String serviceFileName,
                                      ArrayMap<String, String> headers) {
        ArrayMap<String, RequestBody> photos = new ArrayMap<>();
        if (filePaths != null && filePaths.size() > 0) {
            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                photos.put(serviceFileName + "\"; filename=\"" + file.getName(), requestFile);
            }
        }
        ArrayMap<String, RequestBody> map = new ArrayMap<>();
        if (maps != null)
            for (String key : maps.keySet()) {
                if (maps.get(key) == null) continue;
                map.put(key, RequestBody.create(MediaType.parse("form-data"), maps.get(key)));
            }
        return apiService.uploadFile(url, map, photos, headers);
    }


}
