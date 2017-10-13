package com.hjg.hjgapplife.activity.md;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hjg.hjgapplife.R;

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
    //    @BindView(R.id.viewpager)
//    ViewPager viewpager;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_layout_and_table_layout);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("This is Title");
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(toolbar);


        //tab可滚动  MODE_FIXED属性可选   MODE_SCROLLABLE
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //给tab页卡起名字
        for (int i = 0; i < 8; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("列表=" + i + "   "));
        }

        //设置tab的点击监听器
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(AppLayoutAndTableLayoutActivity.this, tab.getPosition() + ":" + tab.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
