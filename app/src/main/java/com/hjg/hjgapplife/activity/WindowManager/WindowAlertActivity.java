package com.hjg.hjgapplife.activity.WindowManager;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WindowAlertActivity extends BaseOthreRenderSwipActivity {

    @BindView(R.id.tv)
    TextView tv;
    private WindowManager windowManager;
    private TextView textView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_window_alert;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "需要申请权限实现全局悬浮");
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tv)
    public void onViewClicked() {
        textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.BLACK);
        textView.setText("zhang phil @ csdn");
        textView.setTextSize(10);
        textView.setTextColor(Color.RED);

        //类型是TYPE_TOAST，像一个普通的Android Toast一样。这样就不需要申请悬浮窗权限了。
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);

        //初始化后不首先获得窗口焦点。不妨碍设备上其他部件的点击、触摸事件。
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = 300;
        //params.gravity=Gravity.BOTTOM;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "需要权限的悬浮窗实现", Toast.LENGTH_LONG).show();
            }
        });

        windowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        windowManager.addView(textView, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textView != null && windowManager != null) {
            windowManager.removeViewImmediate(textView);
        }
    }
}
