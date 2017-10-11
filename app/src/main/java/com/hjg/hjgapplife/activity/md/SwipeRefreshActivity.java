package com.hjg.hjgapplife.activity.md;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import butterknife.BindView;

public class SwipeRefreshActivity extends BaseOthreRenderSwipActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_swipe_refresh;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "下拉刷新");
    }

    @Override
    protected void initData() {
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);// // 进度动画颜色
        swipeRefresh.setProgressBackgroundColorSchemeResource(R.color.gray); // 进度背景颜色
//        swipeRefresh.setRefreshing(true);//一进入界面就自动刷新
        swipeRefresh.setOnRefreshListener(this);//设置监听
//        swipeRefresh.setProgressViewEndTarget(true, 8);//设置刷新控件的终止位置，scale属性设置同上
        swipeRefresh.setSize(SwipeRefreshLayout.LARGE);//刷新控件的大小
    }

    @Override
    public void onRefresh() {
        Toast.makeText(activity, "执行了刷新", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        }, 3000);
    }
}
