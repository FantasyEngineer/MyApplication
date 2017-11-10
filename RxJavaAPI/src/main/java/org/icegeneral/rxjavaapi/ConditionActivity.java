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
import org.icegeneral.rxjavaapi.condition.AllActivity;
import org.icegeneral.rxjavaapi.condition.AmbActivity;
import org.icegeneral.rxjavaapi.condition.ContainsActivity;
import org.icegeneral.rxjavaapi.condition.DefaultIfEmptyActivity;
import org.icegeneral.rxjavaapi.condition.ExistActivity;
import org.icegeneral.rxjavaapi.condition.IsEmptyActivity;
import org.icegeneral.rxjavaapi.condition.SequenceEqualActivity;
import org.icegeneral.rxjavaapi.condition.SkipUntilActivity;
import org.icegeneral.rxjavaapi.condition.SkipWhileActivity;
import org.icegeneral.rxjavaapi.condition.TakeUntilActivity;
import org.icegeneral.rxjavaapi.condition.TakeWhileActivity;

/**
 * Created by jianjun.lin on 16/7/11.
 */

public class ConditionActivity extends Activity {

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
                        startActivity(new Intent(ConditionActivity.this, AllActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(ConditionActivity.this, AmbActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(ConditionActivity.this, ContainsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(ConditionActivity.this, IsEmptyActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(ConditionActivity.this, ExistActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(ConditionActivity.this, DefaultIfEmptyActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(ConditionActivity.this, SequenceEqualActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(ConditionActivity.this, SkipUntilActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(ConditionActivity.this, SkipWhileActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(ConditionActivity.this, TakeUntilActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(ConditionActivity.this, TakeWhileActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"All", "Amb", "Contains", "IsEmpty", "Exist", "DefaultIfEmpty", "SequenceEqual", "SkipUntil", "SkipWhile", "TakeUntil", "TakeWhile"});
        rv.setAdapter(adapter);
    }

}
