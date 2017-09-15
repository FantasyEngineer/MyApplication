package com.hjg.baseapp.util;

import java.util.Map;

import static com.hjg.baseapp.util.StringUtils.isNotEmpty;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class MapUtils {
    static final String TAG = "MapUtils";

    /**
     * Map中取整数值
     *
     * @param map
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Map map, String key, int defValue) {
        if (null != map && StringUtils.isNotEmpty(key)) {
            try {
                return Integer.parseInt((String) map.get(key));
            } catch (Exception e) {
                Logs.e(TAG, e.getMessage());
            }
        }
        return defValue;
    }

    /**
     * Map中取浮点值
     *
     * @param map
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Map map, String key, int defValue) {
        if (null != map && isNotEmpty(key)) {
            try {
                return Float.parseFloat(map.get(key).toString());
            } catch (Exception e) {
            }
        }
        return defValue;
    }

    /**
     * Map根据key取值
     *
     * @param map
     * @param key
     * @return
     */
    public static Object getMapValue(Map map, Object key) {
        if (null == map || null == key)
            return "";

        if ((key instanceof String)) {
            String keystr = (String) key;
            keystr = keystr.toUpperCase();
            key = keystr;
        }
        Object value = map.get(key);
        return null == value ? "" : value;
    }

}
