package org.icegeneral.rxjavaapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.transform.BufferActivity;
import org.icegeneral.rxjavaapi.transform.FlatMapActivity;
import org.icegeneral.rxjavaapi.transform.GroupByActivity;
import org.icegeneral.rxjavaapi.transform.MapActivity;
import org.icegeneral.rxjavaapi.transform.ScanActivity;
import org.icegeneral.rxjavaapi.transform.WindowActivity;

public class TransformActivity extends AppCompatActivity {

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
                        startActivity(new Intent(TransformActivity.this, MapActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(TransformActivity.this, FlatMapActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(TransformActivity.this, BufferActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(TransformActivity.this, WindowActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(TransformActivity.this, ScanActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(TransformActivity.this, GroupByActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"Map/Cast", "FlatMap/ConcatMap/SwitchMap", "Buffer", "Window", "Scan", "GroupBy"});
        rv.setAdapter(adapter);
    }


}
