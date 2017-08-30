package com.hjg.hjgapplife.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_AND_WIFI = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initTitle() {
        hideTopBar();
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initAction() {
    }

    @Override
    protected void initData() {
        initPermission();
    }

    //, Manifest.permission.SYSTEM_ALERT_WINDOW请求权限默认在小米是拒绝的
    private void initPermission() {
        String[] perms = {Manifest.permission.CALL_PHONE
                , Manifest.permission.CAMERA
                , Manifest.permission.ACCESS_FINE_LOCATION};
        //检查有没有权限
        if (EasyPermissions.hasPermissions(this, perms)) {
            jumpActivty();
        } else {
            //没有权限申请权限
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",
                    RC_CAMERA_AND_WIFI, perms);
        }

    }


    //已经被授权的
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Toast.makeText(activity, "权限同意", Toast.LENGTH_SHORT).show();
        jumpActivty();
    }

    //    已经被拒绝的
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Toast.makeText(activity, "权限拒绝", Toast.LENGTH_SHORT).show();
        for (String s : perms) {
            Log.d("SplashActivity", s);
        }
        new AppSettingsDialog.Builder(this).build().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void jumpActivty() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.finish();
                        activity.startActivity(new Intent(activity, MainActivity.class));
                    }
                });

            }
        }, 200);
    }
}
