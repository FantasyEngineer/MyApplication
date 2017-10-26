package com.hjg.hjgapplife.activity.baseRender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hjg.baseapp.R;
import com.hjg.baseapp.manage.TopBarManage;
import com.hjg.baseapp.util.ACache;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import xiaofei.library.hermeseventbus.HermesEventBus;


/**
 * 基类最优选择
 * 基于BaseOthreRenderActivity的侧滑关闭页面
 * 不同在于状态栏的渲染方式不同
 * 外布局可以修改是scrollview或者Relativelayoyut可以为Linearlayout
 */

public abstract class BaseOthreRenderSwipFinalActivity extends me.imid.swipebacklayout.lib.app.SwipeBackActivity {
    protected Activity activity;
    private FrameLayout fl_content;//最外层布局
    protected TopBarManage topBarManage;
    private SwipeBackLayout mSwipeBackLayout;
    protected ACache mCache;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity = this;
        //不允许横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //最终方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            //getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //建议在这个位置，可以修改外baselayout布局，外布局可以是scrollview或者Relativelayoyut
        switch (getIncludeLayoutType()) {
            case LayoutConstans.LL:
                setContentView(R.layout.baselayout);
                break;
            case LayoutConstans.RL:
                setContentView(R.layout.baserelativelayout);
                break;
            case LayoutConstans.BAR_IN_SV:
                setContentView(R.layout.basebarinscrollview);
                break;
            case LayoutConstans.BAR_OUT_SV:
                setContentView(R.layout.basebaroutscrollview);
                break;
            default:
                setContentView(R.layout.baselayout);
                break;
        }
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        View view = LayoutInflater.from(this).inflate(getContentLayout(), null);
        fl_content.addView(view);
        //缓存，类似于sharepreference
        mCache = ACache.get(this);
        //注册eventbus
        HermesEventBus.getDefault().register(this);

        ButterKnife.bind(this);
        initSwipe();
        initBarColor();
        initTitle();
        initData();
    }

    /**
     * 设置布局文件
     */

    protected abstract int getContentLayout();

    /**
     * 设置外部包裹的布局LayoutConstans字段分别代表含义
     */
    protected abstract int getIncludeLayoutType();


    /**
     * 当有头部的时候调用这个方法初始化头部
     */
    protected abstract void initTitle();


    private void initBarColor() {
        //设置导航栏颜色
        View v = findViewById(R.id.topBar);
        topBarManage = new TopBarManage(this, v);
        topBarManage.setTopBarPaddingTop(getStatusBarHeight());
        topBarManage.setTopBarBackground(this.getResources().getColor(R.color.darkorange));
    }

    /**
     * 初始化试图
     */
    protected abstract void initData();


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

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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

    // 更改应用字体重写的方法
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//在ui线程执行
    public void onEventMainThread(Object object) {
    }

}
