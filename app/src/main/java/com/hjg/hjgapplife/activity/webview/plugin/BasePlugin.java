package com.hjg.hjgapplife.activity.webview.plugin;

/**
 * 插件基类，项目中插件需要继承此类，防止js注入
 */

public class BasePlugin {

    /**
     * 防止js注入
     */
    public Object getClass(Object o) {
        return null;
    }
}
