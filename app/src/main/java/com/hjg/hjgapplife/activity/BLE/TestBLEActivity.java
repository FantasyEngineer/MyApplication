package com.hjg.hjgapplife.activity.BLE;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.util.ToastUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TestBLEActivity extends BaseOthreRenderSwipActivity {

    @BindView(R.id.btn_get_address)
    Button btnGetAddress;
    @BindView(R.id.tv_show_address)
    TextView tvShowAddress;
    private BluetoothAdapter blueadapter;//蓝牙adapter

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

    }

    private void setBLE() {
        blueadapter = BluetoothAdapter.getDefaultAdapter();
        if (blueadapter != null) {  //设备支持蓝牙
            //确认开启蓝牙
            if (!blueadapter.isEnabled()) {
                //请求用户开启
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RESULT_FIRST_USER);
                //使蓝牙设备可见，方便配对
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

    @OnClick(R.id.btn_get_address)
    public void onViewClicked() {
        if (blueadapter == null) {
            Toast.makeText(activity, "蓝牙的blueadapter获取失败", Toast.LENGTH_SHORT).show();
            return;
        }
        tvShowAddress.setText("蓝牙名称：" + blueadapter.getName() + " ; 蓝牙地址：" + blueadapter.getAddress());
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
}
