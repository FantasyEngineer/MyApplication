package com.hjg.hjgapplife.activity.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 蓝牙开启关闭的监听
 */

public class MyBLEReveiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                BluetoothAdapter.ERROR);
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                HermesEventBus.getDefault().post("BLEOFF");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                HermesEventBus.getDefault().post("BLEOFFING");
                break;
            case BluetoothAdapter.STATE_ON:
                HermesEventBus.getDefault().post("BLEON");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                HermesEventBus.getDefault().post("BLEONING");
                break;
        }
    }
}
