package com.hjg.hjgapplife.activity.md;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hjg.baseapp.util.StatusBarUtil;
import com.hjg.hjgapplife.R;

public class AppBarLayoutActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.darkorange), 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("This is Title");
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(toolbar);


        //获取appbarlayout的偏移量
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("AppBarLayoutActivity", "verticalOffset:" + verticalOffset);
            }
        });

    }
}
