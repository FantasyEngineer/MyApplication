package com.hjg.hjgapplife.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.hjg.baseapp.R;
import com.hjg.baseapp.manage.TopBarManage;
import com.hjg.baseapp.util.ACache;
import com.hjg.baseapp.util.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import xiaofei.library.hermeseventbus.HermesEventBus;


/**
 * 这里的BaseActivituy是给使用Knife插件的activity使用的。
 * 因为插件的build.gradle设置必须在app包下的build.gradle设置。
 * 如果在baseLib中就引入不到knife的包。
 * BaseActivity沉浸状态栏 使用的是StatusBarUtil
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity activity;
    private FrameLayout fl_content;//最外层布局
    protected TopBarManage topBarManage;
    protected ACache mCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不允许横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity = this;
        setContentView(R.layout.baselayout);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        View view = LayoutInflater.from(this).inflate(getContentLayout(), null);
        fl_content.addView(view);
        mCache = ACache.get(this);
        //注册eventbus
        HermesEventBus.getDefault().register(this);

        ButterKnife.bind(this);
        initBarColor();
        initTitle();
        initView();
        initData();
        initAction();
    }

    /**
     * 当有头部的时候调用这个方法初始化头部
     */
    protected abstract void initTitle();


    private void initBarColor() {
        //设置导航栏颜色
        View v = findViewById(R.id.topBar);
        topBarManage = new TopBarManage(this, v);
        topBarManage.setTopBarBackground(this.getResources().getColor(R.color.darkorange));
        //设置状态栏颜色（页面的最上面 电量显示一栏）
        StatusBarUtil.setColor(activity, getResources().getColor(R.color.darkorange), 0);
    }


//    /**
//     * 设置状态栏颜色
//     *
//     * @return
//     */
//    protected abstract int setBarColor();

    /**
     * 设置布局文件
     */

    protected abstract int getContentLayout();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化试图
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    public void initAction() {
    }


    private long lastClickTime;

    /**
     * 判断事件出发时间间隔是否超过预定值
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void startActivity(Intent intent) {
        // 防止连续点击
        if (isFastDoubleClick()) {
            Log.d("BaseActivity", "startActivity() 重复调用");
            return;
        }
        super.startActivity(intent);
    }

    /**
     * 隐藏bar
     */
    public void hideTopBar() {
        topBarManage.setVisibleTopbar(false);
    }

    /**
     * 展示bar
     */
    public void showTopBar() {
        topBarManage.setVisibleTopbar(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }

    // 更改应用字体重写的方法
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)//在ui线程执行
    public void onEventMainThread(Object object) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
