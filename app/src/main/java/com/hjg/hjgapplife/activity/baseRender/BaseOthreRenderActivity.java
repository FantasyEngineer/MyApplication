package com.hjg.hjgapplife.activity.baseRender;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hjg.baseapp.R;
import com.hjg.baseapp.manage.TopBarManage;
import com.hjg.baseapp.util.ACache;
import com.hjg.hjgapplife.entity.EventBusBean;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import xiaofei.library.hermeseventbus.HermesEventBus;


/**
 * BaseOthreRenderActivity 与BaseActivity同级别
 * 不同在于状态栏的渲染方式不同，不允许侧滑删除页面
 * 其他OK
 */

public abstract class BaseOthreRenderActivity extends AppCompatActivity {
    protected Activity activity;
    private FrameLayout fl_content;//最外层布局
    protected TopBarManage topBarManage;
    protected ACache mCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //固定手机方向
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity = this;
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
        HermesEventBus.getDefault().register(activity);
        MobclickAgent.setDebugMode( true );
        setContentView(R.layout.baselayout);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        View view = LayoutInflater.from(this).inflate(getContentLayout(), null);
        fl_content.addView(view);
        mCache = ACache.get(this);
        ButterKnife.bind(this);
        initBarColor();
        initTitle();
        initData();
        initDataWithBundle(savedInstanceState);
    }

    /**
     * 设置布局文件
     */

    protected abstract int getContentLayout();

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

    /**
     * 初始化试图带bundle的
     */
    protected void initDataWithBundle(Bundle savedInstanceState) {

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

    //eventbus
    @Subscribe(threadMode = ThreadMode.MAIN)//在ui线程执行
    public void onEventMainThread(Object bean) {
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
