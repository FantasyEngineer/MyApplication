package com.example.hjg.okhttp.finalcapsulation;

import android.content.Context;

import com.example.hjg.okhttp.Handle;
import com.example.hjg.okhttp.IResponseListener;
import com.example.hjg.okhttp.finalcapsulation.inteface.FinalNetRequest;
import com.example.hjg.okhttp.finalcapsulation.model.NetworkOption;
import com.example.hjg.okhttp.util;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class FinalMyRequest implements FinalNetRequest {
    private OkHttpClient okHttpClient;

    @Override
    public void init(Context context) {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void doGet(String url, Map<String, String> paramsMap, final IResponseListener iResponseListener) {
        util.checkNotNull(okHttpClient, "需要先调用init方法");

        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }

        Request.Builder builder = new Request.Builder()
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
    public void doGet(String url, Map<String, String> paramsMap, NetworkOption networkOption, IResponseListener iResponseListener) {
        util.checkNotNull(okHttpClient, "需要先调用init方法");
    }

    @Override
    public void cancelRequest(String tag) {

    }
}
