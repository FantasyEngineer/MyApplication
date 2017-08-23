package com.hjg.hjgapplife.activity.titlepage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hjg.baseapp.util.ScreenUtils;
import com.hjg.baseapp.widget.NoScrollViewPager;
import com.hjg.baseapp.widget.PagerSlidingTabStrip;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;
import com.hjg.hjgapplife.activity.titlepage.adapter.MyPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class PageSlidTabActivity extends BaseActivity {
    private String[] titles = {"影片", "手办", "主题", "影片", "手办", "主题", "影片", "手办", "主题", "影片", "手办", "主题"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.tabs_act_pro)
    PagerSlidingTabStrip tabsActPro;
    @BindView(R.id.pager_act_pro)
    NoScrollViewPager pagerActPro;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "PageSlidTabActivity");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_page_slid_tab;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        initFragment();
        initAdapter();
    }

    private void initFragment() {
        fragments.clear();
        for (int i = 0; i < titles.length; i++) {
            SimpleCardFragment goodsListFragment = new SimpleCardFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            goodsListFragment.setArguments(bundle);
            fragments.add(goodsListFragment);
        }
    }

    private void initAdapter() {
        tabsActPro.setTextSize((int) ScreenUtils.sp2px(this, 15));
        pagerActPro.setNoScroll(false);
        tabsActPro.setTextColor(Color.parseColor("#aeb6c3"));
        pagerActPro.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), titles, fragments));
        tabsActPro.setViewPager(pagerActPro);
    }
}
