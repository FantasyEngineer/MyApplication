package com.example.hjg.okhttp;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Handle {
    public static void handleResult(Response response, IResponseListener iResponseListener) {
        if (iResponseListener != null) {
            try {
                iResponseListener.success(response.body().string());
            } catch (IOException e) {
                iResponseListener.fail("11", "IO异常");
            }
        }
    }

    public static void handleError(IOException e, IResponseListener iResponseListener) {
        if (iResponseListener != null) {
            iResponseListener.fail("11", e.getMessage());
        }
    }

}
