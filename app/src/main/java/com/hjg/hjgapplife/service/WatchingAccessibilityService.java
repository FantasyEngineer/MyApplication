package com.hjg.hjgapplife.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.hjg.baseapp.widget.TasksWindow;
import com.hjg.hjgapplife.activity.MainActivity;
import com.hjg.hjgapplife.notification.NotificationActionReceiver;

/**
 * Created by Wen on 1/14/15.
 */
public class WatchingAccessibilityService extends AccessibilityService {
    private static WatchingAccessibilityService sInstance;
    Handler handler = new Handler();

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("WatchingAccessibilitySe", event.getPackageName() + "\n" + event.getClassName());
        if (TasksWindow.canShowWindow(this)) {
            TasksWindow.show(getApplicationContext(), event.getPackageName() + "\n" + event.getClassName());
        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        sInstance = this;
        Log.d("WatchingAccessibilitySe", "onServiceConnected");
//        NotificationActionReceiver.showNotification(this, false);
        super.onServiceConnected();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        sInstance = null;
        TasksWindow.dismiss();
        NotificationActionReceiver.cancelNotification(this);
        return super.onUnbind(intent);
    }

    public static WatchingAccessibilityService getInstance() {
        return sInstance;
    }

}
