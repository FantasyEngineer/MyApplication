/**
 * Created on 2015-9-6 下午4:58:40
 */
package com.hjg.baseapp.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * @author hjg
 * @Description:activity工具类
 */
public class ActivityUtils {
    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某一个类是否存在任务栈里面
     *
     * @return
     */
    public static boolean isExsitMainActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    /**
     * 判断activtity是否处于栈顶
     * true 栈顶  false 不在栈顶
     */
    public static boolean isActivityTop(Context context, Class cls) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = am.getRunningTasks(1);
        String name = "";
        if (runningTaskInfos != null && !runningTaskInfos.isEmpty()) {
            name = runningTaskInfos.get(0).topActivity.getClassName();
        } else {
            name = "name";
        }
        return name.equals(cls.getName());

    }
}
