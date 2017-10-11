package com.hjg.hjgapplife.activity.md;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hjg.hjgapplife.R;

/**
 * 当layout_scrollFlags的属性为scroll时，当scrollview滑动到最顶部的时候，bar和它一起下拉
 * 当layout_scrollFlags的属性为scroll|enterAlways时，只要scrollview往下拉，bar就会出现，往上拉，bar就会消失
 */
public class AppBarLayoutActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("This is Title");
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(toolbar);


    }
}
