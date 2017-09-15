package com.hjg.baseapp.util;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * 安全关闭句柄工具类
 */
public class SafeCloseUtils {

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException exception) {
                Log.d("CloseUtils", exception.getMessage());
            }
        }
    }

    public static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
                Log.e("SafeCloseUtils", e.getMessage());
            }
        }
    }

    public static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                Log.e("SafeCloseUtils", e.getMessage());
            }
        }
    }

    public static void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e) {
                Log.e("SafeCloseUtils", e.getMessage());
            }
        }
    }
}
