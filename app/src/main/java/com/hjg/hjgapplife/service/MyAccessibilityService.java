package com.hjg.hjgapplife.service;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;


public class MyAccessibilityService extends AccessibilityService {
    public int state = 0;

    /**
     * 当启动服务的时候就会被调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    /**
     * 中断服务的回调
     */
    @Override
    public void onInterrupt() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED://有通知
                Log.d("MyAccessibilityService", "有通知");
                //判断是否解锁屏幕
                unlockScreen();
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        Log.i("MyAccessibilityService", "text:" + content);
                        if (content.contains("打卡")) {
                            state = 0;//重置状态
                            //模拟打开通知栏消息
                            if (event.getParcelableData() != null
                                    &&
                                    event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;

            //当窗口的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d("MyAccessibilityService", "窗口的状态");
                state++;
                if (state == 2) {//点击聊天界面的返回
                    clickID(event, "com.alibaba.android.rimet:id/img_back", "android.widget.ImageView");
                } else if (state == 3) {//点击主界面的工作
                    clickID(event, "com.alibaba.android.rimet:id/home_bottom_tab_button_work", "android.widget.FrameLayout");
                }
                break;
            //当窗口的内容发生变化的时候
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.d("MyAccessibilityService", "当窗口的内容发生变化的时候");
                AccessibilityNodeInfo accessibilityNodeInfo = event.getSource().getParent();

                if (state == 3) {//在工作的fragment下，去找考勤打卡
                    findAttendanceCard(accessibilityNodeInfo, event);
                }
                break;
        }
    }

    /**
     * 在工作界面查找考勤打卡
     *
     * @param accessibilityNodeInfo
     * @param event
     */
    private void findAttendanceCard(AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityEvent event) {
        // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
        List<AccessibilityNodeInfo> stop_nodes = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.alibaba.android.rimet:id/oa_fragment_gridview");
        Log.d("MyAccessibilityService", "是否找到：" + stop_nodes.size());
        if (stop_nodes != null && !stop_nodes.isEmpty() && stop_nodes.size() != 0) {
            AccessibilityNodeInfo node = stop_nodes.get(0);//获取到了recycleview（页面中有且只有一个）
//                        node.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
            AccessibilityNodeInfo child1 = node.getChild(5);
//                        if (child1 != null && isHavaDK(child1)) {
//                            Log.d("MyAccessibilityService", "打卡1");
            if (child1 != null) {
                child1.performAction(AccessibilityNodeInfo.ACTION_CLICK); // click
            } else {
                Log.d("MyAccessibilityService", "child1为空重新尝试");
            }
        } else {
            Log.d("MyAccessibilityService", "重新尝试");
        }
    }


    /**
     * 判断屏幕是否锁屏
     */
    private void unlockScreen() {
        KeyguardManager keyguardManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        String islocktxt = keyguardManager.inKeyguardRestrictedInputMode() ? "锁屏了" : "屏幕已经解锁";
        if (keyguardManager.inKeyguardRestrictedInputMode()) {
            wakeAndUnlock();
        }
    }

    /**
     * 唤醒和解锁
     */
    private void wakeAndUnlock() {
        PowerManager.WakeLock wl = null;
        KeyguardManager.KeyguardLock kl = null;
        //获取电源管理器对象
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire();
        //得到键盘锁管理器对象
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
    }

    /**
     * 是否有考勤打卡字样
     *
     * @param child1
     * @return
     */
    public boolean isHavaDK(AccessibilityNodeInfo child1) {
        for (int i = 0; i < child1.getChildCount(); i++) {
            if (child1.getChild(0).getText().equals("考勤打卡")) {
                return true;
            }
        }
        return false;
    }


    /**
     * 模拟点击
     *
     * @param event      事件
     * @param text       按钮文字
     * @param widgetType 按钮类型，如android.widget.Button，android.widget.TextView
     */

    private void clickText(AccessibilityEvent event, String text, String widgetType) {
        // 事件页面节点信息不为空
        if (event.getSource() != null) {
            Log.d("MyAccessibilityService", "点击工作");
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = event.getSource().findAccessibilityNodeInfosByText(text);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        Log.d("MyAccessibilityService", "找到工作");
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            Log.d("MyAccessibilityService", "可以点击");
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
        }
    }

    /**
     * 模拟点击
     *
     * @param event      事件img_back
     * @param id         按钮id
     * @param widgetType 按钮类型，如android.widget.Button，android.widget.TextView
     */
    private void clickID(AccessibilityEvent event, String id, String widgetType) {
        // 事件页面节点信息不为空
        if (event.getSource() != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = event.getSource().findAccessibilityNodeInfosByViewId(id);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
        }
    }


}
