package com.hjg.hjgapplife.activity.BLE;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;

import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;
import com.hjg.hjgapplife.activity.receiver.MyBLEReveiver;

public class BleMainActivity extends BaseOtherRenderListActivity {
    private MyBLEReveiver bleReveiver;

    @Override
    public void initDataList() {
        bleReveiver = new MyBLEReveiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bleReveiver, intentFilter);

        dataList.add("客户端");
        dataList.add("服务端");
        dataList.add("测试");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(activity, BleClientActivity.class));
                break;
            case 1:
                startActivity(new Intent(activity, BleServerActivity.class));
                break;
            case 2:
                startActivity(new Intent(activity, TestBLEActivity.class));
                break;
        }

    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "蓝牙相关");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bleReveiver);
    }
}
