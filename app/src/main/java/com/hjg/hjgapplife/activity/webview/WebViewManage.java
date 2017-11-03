package com.hjg.hjgapplife.activity.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.webview.plugin.NativePlugin;


/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class WebViewManage {
    private WebView mWebView;
    private Context context;
    private WebSettings settings;
    private MyWebChromeClient mWebChromeClient;
    private static final String TAG = "WebViewManage";
    private TitleChangeListener titleChangeListener;//标题变化监听
    private NativePlugin plugin;

    public WebViewManage(Context context, WebView mWebView, NativePlugin plugin) {
        this.context = context;
        this.mWebView = mWebView;
        this.plugin = plugin;
        initWebSettings();
    }

    private void initWebSettings() {
        mWebChromeClient = new MyWebChromeClient();
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 去掉底部和右边的滚动条
        mWebView.setWebViewClient(new MyWebViewClient());
        // 设置webview
        settings = mWebView.getSettings();
        //是否显示网页缩放按钮
        settings.setBuiltInZoomControls(false);
        //可能的话使所有列的宽度不超过屏幕宽度，NORMAL  、  SINGLE_COLUMN
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //设置加载进来的页面自适应手机屏幕
        settings.setUseWideViewPort(false);
        //// 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(true);
        settings.setSaveFormData(true);
        //打开javasc
        settings.setJavaScriptEnabled(true);
        //允许网页获取地理位置
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        settings.setDomStorageEnabled(true);
        //网页加载速度慢，将图片阻塞，等到页面加载结束之后再加载图片 加载图片放在最后加载渲染
//        settings.setBlockNetworkImage(true);
        settings.setNeedInitialFocus(false);// 设置是否可以访问文件


    }


    private class MyWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            Log.i(TAG, "here in on ShowCustomView");
        }

        @Override
        public void onHideCustomView() {
            Log.i(TAG, "set it to webVew");
        }

        // Html中，视频（video）控件在没有播放的时候将给用户展示一张“海报”图片（预览图）。
//        其预览图是由Html中video标签的poster属性来指定的。如果开发者没有设置poster属性, 则可以通过这个方法来设置默认的预览图。
//        @Override
//        public Bitmap getDefaultVideoPoster() {
//            Log.i(TAG, "here in on getDefaultVideoPoster");
//            if (mDefaultVideoPoster == null) {
//                mDefaultVideoPoster = BitmapFactory.decodeResource(
//                        getResources(), R.drawable.default_video_poster);
//            }
//            return mDefaultVideoPoster;
//        }


        //      播放视频时，在第一帧呈现之前，需要花一定的时间来进行数据缓冲。ChromeClient可以使用这个函数来提供一个在数据缓冲时显示的视图。
//      例如,ChromeClient可以在缓冲时显示一个转轮动画。
        @Override
        public View getVideoLoadingProgressView() {
            Log.i(TAG, "here in on getVideoLoadingPregressView");
            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                mVideoProgressView = inflater.inflate(R.layout.video_loading_progress, null);
            }
            return mVideoProgressView;
        }

        //获得所有访问历史项目的列表，用于链接着色。
        @Override
        public void getVisitedHistory(ValueCallback<String[]> callback) {
            Log.d(TAG, "callback:" + callback);
            super.getVisitedHistory(callback);
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (titleChangeListener != null) {
                titleChangeListener.titleChanged(title);
            }
//            ((Activity) context).setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (titleChangeListener != null) {
                titleChangeListener.progressChanged(newProgress);
            }
//            ((Activity) context).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "shouldOverrideUrlLoading=====" + url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //开启等待层
            plugin.showWaitPanel();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //关闭等待层
            plugin.hideWaitPanel();
            //开始加载相关图片
            settings.setBlockNetworkImage(false);
            super.onPageFinished(view, url);
        }
    }

    public void setTitleChangeListener(TitleChangeListener titleChangeListener) {
        this.titleChangeListener = titleChangeListener;
    }


    /**
     * 添加javascript插件
     *
     * @param plugin
     * @param name
     */
    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object plugin, String name) {
        mWebView.addJavascriptInterface(plugin, name);
    }

}
