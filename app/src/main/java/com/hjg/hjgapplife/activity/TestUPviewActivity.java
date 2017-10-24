package com.hjg.hjgapplife.activity;

import android.widget.Toast;

import com.hjg.baseapp.widget.UpView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

public class TestUPviewActivity extends BaseOthreRenderSwipActivity {

    private UpView upView;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_test_upview;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "UPVIEW");
    }

    @Override
    protected void initData() {
        upView = (UpView) findViewById(R.id.upView);
        upView.setOnClickListener(view -> Toast.makeText(activity, "此处点击有效果", Toast.LENGTH_SHORT).show());
    }
}
