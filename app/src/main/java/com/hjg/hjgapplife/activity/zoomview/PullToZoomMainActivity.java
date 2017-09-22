package com.hjg.hjgapplife.activity.zoomview;

import android.content.Intent;
import android.view.View;

import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;

import razerdp.popup.DialogPopup;

public class PullToZoomMainActivity extends BaseOtherRenderListActivity {

    @Override
    public void initDataList() {
        dataList.add("PullToZoomListActivity");
        dataList.add("PullToZoomScrollActivity");
        dataList.add("PullToZoomRecycleViewActivity");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(activity, PullToZoomListActivity.class));
                break;
            case 1:
                startActivity(new Intent(activity, PullToZoomScrollActivity.class));
                break;
            case 2:
                final DialogPopup dialogPopup = new DialogPopup(activity);
                dialogPopup.setSingleBtn("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogPopup.dismiss();
                    }
                });
                dialogPopup.setTitleAndContent("友情提醒", "PullToZoomRecycleViewActivity的操作详情见首页的fourfragment的展示效果");
                dialogPopup.showPopupWindow();
                break;
        }
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "下拉背景变大");
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
