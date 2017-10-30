package com.hjg.hjgapplife.activity.recycleViewAbout;

import android.content.Intent;

import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;
import com.hjg.hjgapplife.activity.recycleViewAbout.BaseRecycleAdapter.RecycleViewAnimationActivity;
import com.hjg.hjgapplife.activity.recycleViewAbout.BaseRecycleAdapter.RecycleViewHeaderActivity;
import com.hjg.hjgapplife.activity.recycleViewAbout.BaseRecycleAdapter.RecycleViewflushActivity;
import com.hjg.hjgapplife.activity.recycleViewAbout.BaseRecycleAdapter.SingleSelectActivity;
import com.hjg.hjgapplife.activity.recycleViewAbout.link.LinkRecycleViewActivity;
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
        dataList.add("RecyViewAdapterHelper官网");
        dataList.add("RecyView滑动+侧滑删除动画");
        dataList.add("recycleView的头布局和尾布局");
        dataList.add("recycleView的上拉刷新");
        dataList.add("recycleView的单选");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(activity, StickyMainActivity.class));
                break;
            case 1:
                startActivity(new Intent(activity, LinkRecycleViewActivity.class));

                break;
            case 2:
                WebViewActivity.startActivityToWebView(activity, "http://www.recyclerview.org/", "RecyViewAdapterHelper");
                break;
            case 3:
                startActivity(new Intent(activity, RecycleViewAnimationActivity.class));
                break;
            case 4:
                startActivity(new Intent(activity, RecycleViewHeaderActivity.class));
                break;
            case 5:
                startActivity(new Intent(activity, RecycleViewflushActivity.class));
                break;
            case 6:
                startActivity(new Intent(activity, SingleSelectActivity.class));
                break;
        }
    }

}
