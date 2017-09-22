package com.hjg.hjgapplife.application;

import com.hjg.baseapp.application.BaseApplication;
import com.hjg.hjgapplife.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import xiaofei.library.hermeseventbus.HermesEventBus;

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
        //字体库
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/tengxiang.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        //EventBus
        HermesEventBus.getDefault().init(this);//初始化HermesEventBus

    }

    public static MyApplication getApplication() {
        return app;
    }
}
