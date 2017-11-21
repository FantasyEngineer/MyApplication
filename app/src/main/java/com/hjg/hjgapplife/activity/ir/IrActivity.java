package com.hjg.hjgapplife.activity.ir;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;
import com.hjg.hjgapplife.activity.baseRender.LayoutConstans;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class IrActivity extends BaseOthreRenderSwipFinalActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_ir;
    }

    @Override
    protected int getIncludeLayoutType() {
        return LayoutConstans.LL;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "红外线开发");
        topBarManage.setLeftBtnBack(true, view -> {
            finish();
        });
    }

    @Override
    protected void initData() {

    }
}
