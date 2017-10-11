package com.hjg.hjgapplife.activity.md;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hjg.baseapp.util.StatusBarUtil;
import com.hjg.hjgapplife.R;

public class AppBarLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.darkorange), 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("This is Title");
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(toolbar);

    }
}
