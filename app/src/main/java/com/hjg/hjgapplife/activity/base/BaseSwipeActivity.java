package com.hjg.hjgapplife.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.hjg.baseapp.R;
import com.hjg.baseapp.manage.TopBarManage;
import com.hjg.baseapp.util.ACache;
import com.hjg.baseapp.util.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import xiaofei.library.hermeseventbus.HermesEventBus;


/**
 * 无用的基类（采用的是系统的头部布局，可以侧滑删除页面）
 * 这里的BaseActivituy是给使用Knife插件的activity使用的。
 * 因为插件的build.gradle设置必须在app包下的build.gradle设置。
 * 如果在baseLib中就引入不到knife的包。
 */

public abstract class BaseSwipeActivity extends me.imid.swipebacklayout.lib.app.SwipeBackActivity {
    protected Activity activity;
    private FrameLayout fl_content;//最外层布局
    protected TopBarManage topBarManage;
    private SwipeBackLayout mSwipeBackLayout;
    protected ACache mCache;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题栏
        activity = this;
        HermesEventBus.getDefault().register(activity);
        setContentView(R.layout.baselayout);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        View view = LayoutInflater.from(this).inflate(getContentLayout(), null);
        fl_content.addView(view);
        mCache = ACache.get(activity);
        ButterKnife.bind(this);
        initSwipe();
        initBarColor();
        initTitle();
        initData();
        initAction();
    }

    /**
     * 初始化侧滑状态
     */
    protected void initSwipe() {
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL,EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                vibrate(20);
            }

            @Override
            public void onScrollOverThreshold() {
                vibrate(20);
            }
        });
    }

    /**
     * 震动
     *
     * @param duration 震动时间
     */
    public void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
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
        HermesEventBus.getDefault().unregister(activity);

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
