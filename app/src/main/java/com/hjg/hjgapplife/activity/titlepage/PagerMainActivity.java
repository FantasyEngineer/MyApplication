package com.hjg.hjgapplife.activity.titlepage;

import android.content.Intent;
import android.view.View;

import com.hjg.hjgapplife.activity.base.BaseListActivity;
import com.hjg.hjgapplife.activity.md.AppLayoutAndTableLayoutActivity;

public class PagerMainActivity extends BaseListActivity {


    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "Pager样式展示列表");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initDataList() {
        dataList.add("PagerSlidingTabStrip");
        dataList.add("SlidingTabLayout");
        dataList.add("CommonTabLayout");
        dataList.add("SegmentTabLayout");
        dataList.add("安卓原生TabLayout");
    }

    @Override
    public void OnItemClick(int i) {

        switch (i) {
            case 0:
                startActivity(new Intent(activity, PageSlidTabActivity.class));
                break;
            case 1:
                startActivity(new Intent(activity, SlidingTabActivity.class));
                break;
            case 2:
                startActivity(new Intent(activity, CommonTabActivity.class));

                break;
            case 3:
                startActivity(new Intent(activity, SegmentTabActivity.class));

                break;
            case 4:
                startActivity(new Intent(activity, AppLayoutAndTableLayoutActivity.class));

                break;
        }
    }
}
