package com.hjg.hjgapplife.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.hjg.baseapp.widget.TasksWindow;

/**
 * 监听窗口发生变化的service（辅助功能的开启）
 */
public class WatchingAccessibilityService extends AccessibilityService {
    private static WatchingAccessibilityService sInstance;

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("WatchingAccessibilitySe", event.getPackageName() + "\n" + event.getClassName());
        TasksWindow.show(getApplicationContext(), event.getPackageName() + "\n" + event.getClassName());
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        sInstance = this;
        super.onServiceConnected();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        sInstance = null;
        TasksWindow.dismiss();
        return super.onUnbind(intent);
    }

    public static WatchingAccessibilityService getInstance() {
        return sInstance;
    }

}
