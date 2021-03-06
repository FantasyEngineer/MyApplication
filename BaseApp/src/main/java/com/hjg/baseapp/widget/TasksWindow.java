package com.hjg.baseapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjg.baseapp.R;

public class TasksWindow {

    private static WindowManager.LayoutParams sWindowParams;
    private static WindowManager sWindowManager;
    private static View infoView;
    private static TextView tv_name;

    private static View init(Context context) {
        if (infoView == null) {
            if (context == null) return null;
            sWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

            sWindowParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    Build.VERSION.SDK_INT <= Build.VERSION_CODES.N ? WindowManager.LayoutParams.TYPE_SYSTEM_ALERT : WindowManager.LayoutParams.TYPE_PHONE, 0x18,
                    PixelFormat.TRANSLUCENT);
            //部分机器可以使用TYPE_TOAST
            sWindowParams.gravity = Gravity.START | Gravity.TOP;
            infoView = LayoutInflater.from(context).inflate(R.layout.window_activity_info, null);
        }
        return infoView;
    }

    public static void show(Context context, String text) {
        if (sWindowManager == null) {
            init(context);
            tv_name = (TextView) infoView.findViewById(R.id.tv_name);
            tv_name.setText(text);
            try {
                sWindowManager.addView(infoView, sWindowParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tv_name.setText(text);
        }
    }


    public static void dismiss() {
        View infoView = init(null);
        try {
            sWindowManager.removeView(infoView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //悬浮窗是否正在展示
    public static boolean isShowWindow(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("show_window", false);
    }

    //设置悬浮窗展示状态
    public static void setShowWindow(Context context, boolean isShow) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("show_window", isShow).apply();
    }

}
