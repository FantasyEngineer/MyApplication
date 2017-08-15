package com.hjg.baseapp.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.hjg.baseapp.util.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by hjg on 2017/8/14 0014.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity activity;
    private FrameLayout fl_content;//最外层布局
    protected TopBarManage topBarManage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (setBarVisiable()) {
            setContentView(R.layout.baselayout);
            fl_content = (FrameLayout) findViewById(R.id.fl_content);
            View view = LayoutInflater.from(this).inflate(getContentLayout(), null);
            fl_content.addView(view);
            initBarColor();
            initTitle();
        } else {
            setContentView(getContentLayout());
        }
        ButterKnife.bind(this);
        activity = this;
        //设置状态栏颜色（页面的最上面 电量显示一栏）
        StatusBarUtil.setColor(activity, getResources().getColor(setBarColor()), 0);
        initData();
        initAction();
    }

    /**
     * 当有头部的时候调用这个方法初始化头部
     */
    public void initTitle() {

    }

    ;

    private void initBarColor() {
        //设置导航栏颜色
        View v = findViewById(R.id.topBar);
        v.setBackgroundColor(getResources().getColor(setBarColor()));
        topBarManage = new TopBarManage(this, v);
    }


    /**
     * 设置状态栏颜色
     *
     * @return
     */
    protected abstract int setBarColor();

    /**
     * 设置是否需要默认的顶部状态栏
     *
     * @return
     */
    protected abstract boolean setBarVisiable();

    /**
     * 设置布局文件
     */

    protected abstract int getContentLayout();


    /**
     * 初始化数据
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
}
