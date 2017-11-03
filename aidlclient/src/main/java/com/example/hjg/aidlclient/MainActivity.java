package com.example.hjg.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.hjgapplife.IMyAidlInterface;
import com.hjg.hjgapplife.entity.EventBusBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * AIDL 客户端通信，根据源包名和源service的action启动service
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //经过主app的service进行计算得出结果
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    textView.setText("传入的数值是1和2；经过计算的结果是：" + msg.arg1);
                    break;
            }

        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface aidl = IMyAidlInterface.Stub.asInterface(service);
            try {
                int result = aidl.add(1, 2);
                Message message = handler.obtainMessage();
                message.arg1 = result;
                message.what = 1;
                handler.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private TextView textView;
    private Button button;
    private TextView event_msg;
    private TextView receive_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HermesEventBus.getDefault().register(this);

        //从父应用跳转过来的标志
        if (!TextUtils.isEmpty(getIntent().getStringExtra("from"))) {
            Toast.makeText(this, getIntent().getStringExtra("from"), Toast.LENGTH_SHORT).show();
        }
        textView = (TextView) findViewById(R.id.textView);
        event_msg = (TextView) findViewById(R.id.event_msg);
        receive_msg = (TextView) findViewById(R.id.receive_msg);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    /**
     * 启动AIDL服务
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setAction("com.hjg.aidlserver");
        intent.setPackage("com.hjg.hjgapplife");
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }

    //接收来自父app发来的消息
    @Subscribe(threadMode = ThreadMode.MAIN)//在ui线程执行
    public void onEventMainThread(Object object) {
        Log.d("MainActivity", "onEventMainThread");
        if (object instanceof EventBusBean) {
            event_msg.setText(event_msg.getText() + ((EventBusBean) object).getContent());
        }
        if (object instanceof String) {
            receive_msg.setText(receive_msg.getText() + (String) object);
        }
    }
}
