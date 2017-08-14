package com.hjg.baseapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.hjg.baseapp.util.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
        }
        activity = this;
        ButterKnife.bind(this);
        //设置状态栏颜色（页面的最上面 电量显示一栏）
        StatusBarUtil.setColor(activity, getResources().getColor(setBarColor()));
        initData();
        initAction();
    }

    protected abstract int setBarColor();

    /**
     * 设置布局文件
     */

    protected abstract int getContentLayout();

    /**
     * 初始化事件
     */
    protected abstract void initAction();

    /**
     * 初始化数据
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
}
