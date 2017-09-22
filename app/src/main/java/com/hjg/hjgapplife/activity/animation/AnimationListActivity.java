package com.hjg.hjgapplife.activity.animation;

import android.content.Intent;

import com.hjg.hjgapplife.activity.base.BaseListActivity;

public class AnimationListActivity extends BaseListActivity {


    @Override
    public void initDataList() {
        dataList.add("卡片翻转，翻牌效果");
        dataList.add("仿淘宝点击商品加入到购物车特效");
        dataList.add("仿淘宝点击购物参数选择效果");
        dataList.add("贝塞尔曲线");
        dataList.add("高光部分界面");
        dataList.add("仿百度地图上拉滑动效果");
        dataList.add("济南汽车站首页效果");
    }

    @Override
    public void OnItemClick(int i) {

        switch (i) {
            case 0:
                startActivity(new Intent(activity, CardFlipActivity.class));
                break;
            case 1:
                startActivity(new Intent(activity, ScaleActivity.class));
                break;
            case 2:
                startActivity(new Intent(activity, RotationActivity.class));

                break;
            case 3:
                startActivity(new Intent(activity, BezierLineActivity.class));
                break;
            case 4:
                startActivity(new Intent(activity, ShimmerActivity.class));
                break;
            case 5:
                startActivity(new Intent(activity, LikeBDMapActivity.class));
                break;
            case 6:
                startActivity(new Intent(activity, AutoUPChangeActivity.class));
                break;
        }
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "项目工程中用到的动画效果");
    }

    @Override
    protected void initData() {

    }
}
