package com.hjg.hjgapplife.application;

import com.hjg.baseapp.application.BaseApplication;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(mApp);
    }
}
