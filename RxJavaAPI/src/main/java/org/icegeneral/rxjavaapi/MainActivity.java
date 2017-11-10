package org.icegeneral.rxjavaapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.icegeneral.rxjavaapi.connect.*;
import org.icegeneral.rxjavaapi.error.CatchActivity;

import java.util.concurrent.locks.Condition;

public class MainActivity extends AppCompatActivity {


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
                        startActivity(new Intent(MainActivity.this, GenerateActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, TransformActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, FilterActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, CombineActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, ErrorActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, AssistActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, ConvertActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this, ConditionActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this, ConnectActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(MainActivity.this, BlockActivity.class));
                        break;
                }
            }
        };
        adapter.setData(new String[]{"创建操作", "变换操作", "过滤操作", "结合操作", "错误处理", "辅助操作", "转换操作", "条件和布尔操作", "连接操作(ConnectableObservable)", "阻塞操作", "字符串操作(StringObservable)", "算数和聚合操作(rxjava-math)", "异步操作(rxjava-async)"});
        rv.setAdapter(adapter);
    }


}
