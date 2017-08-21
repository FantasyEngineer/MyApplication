package com.hjg.hjgapplife.activity.titlepage;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;

public class TitlePagerActivity extends BaseActivity {

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "头部pager展示");
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_title_pager;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
