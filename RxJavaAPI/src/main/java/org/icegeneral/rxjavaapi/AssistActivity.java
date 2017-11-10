package org.icegeneral.rxjavaapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.assist.DelayActivity;
import org.icegeneral.rxjavaapi.assist.DoActivity;
import org.icegeneral.rxjavaapi.assist.TimeIntervalActivity;
import org.icegeneral.rxjavaapi.assist.TimeOutActivity;
import org.icegeneral.rxjavaapi.assist.TimestampActivity;

/**
 * Created by jianjun.lin on 16/7/11.
 */

public class AssistActivity extends Activity {

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
                        startActivity(new Intent(AssistActivity.this, DelayActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AssistActivity.this, DoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(AssistActivity.this, TimeIntervalActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(AssistActivity.this, TimestampActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(AssistActivity.this, TimeOutActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"Delay", "Do", "TimeInterval", "Timestamp", "TimeOut", "Serialize", "Using", "ObserveOn", "SubscribeOn", "Subscribe"});
        rv.setAdapter(adapter);
    }

}
