package com.hjg.baseapp.application;

import android.app.Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class BaseApplication extends Application {
    public static final String TAG = "BaseApplication";

    protected static BaseApplication mApp;

    public static BaseApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

//    /**
//     * 读取asset目录下json格式的文件
//     *
//     * @param fileName
//     * @return
//     * @author flueky zkf@yitong.com.cn
//     * @date 2015-4-9 下午5:18:45
//     */
//    public String getFromAssets(String fileName) {
//        String line = null;
//        try {
//            InputStreamReader inputReader = new InputStreamReader(
//                    getApplicationContext().getResources().getAssets()
//                            .open(fileName));
//            BufferedReader bufReader = new BufferedReader(inputReader);
//            // 只读一行
//            line = bufReader.readLine();
//        } catch (Exception e) {
//            Log.d(TAG, e.getMessage());
//        }
//        return line;
//    }
}
