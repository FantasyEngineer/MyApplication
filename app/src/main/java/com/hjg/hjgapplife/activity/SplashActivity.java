package com.hjg.hjgapplife.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.hjg.baseapp.activity.BaseActivity;
import com.hjg.hjgapplife.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int setBarColor() {
        return R.color.transp;
    }

    @Override
    protected boolean setBarVisiable() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initAction() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.finish();
                activity.startActivity(new Intent(activity, MainActivity.class));
            }
        }, 1000);

    }

    @Override
    protected void initData() {

    }
}
