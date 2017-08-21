package com.hjg.baseapp.util;

import android.util.Log;

/**
 * 安卓系统日志打印
 *
 * @author hjg
 *         Copyright (c) 2015 Shanghai P&C Information Technology Co., Ltd.
 */
public class Logs {

    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;
    public static final int NONE = Log.ERROR + 1;

    // 当前日志等级
    public static int LOGLEVEL = Log.VERBOSE;

    /**
     * 关闭日志
     */
    public static void closeLogs() {
        LOGLEVEL = Logs.NONE;
    }

    /**
     * 设置日志等级.
     *
     * @param logLevel
     */
    public static void setLogLevel(int logLevel) {
        LOGLEVEL = logLevel;
    }

    /**
     * 判断某个等级日志能否被打印
     */
    public static boolean isLoggable(int logLevel) {
        return (logLevel >= LOGLEVEL);
    }

    /**
     * Verbose 日志.
     *
     * @param tag
     * @param s
     */
    public static void v(String tag, String s) {
        if (Logs.VERBOSE >= LOGLEVEL) Log.v(tag, s + "");
    }

    /**
     * Debug 日志.
     *
     * @param tag
     * @param s
     */
    public static void d(String tag, String s) {
        if (Logs.DEBUG >= LOGLEVEL) Log.d(tag, s + "");
    }

    /**
     * Info 日志.
     *
     * @param tag
     * @param s
     */
    public static void i(String tag, String s) {
        if (Logs.INFO >= LOGLEVEL) Log.i(tag, s + "");
    }

    /**
     * Warning 日志.
     *
     * @param tag
     * @param s
     */
    public static void w(String tag, String s) {
        if (Logs.WARN >= LOGLEVEL) Log.w(tag, s + "");
    }

    /**
     * Error 日志.
     *
     * @param tag
     * @param s
     */
    public static void e(String tag, String s) {
        if (Logs.ERROR >= LOGLEVEL) Log.e(tag, s + "");
    }

    /**
     * Verbose 日志.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void v(String tag, String s, Throwable e) {
        if (Logs.VERBOSE >= LOGLEVEL) Log.v(tag, s + "", e);
    }

    /**
     * Debug 日志.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void d(String tag, String s, Throwable e) {
        if (Logs.DEBUG >= LOGLEVEL) Log.d(tag, s + "", e);
    }

    /**
     * Info 日志.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void i(String tag, String s, Throwable e) {
        if (Logs.INFO >= LOGLEVEL) Log.i(tag, s + "", e);
    }

    /**
     * Warning 日志.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void w(String tag, String s, Throwable e) {
        if (Logs.WARN >= LOGLEVEL) Log.w(tag, s + "", e);
    }

    /**
     * Error 日志.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void e(String tag, String s, Throwable e) {
        if (Logs.ERROR >= LOGLEVEL) Log.e(tag, s + "", e);
    }

    /**
     * Verbose 格式化日志.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void v(String tag, String s, Object... args) {
        if (Logs.VERBOSE >= LOGLEVEL) Log.v(tag, String.format(s, args));
    }

    /**
     * Debug 格式化日志.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void d(String tag, String s, Object... args) {
        if (Logs.DEBUG >= LOGLEVEL) Log.d(tag, String.format(s, args));
    }

    /**
     * Info 格式化日志.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void i(String tag, String s, Object... args) {
        if (Logs.INFO >= LOGLEVEL) Log.i(tag, String.format(s, args));
    }

    /**
     * Warning 格式化日志.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void w(String tag, String s, Object... args) {
        if (Logs.WARN >= LOGLEVEL) Log.w(tag, String.format(s, args));
    }

    /**
     * Error 格式化日志.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void e(String tag, String s, Object... args) {
        if (Logs.ERROR >= LOGLEVEL) Log.e(tag, String.format(s, args));
    }
}
