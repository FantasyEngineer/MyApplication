package org.icegeneral.rxjavaapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.convert.GetIteratorActivity;
import org.icegeneral.rxjavaapi.convert.NestActivity;
import org.icegeneral.rxjavaapi.convert.ToFutureActivity;
import org.icegeneral.rxjavaapi.convert.ToIterableActivity;
import org.icegeneral.rxjavaapi.convert.ToListActivity;
import org.icegeneral.rxjavaapi.convert.ToMapActivity;
import org.icegeneral.rxjavaapi.convert.ToMultiMapActivity;
import org.icegeneral.rxjavaapi.convert.ToSortedActivity;

import java.util.concurrent.Future;

public class ConvertActivity extends AppCompatActivity {

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
                        startActivity(new Intent(ConvertActivity.this, ToListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(ConvertActivity.this, ToMapActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(ConvertActivity.this, ToSortedActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(ConvertActivity.this, ToMultiMapActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(ConvertActivity.this, ToIterableActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(ConvertActivity.this, GetIteratorActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(ConvertActivity.this, ToFutureActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(ConvertActivity.this, NestActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"ToList", "ToMap", "ToSortedList", "ToMultiMap", "ToIterable", "GetIterator", "Nest"});
        rv.setAdapter(adapter);
    }


}
