package com.hjg.hjgapplife.activity.webview.plugin;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hjg.baseapp.util.Logs;
import com.hjg.baseapp.widget.dialog.LoadingProgressDialog;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class NativePlugin extends BasePlugin {

    public static final String TAG = "NativePlugin";

    public static final String NAME = "SysClientJs";
    private Activity activity;

    private WebView webView;
    private LoadingProgressDialog dialog;


    public NativePlugin(Activity activity, WebView webView, LoadingProgressDialog dialog) {
        this.activity = activity;
        this.webView = webView;
        this.dialog = dialog;
    }

    /**
     * 开启等待层
     */
    @JavascriptInterface
    public void showWaitPanel() {
        Logs.d(TAG, "showWaitPanel开启等待层msg--->");
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (dialog != null) {
                    dialog.show();
                }
            }
        });
    }

    /**
     * 开启等待层
     */
    @JavascriptInterface
    public void hideWaitPanel() {
        Logs.d(TAG, "showWaitPanel关闭等待层msg--->");
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (dialog != null && dialog.isShowing() && !activity.isFinishing()) {
                    dialog.dismiss();
                }
            }
        });
    }

}
