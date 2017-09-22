package com.hjg.baseapp.util.glide;

import android.content.Context;
import android.os.Looper;

import com.bumptech.glide.Glide;

/**
 * Created by xiaohong.peng on 2017/6/15.
 * Glide 清除缓存工具类
 */

public class GlideCathUitls {

    private static GlideCathUitls instance;

    public static GlideCathUitls getInstance() {
        if (null == instance) {
            instance = new GlideCathUitls();
        }
        return instance;
    }


    // 清除图片磁盘缓存，调用Glide自带方法
    public boolean clearCacheDiskSelf(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public boolean clearCacheMemory(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
