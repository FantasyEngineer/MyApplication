package com.hjg.hjgapplife.activity.BLE;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.util.ToastUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.popup.DialogPopup;

public class TestBLEActivity extends BaseOthreRenderSwipActivity {

    @BindView(R.id.btn_get_address)
    Button btnGetAddress;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.tv_show_address)
    TextView tvShowAddress;
    private BluetoothAdapter blueadapter;//蓝牙adapter
    private String TAG = TestBLEActivity.class.getSimpleName();
    private DialogPopup dialogPopup;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test_ble;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "测试蓝牙");
    }

    @Override
    protected void initData() {
        //尝试打开蓝牙
        setBLE();
        //注册广播
        register();
        dialogPopup = new DialogPopup(activity);
    }

    private void register() {
        //搜索蓝牙设备，该过程是异步的，通过下面注册广播接受者，可以监听是否搜到设备。
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备连接状态改变
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //蓝牙设备状态改变
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothReceiver, filter);
    }

    private void setBLE() {
        blueadapter = BluetoothAdapter.getDefaultAdapter();
        if (blueadapter != null) {  //设备支持蓝牙
            //确认开启蓝牙
            if (!blueadapter.isEnabled()) {
                //请求用户开启
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RESULT_FIRST_USER);
                //使蓝牙设备可见，方便配对,200毫秒可见
                Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                in.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
                startActivity(in);
                //直接开启，不经过提示
//                blueadapter.enable();
            }
        } else {   //设备不支持蓝牙
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("No bluetooth devices");
            dialog.setMessage("Your equipment does not support bluetooth, please change device");

            dialog.setNegativeButton("cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            dialog.show();
        }
    }

    private boolean isSearch = true;

    @OnClick({R.id.btn_get_address, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_address://获取蓝牙信息
                if (blueadapter == null) {
                    Toast.makeText(activity, "蓝牙的blueadapter获取失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvShowAddress.setText("蓝牙名称：" + blueadapter.getName() + " ; 蓝牙地址：" + blueadapter.getAddress());
                //获取已配对蓝牙设备
                Set<BluetoothDevice> devices = blueadapter.getBondedDevices();
                String blutToothName = "";
                String blutToothAddress = "";
                for (BluetoothDevice bonddevice : devices) {
                    blutToothName = blutToothName + bonddevice.getName() + " ; \n    \t";
                    blutToothAddress = blutToothAddress + bonddevice.getAddress() + " ; \n    \t";

                    tvShowAddress.setText("蓝牙名称：" + blueadapter.getName() + " ; " +
                            "蓝牙地址：" + blueadapter.getAddress() + " ; \n" +
                            "已经配对的蓝牙数量：" + devices.size() + " ; \n" +
                            "已配对的蓝牙设备名称：\n    \t" + blutToothName + "\n" +
                            "已配对的蓝牙设备地址：\n    \t" + blutToothAddress);
                }

                break;
            case R.id.btn_search://搜索设备，每一次搜索到的设备都会发送广播
                if (isSearch) {
                    blueadapter.startDiscovery();
                    btnSearch.setText("停止搜索");
                    isSearch = false;
                    dialogPopup.setTitleAndContent("搜索设备中", "搜索中.....");
                    dialogPopup.setSingleBtn("等待一下", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            stopSearch();
                        }
                    });
                    dialogPopup.showPopupWindow();
                } else {
                    stopSearch();
                }
                break;

        }

    }

    @Override
    public void onEventMainThread(Object object) {
        String state = "";
        if (object instanceof String) {
            state = (String) object;
            if (state.equals("BLEOFF")) {
                ToastUtil.show(activity, "蓝牙关闭");
            } else if (state.equals("BLEOFFING")) {
                ToastUtil.show(activity, "蓝牙正在关闭");
            } else if (state.equals("BLEON")) {
                ToastUtil.show(activity, "蓝牙开启");
            } else if (state.equals("BLEONING")) {
                ToastUtil.show(activity, "蓝牙正在开启");
            }
        }
    }

    String scanDeviceInfo = "";
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "mBluetoothReceiver action =" + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {//每扫描到一个设备，系统都会发送此广播。
                //获取蓝牙设备
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (scanDevice == null || scanDevice.getName() == null) return;
                //蓝牙设备名称
                scanDeviceInfo = scanDeviceInfo + "name=" + scanDevice.getName() + "; address=" + scanDevice.getAddress() + "\n";

                dialogPopup.setTitleAndContent("搜索设备", scanDeviceInfo);
                dialogPopup.setSingleBtn("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stopSearch();
                    }
                });
//                if(name != null && name.equals(BLUETOOTH_NAME)){
//                    blueadapter.cancelDiscovery();
//                    //取消扫描
////                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));                   //连接到设备。
////                    mBlthChatUtil.connect(scanDevice);
//                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d("TestBLEActivity", "stop");
            }
        }
    };

    /**
     * 停止搜索按钮点击之后
     */
    public void stopSearch() {
        dialogPopup.dismiss();
        blueadapter.cancelDiscovery();
        btnSearch.setText("开始搜索");
        scanDeviceInfo = "";
        isSearch = true;
    }
}
