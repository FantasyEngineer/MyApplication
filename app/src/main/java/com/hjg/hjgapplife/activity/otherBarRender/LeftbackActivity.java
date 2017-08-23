package com.hjg.hjgapplife.activity.otherBarRender;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

public class LeftbackActivity extends BaseOthreRenderSwipActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_leftback;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "侧滑看一下效果");
        topBarManage.setTopBarBackground(getResources().getColor(R.color.blue));
    }

    @Override
    protected void initData() {

    }
}
