package com.example.hjg.okhttp.intfacecapsulation.inteface;

import com.example.hjg.okhttp.IResponseListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public interface NetRequest {
    /*get请求*/
    void doGet(String url, String tag, final IResponseListener iResponseListener);

    /*post请求*/
    void doPost(String url, Map<String, String> paramsMap, String tag, final IResponseListener iResponseListener);

    /*取消请求*/
    void cancelRequest(String tag);
}
