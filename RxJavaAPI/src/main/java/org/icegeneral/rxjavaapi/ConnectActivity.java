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
import org.icegeneral.rxjavaapi.connect.ReplayActivity;

/**
 * Created by jianjun.lin on 16/7/11.
 */

public class ConnectActivity extends Activity {

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
                    case 1:
                        startActivity(new Intent(ConnectActivity.this, org.icegeneral.rxjavaapi.connect.ConnectActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(ConnectActivity.this, ReplayActivity.class));
                        break;

                }
            }
        };
        adapter.setData(new String[]{"Publish(将普通的Observable转为可连接的Observable)", "Connect", "RefCount/Share(这2种写法一样，将可连接的Observable转为普通的Observable)", "Replay"});
        rv.setAdapter(adapter);
    }

}
