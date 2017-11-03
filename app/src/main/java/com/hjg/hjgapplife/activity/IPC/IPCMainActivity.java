package com.hjg.hjgapplife.activity.IPC;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hjg.baseapp.util.ToastUtil;
import com.hjg.baseapp.widget.dialog.BottomDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;
import com.hjg.hjgapplife.entity.EventBusBean;

import xiaofei.library.hermeseventbus.HermesEventBus;

public class IPCMainActivity extends BaseOtherRenderListActivity {
    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "IPC进程间通信");
        topBarManage.setRightButtonImgAndTxt(true, null, "跳pluginApp", view -> {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName comp = new ComponentName("com.example.hjg.aidlclient",
                    "com.example.hjg.aidlclient.MainActivity");
            intent.setComponent(comp);
            int launchFlags = Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;
            intent.setFlags(launchFlags);
            intent.setAction("android.intent.action.VIEW");
            Bundle bundle = new Bundle();
            bundle.putString("from", "来自父应用");
            intent.putExtras(bundle);
            startActivity(intent);


        });
    }

    @Override
    public void initDataList() {
        dataList.add("AIDL—Android接口定义语言");
        dataList.add("HermesEventBus想子app发送消息");
        dataList.add("广播跨进程通信");
        dataList.add("File进行跨进程通信");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                BottomDialog bottomDialog = new BottomDialog(activity);
                bottomDialog.showDialog(activity.getResources().getString(R.string.aidl));
                break;
            case 1:
                ToastUtil.show(activity, "发送成功，请先打开AIDLClient，再查看");
                HermesEventBus.getDefault().post(new EventBusBean(9999, "这是一条来自父APP的消息\n"));
                break;
            case 2:
                ToastUtil.show(activity, "广播发送成功，请先打开AIDLClient，再查看");
                //  通过Intent类的构造方法指定广播的ID
                Intent intent = new Intent("com.hjg.MYBROADCAST");
                //  将要广播的数据添加到Intent对象中
                intent.putExtra("text", "来自与父app的广播\n");
                //  发送广播
                sendBroadcast(intent);
                break;
            case 3:
                break;
            case 4:
                break;

        }
    }
}
