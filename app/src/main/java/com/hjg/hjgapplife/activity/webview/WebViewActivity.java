package com.hjg.hjgapplife.activity.webview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hjg.baseapp.util.StringUtils;
import com.hjg.baseapp.widget.dialog.LoadingProgressDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderActivity;
import com.hjg.hjgapplife.activity.webview.plugin.NativePlugin;

//可看视频的webview
public class WebViewActivity extends BaseOthreRenderActivity implements TitleChangeListener, DialogInterface.OnDismissListener {
    private static final String TAG = "WebViewActivity";
    private WebView mWebView;
    private String url;
    private NativePlugin nativePlugin;
    private LoadingProgressDialog dialog;
    private ProgressBar progressBar;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initTitle() {
        String title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        if (StringUtils.isBlank(title)) {
            title = "未知";
        }
        if (StringUtils.isBlank(url)) {
            url = "https://www.baidu.com";
        }

        topBarManage.iniTop(true, title);
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    activity.finish();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initDataWithBundle(Bundle savedInstanceState) {
        //等待层
        dialog = new LoadingProgressDialog(activity, R.style.CustomProgressDialog, "加载中...");

        mWebView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //创建插件，添加插件
        nativePlugin = new NativePlugin(activity, mWebView, dialog);
        WebViewManage webViewManage = new WebViewManage(this, mWebView, nativePlugin);
        webViewManage.addJavascriptInterface(nativePlugin, NativePlugin.NAME);
        webViewManage.setTitleChangeListener(this);
        dialog.setOnDismissListener(this);
        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            mWebView.loadUrl(url);
            //mWebView.loadUrl("file:///data/bbench/index.html");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebView.stopLoading();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 供外部调用跳转到本activity
     *
     * @param context
     * @param url
     * @param title
     */
    public static void startActivityToWebView(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    //提取页面的标题填充到标题栏上
    @Override
    public void titleChanged(String title) {
        //处理头部title，防止过长
        if (!StringUtils.isBlank(title) && title.length() > 25) {
            topBarManage.iniTop(true, title.substring(0, 25));
        } else {
            topBarManage.iniTop(true, title);
        }
    }

    //页面加载嫉妒展示
    @Override
    public void progressChanged(int newProgress) {
        if (newProgress == 100) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
    }

    //当web的加载框消失的时候，停止加载网页。
    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        mWebView.stopLoading();
    }
}
