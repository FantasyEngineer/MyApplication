package org.icegeneral.rxjavaapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.combine.CombineLatestActivity;
import org.icegeneral.rxjavaapi.combine.JoinActivity;
import org.icegeneral.rxjavaapi.combine.MergeActivity;
import org.icegeneral.rxjavaapi.combine.StartWithActivity;
import org.icegeneral.rxjavaapi.combine.SwitchActivity;
import org.icegeneral.rxjavaapi.combine.ZipActivity;

/**
 * Created by jianjun.lin on 16/7/11.
 */

public class CombineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RXAdapter adapter = new RXAdapter(this) {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(CombineActivity.this, ZipActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(CombineActivity.this, MergeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(CombineActivity.this, CombineLatestActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(CombineActivity.this, JoinActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(CombineActivity.this, StartWithActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(CombineActivity.this, SwitchActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"Zip", "Merge", "CombineLatest", "Join", "StartWith", "Switch"});
        rv.setAdapter(adapter);
    }

}
