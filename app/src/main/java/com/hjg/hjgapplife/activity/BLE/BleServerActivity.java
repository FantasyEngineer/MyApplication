package com.hjg.hjgapplife.activity.BLE;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.BLE.adapter.MultipleItemQuickAdapter;
import com.hjg.hjgapplife.activity.BLE.entity.MultipleItem;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import java.util.ArrayList;


//蓝牙服务端
public class BleServerActivity extends BaseOthreRenderSwipActivity implements View.OnClickListener {
    private final static String TAG = "ServerActivity";
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Context mContext;

    private Button mBtnBluetoothVisibility;
    private Button mBtnBluetoohDisconnect;
    private Button mBtnSendMessage;
    private EditText mEdttMessage;

    private TextView mBtConnectState;
    private ProgressDialog mProgressDialog;
    private BluetoothChatUtil mBlthChatUtil;
    private RecyclerView recycleViewChat;

    ArrayList<MultipleItem> list = new ArrayList<>();

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothChatUtil.STATE_CONNECTED:
                    String deviceName = msg.getData().getString(BluetoothChatUtil.DEVICE_NAME);
                    mBtConnectState.setText("已成功连接到设备" + deviceName);
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                case BluetoothChatUtil.STATAE_CONNECT_FAILURE:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothChatUtil.MESSAGE_DISCONNECTED:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mBtConnectState.setText("与设备断开连接");
                    mBlthChatUtil.startListen();
                    break;
                case BluetoothChatUtil.MESSAGE_READ: {
                    byte[] buf = msg.getData().getByteArray(BluetoothChatUtil.READ_MSG);
                    String str = new String(buf, 0, buf.length);
                    //先清空list
                    list.removeAll(list);
                    MultipleItem multipleItem = new MultipleItem();
                    multipleItem.itemType = MultipleItem.OTHER;
                    multipleItem.setContent(str);
                    //再填充数据
                    list.add(multipleItem);
                    mAdapter.addData(list);
                    recycleViewChat.smoothScrollToPosition(mAdapter.getItemCount());
                    break;
                }
                case BluetoothChatUtil.MESSAGE_WRITE: {
                    byte[] buf = (byte[]) msg.obj;
                    String str = new String(buf, 0, buf.length);
                    //先清空list
                    list.removeAll(list);
                    MultipleItem multipleItem = new MultipleItem();
                    multipleItem.itemType = MultipleItem.ME;
                    multipleItem.setContent(str);
                    //再填充数据
                    list.add(multipleItem);
                    mAdapter.addData(list);
                    recycleViewChat.smoothScrollToPosition(mAdapter.getItemCount());
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private MultipleItemQuickAdapter mAdapter;
    private RelativeLayout rLayout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_ble_server;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "蓝牙服务端");
    }

    @Override
    protected void initData() {
        rLayout = (RelativeLayout) findViewById(R.id.container);
        //只让底部的输入法弹起的操作
        rLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rLayout.getWindowVisibleDisplayFrame(r);
                //r.top 是状态栏高度
                int screenHeight = rLayout.getRootView().getHeight();
                int softHeight = screenHeight - r.bottom;
                Log.e("test", "screenHeight:" + screenHeight);
                Log.e("test", "top:" + r.top);
                Log.e("test", "bottom:" + r.bottom);
                Log.e("test", "Size: " + softHeight);
                if (softHeight > 100) {//当输入法高度大于100判定为输入法打开了
                    rLayout.scrollTo(0, softHeight);
                    //键盘弹起，recycleView移动到最后
                    recycleViewChat.smoothScrollToPosition(mAdapter.getItemCount());
                } else {//否则判断为输入法隐藏了
                    rLayout.scrollTo(0, 0);
                }
            }
        });


        mContext = this;
        initView();
        initRecycleView();
        initBluetooth();
        mBlthChatUtil = BluetoothChatUtil.getInstance(mContext);
        mBlthChatUtil.registerHandler(mHandler);
    }

    private void initRecycleView() {
        recycleViewChat.setHasFixedSize(false);
        recycleViewChat.setLayoutManager(new LinearLayoutManager(activity));
        recycleViewChat.setAdapter(mAdapter = new MultipleItemQuickAdapter(null));
    }

    private void initView() {

        mBtnBluetoothVisibility = (Button) findViewById(R.id.btn_blth_visiblity);
        mBtnBluetoohDisconnect = (Button) findViewById(R.id.btn_blth_disconnect);
        mBtnSendMessage = (Button) findViewById(R.id.btn_sendmessage);
        mEdttMessage = (EditText) findViewById(R.id.edt_message);
        mBtConnectState = (TextView) findViewById(R.id.tv_connect_state);
        recycleViewChat = (RecyclerView) findViewById(R.id.recycleViewChat);

        mBtnBluetoothVisibility.setOnClickListener(this);
        mBtnBluetoohDisconnect.setOnClickListener(this);
        mBtnSendMessage.setOnClickListener(this);
        mProgressDialog = new ProgressDialog(this);
    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {//设备不支持蓝牙
            Toast.makeText(getApplicationContext(), "设备不支持蓝牙", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        //判断蓝牙是否开启
        if (!mBluetoothAdapter.isEnabled()) {//蓝牙未开启
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            //mBluetoothAdapter.enable();此方法直接开启蓝牙，不建议这样用。
        }
        //设置蓝牙可见性
        if (mBluetoothAdapter.isEnabled()) {
            if (mBluetoothAdapter.getScanMode() !=
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                Intent discoverableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(
                        BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
                startActivity(discoverableIntent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult request=" + requestCode + " result=" + resultCode);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mBluetoothAdapter.isEnabled()) return;
        if (mBlthChatUtil != null) {
            // 只有国家是state_none，我们知道，我们还没有开始
            if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_NONE) {
                // 启动蓝牙聊天服务
                mBlthChatUtil.startListen();
            } else if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                BluetoothDevice device = mBlthChatUtil.getConnectedDevice();
                if (null != device && null != device.getName()) {
                    mBtConnectState.setText("已成功连接到设备" + device.getName());
                } else {
                    mBtConnectState.setText("已成功连接到设备");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btn_blth_visiblity:
                if (mBluetoothAdapter.isEnabled()) {
                    if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                        Intent discoveryIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        discoveryIntent.putExtra(
                                BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivity(discoveryIntent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.bluetooth_unopened), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_blth_disconnect:
                if (mBlthChatUtil.getState() != BluetoothChatUtil.STATE_CONNECTED) {
                    Toast.makeText(mContext, "蓝牙未连接", Toast.LENGTH_SHORT).show();
                } else {
                    mBlthChatUtil.disconnect();
                }
                break;
            case R.id.btn_sendmessage:
                String messagesend = mEdttMessage.getText().toString();
                if (null == messagesend || messagesend.length() == 0) {
                    return;
                }
                mBlthChatUtil.write(messagesend.getBytes());
                break;
            default:
                break;
        }
    }

}
