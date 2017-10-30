package com.hjg.hjgapplife.activity.recycleViewAbout.link;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

public class LinkRecycleViewActivity extends BaseOthreRenderSwipActivity {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_link_recycle_view;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "仿淘宝外卖双recycleView联动");
    }

    @Override
    protected void initData() {

    }
}
