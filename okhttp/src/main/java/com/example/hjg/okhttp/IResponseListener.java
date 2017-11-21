package com.example.hjg.okhttp;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public interface IResponseListener {

    void success(String data);

    void fail(String code, String codDes);
}
