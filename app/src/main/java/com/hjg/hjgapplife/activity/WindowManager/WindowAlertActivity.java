package com.hjg.hjgapplife.activity.WindowManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.util.ActivityUtils;
import com.hjg.baseapp.util.ToastUtil;
import com.hjg.baseapp.widget.TasksWindow;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.service.WatchingService;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hjg.baseapp.widget.TasksWindow.setShowWindow;

public class WindowAlertActivity extends BaseOthreRenderSwipActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_show_other_class)
    TextView tv_show_other_class;

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


    @OnClick({R.id.tv, R.id.tv_show_other_class})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                if (TasksWindow.isShowWindow(activity)) {
                    if (ActivityUtils.isServiceRunning(activity, WatchingService.class.getName())) {
                        Toast.makeText(activity, "服务正在运行...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "开启服务", Toast.LENGTH_SHORT).show();
                        startService(new Intent(this, WatchingService.class));
                    }
                } else {
                    ToastUtil.show(activity, "请先授予悬浮窗");
                    requestFloatWindowPermissionIfNeeded();
                }

                break;
            case R.id.tv_show_other_class:
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onResume() {
        if (Settings.canDrawOverlays(this)) {
            TasksWindow.setShowWindow(activity, true);
        } else {
            TasksWindow.setShowWindow(activity, false);
        }
        requestFloatWindowPermissionIfNeeded();
        super.onResume();
    }

    /**
     * 申请悬浮窗权限
     */
    private void requestFloatWindowPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            new AlertDialog.Builder(this)
                    .setMessage("是否给予当前软件悬浮窗权限")
                    .setPositiveButton("前往打开"
                            , (dialog, which) -> {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                                dialog.dismiss();
                            })
                    .setNegativeButton("取消"
                            , (dialog, which) -> {
                                setShowWindow(activity, false);
                            })
                    .setOnCancelListener(dialog -> {
                        setShowWindow(activity, false);
                    })
                    .create()
                    .show();

        }
    }


}
