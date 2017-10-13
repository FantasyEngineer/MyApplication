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
        dataList.add("SwipeRefreshLayout实现刷新");
        dataList.add("文字缩小吸附在toolbar");
        dataList.add("ScrollView滑动使得toolbar的显示和消失");
        dataList.add("AppBarLayout与TableLayout");
        dataList.add("仿图片缩小吸附到toolbar上");
        dataList.add("Behavior难度1");
        dataList.add("吸顶效果");
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
            case 4:
                startActivity(new Intent(activity, ImgAbsorbToolBarActivity.class));
                break;
            case 5:
                startActivity(new Intent(activity, Behavior1Activity.class));
                break;
            case 6:
                startActivity(new Intent(activity, Behavior2likeUCActivity.class));
                break;

        }
    }
}
