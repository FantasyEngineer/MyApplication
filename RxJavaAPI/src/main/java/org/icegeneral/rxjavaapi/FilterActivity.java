package org.icegeneral.rxjavaapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.filter.DebounceActivity;
import org.icegeneral.rxjavaapi.filter.DistinctActivity;
import org.icegeneral.rxjavaapi.filter.ElementAtActivity;
import org.icegeneral.rxjavaapi.filter.FirstActivity;
import org.icegeneral.rxjavaapi.filter.IgnoreElementsActivity;
import org.icegeneral.rxjavaapi.filter.LastActivity;
import org.icegeneral.rxjavaapi.filter.ThrottleActivity;
import org.icegeneral.rxjavaapi.filter.SkipActivity;
import org.icegeneral.rxjavaapi.filter.SkipLastActivity;
import org.icegeneral.rxjavaapi.filter.TakeActivity;
import org.icegeneral.rxjavaapi.filter.TakeLastActivity;

public class FilterActivity extends AppCompatActivity {

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
                        startActivity(new Intent(FilterActivity.this, DebounceActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(FilterActivity.this, DistinctActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(FilterActivity.this, ElementAtActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(FilterActivity.this, org.icegeneral.rxjavaapi.filter.FilterActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(FilterActivity.this, FirstActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(FilterActivity.this, LastActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(FilterActivity.this, TakeActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(FilterActivity.this, TakeLastActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(FilterActivity.this, SkipActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(FilterActivity.this, SkipLastActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(FilterActivity.this, IgnoreElementsActivity.class));
                        break;
                    case 11:
                        startActivity(new Intent(FilterActivity.this, ThrottleActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"Debounce", "Distinct", "ElementAt", "Filter/OfType", "First/Single", "Last", "Take", "TakeLast/TakeLastBuffer", "Skip", "SkipLast", "IgnoreElements", "ThrottleFirst/ThrottleLast"});
        rv.setAdapter(adapter);
    }


}
