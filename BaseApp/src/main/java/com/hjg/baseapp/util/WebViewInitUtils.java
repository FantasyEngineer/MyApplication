package com.hjg.baseapp.util;

/*
 * @创建者     xianwei
 * @创建时间   2017/5/14 13:11
 * @描述       WebView初始化
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import android.app.Activity;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewInitUtils {

	public static WebView WebSettingInit(WebView web_view,
                                         final Activity activity) {
		WebSettings ws = web_view.getSettings();
		ws.setJavaScriptEnabled(true); // 支持js
		ws.setBuiltInZoomControls(false);// 支持缩放按钮
		ws.setUseWideViewPort(true);// 设置此属性，可任意比例缩放 将图片调整到适合webview的大小
		ws.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		ws.setSupportZoom(false); // 支持缩放
		// 设置 缓存模式
		ws.setCacheMode(WebSettings.LOAD_NO_CACHE); // 关闭webview中缓存

		web_view.clearCache(true);
		web_view.setTag(true);
		// 开启 DOM storage API 功能
		ws.setDomStorageEnabled(true);

		ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
		// 开启 database storage API 功能
		ws.setDatabaseEnabled(false);

		ws.setDefaultTextEncodingName("utf-8");

		ws.setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
		ws.setAllowFileAccess(true); // 允许访问文件
		//		settings.setAppCacheEnabled(false); //设置H5的缓存关闭

		web_view.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

		});
		return web_view;
	}

}
