package com.hjg.hjgapplife.activity;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

public class CreateTwoBarActivity extends BaseOthreRenderSwipActivity {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_create_two_bar;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "生成二维码界面");
    }

    @Override
    protected void initData() {

    }


    @Override
    public void finish() {
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(0, R.anim.startactivity_down_exit);
    }
}
