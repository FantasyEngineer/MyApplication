package com.hjg.hjgapplife.activity.takephoto;

import com.hjg.hjgapplife.activity.GIFshowFunctionAcitivity;
import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;

public class PhotoDealMainActivity extends BaseOtherRenderListActivity {
    @Override
    public void initDataList() {
        dataList.add("crop框架图片裁剪");
        dataList.add("**框架图片裁剪");
        dataList.add("高斯模糊");
        dataList.add("各种形状图片展示（圆角，圆形）");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                GIFshowFunctionAcitivity.startActivityToGIFView(activity, "crop");
                break;
            case 1:
                break;
        }
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "图片处理");
    }
}
