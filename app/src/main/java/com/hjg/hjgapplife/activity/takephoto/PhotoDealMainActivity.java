package com.hjg.hjgapplife.activity.takephoto;

import android.content.Intent;

import com.hjg.hjgapplife.activity.GIFshowFunctionAcitivity;
import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;
import com.hjg.hjgapplife.activity.takephoto.glide.GlideUseActivity;

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
            case 2:
                break;
            case 3:
                startActivity(new Intent(activity, GlideUseActivity.class));
                break;
        }
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "图片处理");
    }
}
