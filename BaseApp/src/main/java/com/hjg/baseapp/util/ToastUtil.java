package com.hjg.baseapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 快速更新弹出吐司
 */

public class ToastUtil {
    static Toast toast = null;

    public static void show(Context ctx, String msg) {
        if (toast == null) {
            toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
