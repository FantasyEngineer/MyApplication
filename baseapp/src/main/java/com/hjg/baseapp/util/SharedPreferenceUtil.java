package com.hjg.baseapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hjg.baseapp.application.BaseApplication;

/**
 * SharedPreference工具类
 *
 * @Description
 * @Class SharedPreferenceUtil
 */
public class SharedPreferenceUtil {

    public final static String SP_NAME = "SP_NAME_INFO";
    private static SharedPreferences preferences = BaseApplication.getInstance()
            .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

//	public static boolean removeData(String key) {
//		preferences.edit().remove(key).commit();
//		return true;
//	}
//
//	public static boolean removeAllData() {
//		preferences.edit().clear().commit();
//		return true;
//	}

    public static String getInfoFromShared(String key) {
        return preferences.getString(key, null);
    }

    public static String getInfoFromShared(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static boolean setInfoToShared(String key, String value) {
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static boolean getInfoFromShared(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static boolean setInfoToShared(String key, boolean value) {
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return true;
    }

    public static int getInfoFromShared(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static boolean setInfoToShared(String key, int value) {
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
        return true;
    }

    //判断某个key是否已经存在
//	public static boolean contains(String key) {
//		return preferences.contains(key);
//	}
}
