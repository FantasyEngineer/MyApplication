package com.example.hjg.okhttp.finalcapsulation.inteface;

import android.content.Context;

import com.example.hjg.okhttp.IResponseListener;
import com.example.hjg.okhttp.finalcapsulation.model.NetworkOption;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public interface FinalNetRequest {
    /*初始化*/
    void init(Context context);

    /*get请求*/
    void doGet(String url, final Map<String, String> paramsMap, final IResponseListener iResponseListener);

    /*get请求*/
    void doGet(String url, final Map<String, String> paramsMap, NetworkOption networkOption, final IResponseListener iResponseListener);

    /*取消请求*/
    void cancelRequest(String tag);
}
