package com.hjg.hjgapplife.activity.webview.plugin;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

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

    /**
     * JS调用Android(Java)无参数的方法
     */
    @JavascriptInterface
    public void jsCallWebView() {
        Toast.makeText(activity, "JS Call Java!",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * JS调用Android(Java)含参数的方法
     *
     * @param param
     */
    @JavascriptInterface
    public void jsCallWebView(String param) {
        Toast.makeText(activity, "JS Call Java!" + param,
                Toast.LENGTH_SHORT).show();
    }


    /**
     * JS调用，销毁页面
     */
    @JavascriptInterface
    public void destory() {
        if (!activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * JS调用原生，提供方法名，原生根据方法名，调用js方法，实现刷新
     */
    @JavascriptInterface
    public void flush(String method) {
        Log.d(TAG, method);
        activity.runOnUiThread(() -> webView.loadUrl("javascript:" + method + "()"));
    }


}
