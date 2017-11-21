package com.example.hjg.okhttp.toolcapsulation;

import android.content.Context;

import com.example.hjg.okhttp.Handle;
import com.example.hjg.okhttp.IResponseListener;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 浅谈封装第一步（工具类封装）
 * <p>
 * 简单的OKhttp的工具类，但是容易被人修改（这是一个最基础的封装）
 */

public class OkHttpUtils {
    private static long cacheSize = 1024 * 1024 * 10;

    /**
     * get
     *
     * @param context
     * @param url
     * @param paramsMap
     * @param iResponseListener
     */
    public static void doGet(Context context, String url, Map<String, String> paramsMap,
                             final IResponseListener iResponseListener) {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(new Cache(context.getExternalFilesDir("okhttp"), cacheSize));
        OkHttpClient cilent = mBuilder.build();

        Request.Builder builder = new Request.Builder().url(url);


        Request build = builder.build();

        cilent.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handle.handleError(e, iResponseListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Handle.handleResult(response, iResponseListener);
            }
        });
    }

    /**
     * post
     *
     * @param context
     * @param url
     * @param paramsMap
     * @param iResponseListener
     */
    public static void doPost(Context context, String url, Map<String, String> paramsMap,
                              final IResponseListener iResponseListener) {
//        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .cache(new Cache(context.getExternalFilesDir("okhttp"), cacheSize));
//        OkHttpClient cilent = mBuilder.build();
//
//        FormBody.Builder builder = new FormBody.Builder();
//        FormBody formBody = builder.build();
//
//        Request.Builder requestBuilder = new Request.Builder().url(url).post(formBody);
//        Request request = requestBuilder.build();
//        cilent.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                handleError(e, iResponseListener);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                handleResult(response, iResponseListener);
//            }
//        });
        //构造OKhttp
        OkHttpClient okHttpClient = new OkHttpClient();
        //构造请求参数
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handle.handleError(e, iResponseListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Handle.handleResult(response, iResponseListener);
            }
        });
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    /**
     * 上传文件
     *
     * @param url
     * @param file
     * @param iResponseListener
     */
    public void postFile(String url, File file, final IResponseListener iResponseListener) {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handle.handleError(e, iResponseListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Handle.handleResult(response, iResponseListener);
            }
        });

    }


}
