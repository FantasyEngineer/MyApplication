package com.hjg.hjgapplife.activity.BLE;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

//蓝牙服务端
public class BleServerActivity extends BaseOthreRenderSwipActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private String TAG = "Bluetooth_activity";
    // Member object for the chat services
    private BluetoothChatServiceServer mChatService = null;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private TextView mTextView;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public ListenerThread StartListenThread;
    private Button getAddressBtn;
    private String strReceiveData = "";

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
        mTextView = (TextView) findViewById(R.id.showText);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            //直接打开；
            mBluetoothAdapter.enable();
        }

        getAddressBtn = (Button) findViewById(R.id.button1);
        getAddressBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String address = mBluetoothAdapter.getAddress();
                System.out.println(address);

            }
        });

    }


    @Override
    public synchronized void onResume() {
        super.onResume();
        Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                StartListenThread = new ListenerThread();
                StartListenThread.start();


            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult

        // Initialize the BluetoothChatService to perform bluetooth connections
        if (null == mChatService) {
            mChatService = new BluetoothChatServiceServer(activity, mHandler);
        }
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    strReceiveData += readMessage + "\n";
                    System.out.println("rev :" + readMessage);
                    mTextView.setText(strReceiveData);
                    // mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Stop the Bluetooth chat services
        if (mChatService != null)
            mChatService.stop();
        Log.e(TAG, "--- ON DESTROY ---");

    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class ListenerThread extends Thread {
        // The local server socket

        public void run() {
            // Listen to the server socket if we're not connected
            for (int i = 0; i < 100; i++) {
                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    mChatService.start();
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
