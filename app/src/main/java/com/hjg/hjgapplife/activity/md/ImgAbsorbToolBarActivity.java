package com.hjg.hjgapplife.activity.md;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hjg.hjgapplife.R;


public class ImgAbsorbToolBarActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_absorb_tool_bar);
        initView();
    }

    private void initView() {
        //获取appbarlayout的偏移量
        appBarLayout1 = (AppBarLayout) findViewById(R.id.appBarLayout1);
        appBarLayout1.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("AppBarLayoutActivity", "verticalOffset:" + verticalOffset);
            }
        });
    }
}
