package com.hjg.hjgapplife.activity.zoomview;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.hjg.baseapp.widget.zoomview.PullToZoomScrollViewEx;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import java.util.ArrayList;

import razerdp.listener.OnItemListener;
import razerdp.popup.AsDropDownPopup;

public class PullToZoomScrollActivity extends BaseOthreRenderSwipActivity implements OnItemListener {
    private PullToZoomScrollViewEx scrollView;
    private ArrayList list = new ArrayList();
    private AsDropDownPopup asDropDownPopup;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_pull_to_zoom_scroll;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "PullToZoomScrollActivity");
        topBarManage.setRightButtonImgAndTxt(true, null, "功能", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asDropDownPopup.showPopupWindow(topBarManage.getRightBtn());
            }
        });
    }

    @Override
    protected void initData() {
        list.add("正常的滑动消失");
        list.add("覆盖式的消失");
        list.add("隐藏头部布局");
        list.add("展示头部布局");
        list.add("禁止下拉放大");
        list.add("支持下拉放大");
        asDropDownPopup = new AsDropDownPopup(activity, list);
        asDropDownPopup.setOnItemListener(this);

        loadViewForCode();
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        scrollView.getPullRootView().findViewById(R.id.tv_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick -->");
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick -->");
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick -->");
            }
        });
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
    }

    @Override
    public void onItem(AdapterView<?> adapterView, View view, int i, long l) {
        asDropDownPopup.dismiss();
        switch (i) {
            case 0:
                scrollView.setParallax(false);
                break;
            case 1:
                scrollView.setParallax(true);
                break;
            case 2:
                scrollView.setHideHeader(false);
                break;
            case 3:
                scrollView.setHideHeader(true);
                break;
            case 4:
                scrollView.setZoomEnabled(false);
                break;
            case 5:
                scrollView.setZoomEnabled(true);
                break;
        }
    }

    private void loadViewForCode() {
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(this).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
    }

}
