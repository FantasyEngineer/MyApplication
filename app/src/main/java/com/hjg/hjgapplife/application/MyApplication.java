package com.hjg.hjgapplife.application;

import com.hjg.baseapp.application.BaseApplication;
import com.hjg.hjgapplife.R;
import com.umeng.analytics.MobclickAgent;

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
        //字体库
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/tengxiang.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        //EventBus  打开注释报错： java.lang.NoClassDefFoundError: Failed resolution of: Landroid/support/v7/app/ActionBarActivity;
//        HermesEventBus.getDefault().init(this);//初始化HermesEventBus
        //友盟普通统计场景类型
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setDebugMode(true);

    }

    public static MyApplication getApplication() {
        return app;
    }
}
