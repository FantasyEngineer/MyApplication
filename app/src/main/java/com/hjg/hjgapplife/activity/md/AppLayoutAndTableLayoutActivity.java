package com.hjg.hjgapplife.activity.md;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.titlepage.adapter.MyPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * tablelayout与toolbar
 */
public class AppLayoutAndTableLayoutActivity extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    String[] titles = new String[]{"节日场景", "亲子场景", "推送场景", "节日场景", "亲子场景", "推送场景"};
    ArrayList<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_layout_and_table_layout);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("This is Title");
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(toolbar);

        //初始化头部的tablelayout
        initTableLayout();
        //初始化viewpager
        initViewPager();
        //绑定
        tableBindVp();
    }


    private void initTableLayout() {
        //tab可滚动  MODE_FIXED属性可选   MODE_SCROLLABLE
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //添加页卡名称
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }
        //tab居中显示
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    }

    private void initViewPager() {
        for (int i = 0; i < titles.length; i++) {
            fragments.add(new TestFragment());
        }
        adapter = new MyPagerAdapter(this.getSupportFragmentManager(), titles, fragments);
        viewpager.setAdapter(adapter);
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = 0;
                float y = 0;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_UP:
                        if (motionEvent.getY() - y > 0) {//是往下滑动
                            Log.d("AppLayoutAndTableLayout", "是往下滑动");
                            if (Math.abs(motionEvent.getX() - x) < 50) {
                                Toast.makeText(AppLayoutAndTableLayoutActivity.this, "是往下滑动的", Toast.LENGTH_SHORT).show();
                                return true;//消耗掉这个事件
                            }
                        } else {//是往上滑动
                            Log.d("AppLayoutAndTableLayout", "是往上滑动");
                            if (Math.abs(motionEvent.getX() - x) < 50) {
                                Toast.makeText(AppLayoutAndTableLayoutActivity.this, "是往上滑动", Toast.LENGTH_SHORT).show();
                                return true;//消耗掉这个事件
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void tableBindVp() {
        //        将tablelayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
