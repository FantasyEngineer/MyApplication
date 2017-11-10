package org.icegeneral.rxjavaapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.generate.CreateActivity;
import org.icegeneral.rxjavaapi.generate.DeferActivity;
import org.icegeneral.rxjavaapi.generate.EmptyNeverThrowActivity;
import org.icegeneral.rxjavaapi.generate.FromActivity;
import org.icegeneral.rxjavaapi.generate.IntervalActivity;
import org.icegeneral.rxjavaapi.generate.JustActivity;
import org.icegeneral.rxjavaapi.generate.RangeActivity;
import org.icegeneral.rxjavaapi.generate.RepeatActivity;
import org.icegeneral.rxjavaapi.generate.TimerActivity;

public class GenerateActivity extends AppCompatActivity {

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
                        startActivity(new Intent(GenerateActivity.this, CreateActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(GenerateActivity.this, JustActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(GenerateActivity.this, FromActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(GenerateActivity.this, DeferActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(GenerateActivity.this, IntervalActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(GenerateActivity.this, TimerActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(GenerateActivity.this, RangeActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(GenerateActivity.this, RepeatActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(GenerateActivity.this, EmptyNeverThrowActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"Create", "Just", "From", "Defer", "Interval", "Timer", "Range", "Repeat", "Empty/Never/Throw"});
        rv.setAdapter(adapter);
    }


}
