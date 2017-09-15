package com.hjg.hjgapplife.application;

import com.hjg.baseapp.application.BaseApplication;
import com.hjg.hjgapplife.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/tengxiang.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static MyApplication getApplication() {
        return app;
    }
}
