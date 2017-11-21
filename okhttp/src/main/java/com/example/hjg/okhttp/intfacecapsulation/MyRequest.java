package com.example.hjg.okhttp.intfacecapsulation;

import com.example.hjg.okhttp.Handle;
import com.example.hjg.okhttp.IResponseListener;
import com.example.hjg.okhttp.intfacecapsulation.inteface.NetRequest;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 使用接口封装
 */

public class MyRequest implements NetRequest {
    private OkHttpClient okHttpClient;

    public MyRequest() {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        okHttpClient = mBuilder.build();
    }

    @Override
    public void doGet(String url, String tag, final IResponseListener iResponseListener) {
        Request.Builder builder = new Request.Builder()
                .tag(tag)
                .url(url);
        Request build = builder.build();

        okHttpClient.newCall(build).enqueue(new Callback() {
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

    @Override
    public void doPost(String url, Map<String, String> paramsMap, String tag, final IResponseListener iResponseListener) {
        //构造OKhttp
//        okHttpClient = new OkHttpClient();
        //构造请求参数
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
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

    @Override
    public void cancelRequest(String tag) {
        Dispatcher dispatcher = okHttpClient.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }

    }
}
