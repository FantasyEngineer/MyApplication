package com.hjg.hjgapplife.activity.recycleViewAbout;

import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;

public class AboutRecycleViewMainActivity extends BaseOtherRenderListActivity {


    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "RecycleView相关");
    }

    @Override
    public void initDataList() {
        dataList.add("recycleView吸顶");
        dataList.add("仿外卖双RecycleView联动");
        dataList.add("recyleView子项侧滑删除");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:

                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

}
