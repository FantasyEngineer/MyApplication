package com.hjg.hjgapplife.activity.recycleViewAbout;

import android.content.Intent;

import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;
import com.hjg.hjgapplife.activity.recycleViewAbout.stickyHeadRecycleview.StickyMainActivity;
import com.hjg.hjgapplife.activity.webview.WebViewActivity;

public class AboutRecycleViewMainActivity extends BaseOtherRenderListActivity {


    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "RecycleView相关");
    }

    @Override
    public void initDataList() {
        dataList.add("recycleView粘性标签");
        dataList.add("仿外卖双RecycleView联动");
        dataList.add("recyleView子项侧滑删除");
        dataList.add("RecyViewAdapterHelper");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(activity, StickyMainActivity.class));
                break;
            case 1:
                break;
            case 2:

                break;
            case 3:
                WebViewActivity.startActivityToWebView(activity, "http://www.recyclerview.org/", "RecyViewAdapterHelper");
                break;
        }
    }

}
