package com.hjg.hjgapplife.activity.md;

import android.content.Intent;

import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;

public class MDMainActivity extends BaseOtherRenderListActivity {

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "Material Design");
    }

    @Override
    public void initDataList() {
        dataList.add("v4.widget.SwipeRefreshLayout");
        dataList.add("AppBarLayout与NestedScrollView");
        dataList.add("AppBarLayout与NestedScrollView2");
        dataList.add("AppBarLayout与TableLayout");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(activity, SwipeRefreshActivity.class));
                break;
            case 1:
                startActivity(new Intent(activity, AppBarLayoutActivity.class));
                break;
            case 2:
                startActivity(new Intent(activity, AppBarLayoutActivity2.class));
                break;
            case 3:
                startActivity(new Intent(activity, AppLayoutAndTableLayoutActivity.class));
                break;

        }
    }
}
