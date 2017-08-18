package com.hjg.hjgapplife.application;

import com.hjg.baseapp.application.BaseApplication;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class MyApplication extends BaseApplication {
    private static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        //        CrashHandler.getInstance().init(mApp);
        app = this;
    }

    public static MyApplication getApplication() {
        return app;
    }
}
