package com.example.hjg.aidlclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //  判断是否为sendbroadcast发送的广播
        if ("com.hjg.MYBROADCAST".equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String text = bundle.getString("text");
                HermesEventBus.getDefault().post("成功接收广播：" + text);
            }
        }
    }
}
