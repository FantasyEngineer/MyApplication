package org.icegeneral.rxjavaapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.error.CatchActivity;
import org.icegeneral.rxjavaapi.error.RetryActivity;

/**
 * Created by jianjun.lin on 16/7/11.
 */

public class ErrorActivity extends Activity {

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
                        startActivity(new Intent(ErrorActivity.this, CatchActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(ErrorActivity.this, RetryActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"Catch", "Retry"});
        rv.setAdapter(adapter);
    }

}
