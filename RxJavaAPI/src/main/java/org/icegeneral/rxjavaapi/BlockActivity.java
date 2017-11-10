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
import org.icegeneral.rxjavaapi.block.ForEachActivity;

/**
 * Created by jianjun.lin on 16/7/11.
 */

public class BlockActivity extends Activity {

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
                        startActivity(new Intent(BlockActivity.this, ForEachActivity.class));
                        break;
                }
            }
        };

        adapter.setData(new String[]{"forEach", "first(阻塞直到Observable发射了一个数据，然后返回第一项数据)", "firstOrDefault(阻塞直到Observable发射了一个数据或者终止，返回第一项数据，或者返回默认值)", "last(阻塞直到Observable终止，然后返回最后一项数据)", "lastOrDefault(阻塞直到Observable终止，然后返回最后一项的数据，或者返回默认值)", "lastest(返回一个iterable，会阻塞直到或者除非Observable发射了一个iterable没有返回的值，然后返回这个值)", "mostRecent(返回一个总是返回Observable最近发射的数据的iterable)", "next(返回一个Iterable，会阻塞直到Observable发射了另一个值，然后返回那个值)", "single(如果Observable终止时只发射了一个值，返回那个值，否则抛出异常)", "singleOrDefault(如果Observable终止时只发射了一个值，返回那个值，否则否好默认值)", "toFuture", "toIterable", "getIterator"});
        rv.setAdapter(adapter);
    }

}
