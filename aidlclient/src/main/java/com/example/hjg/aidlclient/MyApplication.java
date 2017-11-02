package com.example.hjg.aidlclient;

import android.app.Application;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        HermesEventBus.getDefault().connectApp(this, "com.hjg.hjgapplife");
        super.onCreate();
    }
}

