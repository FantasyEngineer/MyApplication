package com.example.hjg.okhttp;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class util {
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
