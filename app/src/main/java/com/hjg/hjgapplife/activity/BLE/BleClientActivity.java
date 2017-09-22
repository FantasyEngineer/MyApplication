package com.hjg.hjgapplife.activity.BLE;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

//蓝牙客户端
public class BleClientActivity extends BaseOthreRenderSwipActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private String TAG = "Bluetooth_activity";
    private BluetoothChatService mChatService = null;
    public ListenerThread StartListenThread;
    private EditText mEditText;
    private Button sendBtn;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_ble_client;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "蓝牙客户端");
    }

    @Override
    protected void initData() {
        mEditText = (EditText) findViewById(R.id.EditText1);
        sendBtn = (Button) findViewById(R.id.button1);
        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
                    String sendContent = mEditText.getText().toString();
                    sendMessage(sendContent);
                }
            }
        });

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {

            //弹出对话框提示用户是后打开
            //Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enabler, REQUEST_ENABLE);

            //不做提示，强行打开
            mBluetoothAdapter.enable();
        }

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this);
    }

    private void connectDevice(String address) {
        // Get the device MAC address

        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, false);
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        Log.e(TAG, "+ ON RESUME +");

        String address = mBluetoothAdapter.getAddress();
        System.out.println(address);
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                // Start the Bluetooth chat services
                StartListenThread = new ListenerThread();
                StartListenThread.start();

            }
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, "未连接", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            // mOutStringBuffer.setLength(0);
            //  mOutEditText.setText(mOutStringBuffer);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Stop the Bluetooth chat services
        if (mChatService != null)
            mChatService.stop();
        Log.e(TAG, "--- ON DESTROY ---");

    }

    private class ListenerThread extends Thread {
        // The local server socket

        public void run() {
            // Listen to the server socket if we're not connected
            for (int i = 0; i < 100; i++) {
                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    //mChatService.start();
                    final String address = "78:F5:FD:FB:1D:FF";//mBluetoothAdapter.getAddress();
                    System.out.println(address);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEditText.setText(address);
                        }
                    });

                    if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED)
                        connectDevice(address);
                    break;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }

    }


}
