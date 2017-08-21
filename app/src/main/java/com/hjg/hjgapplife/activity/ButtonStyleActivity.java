package com.hjg.hjgapplife.activity;

import android.view.View;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;

public class ButtonStyleActivity extends BaseActivity {

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "Button的各种样式");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_button_style;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
