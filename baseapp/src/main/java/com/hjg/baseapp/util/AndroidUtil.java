package com.hjg.baseapp.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * android客户端工具类
 *
 * @author hjg
 * @Description
 */
public class AndroidUtil {

    public static final String TAG = "AndroidUtil";

    /**
     * 获取手机唯一序列号
     * 注：如取不到设备号，则取UUID作为手机唯一序列号
     */
    public static String getDeviceUUID(Context context) {
        String uuid = getDeviceId(context);
        if (StringUtils.isEmpty(uuid)) {
            uuid = getUUID(context);
        }
        return uuid;
    }

    /**
     * 获取手机序列号
     */
    public static String getDeviceId(Context context) {
        String deviceID = null;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (!StringUtils.isEmpty(tm.getDeviceId())) {
            deviceID = tm.getDeviceId();
        }

        return deviceID;
    }

    /**
     * 在取不到设备号时候，使用UUID作为手机唯一设备号
     */
    public static final String MOBILE_SETTING = "MOBILE_SETTING";
    public static final String MOBILE_UUID = "MOBILE_UUID";

    private static String getUUID(Context context) {
        SharedPreferences mShare = context.getSharedPreferences(MOBILE_SETTING, Context.MODE_PRIVATE);
        String uuid = "";
        if (mShare != null && !StringUtils.isEmpty(mShare.getString(MOBILE_UUID, ""))) {
            uuid = mShare.getString(MOBILE_UUID, "");
        }
        if (StringUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString(MOBILE_UUID, uuid).commit();
        }
        Logs.d("getUUID", "getUUID : " + uuid);
        return uuid;
    }


    /**
     * 取得操作系统版本号
     */
    public static String getOSVersion(Context context) {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 取得操API Level是否大于14  os 4.0.0以上系统
     */
    public static boolean afterKatkit() {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取应用版本号
     */
    public static String getAppVersion(Context context) {
        String strVersion = null;
        String settingVersion = SharedPreferenceUtil.getInfoFromShared("version");
        if (StringUtils.isBlank(settingVersion)) {
            try {
                PackageInfo pi = null;
                pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                if (pi != null) {
                    strVersion = pi.versionName;
                }
            } catch (NameNotFoundException e) {
                Logs.e(TAG, e.getMessage(), e);
            }
            return strVersion;
        } else {
            return settingVersion;
        }

    }

    /**
     * 获取签名摘要
     */
    public static String getSign(Context context) {
        String strSign = null;

        try {
            int flag = PackageManager.GET_SIGNATURES;
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> apps = pm.getInstalledPackages(flag);
            Object[] objs = apps.toArray();
            for (int i = 0, j = objs.length; i < j; i++) {
                PackageInfo packageinfo = (PackageInfo) objs[i];
                String packageName = packageinfo.packageName;
                if (packageName.equals(context.getPackageName())) {
                    Signature[] temps = packageinfo.signatures;
                    Signature tmpSign = temps[0];
                    strSign = tmpSign.toCharsString();
                }
            }
        } catch (Exception e) {
            Logs.d(TAG, e.getMessage());
        }
        return strSign;
    }

    /**
     * 判断手机是否ROOT
     */
    public static boolean isSystemRoot() {
        boolean isRoot = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                isRoot = false;
            } else {
                isRoot = true;
            }
            Logs.d("TAG", "isRoot  = " + isRoot);
        } catch (Exception e) {
            Logs.d(TAG, e.getMessage());
        }
        return isRoot;
    }

    //隐藏键盘
    public static void hideKeyboard(Context activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //获取进程名
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager.getRunningAppProcesses()) {
            if (appProcessInfo.pid == pid) {
                return appProcessInfo.processName;
            }
        }
        return null;
    }
}
