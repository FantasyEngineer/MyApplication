package org.icegeneral.rxjavaapi.convert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class ToFutureActivity extends AppCompatActivity {

    private TextView tv_bottom;


    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("将Observable转换为一个返回单个数据项的Future，如果原始Observable发射多个数据项，Future会收到一个IllegalArgumentException；如果原始Observable没有发射任何数据，Future会收到一个NoSuchElementException。\n" +
                "\n" +
                "如果你想将发射多个数据项的Observable转换为Future，可以这样用：myObservable.toList().toBlocking().toFuture()。\n" +
                "\n\n" +
                "btn_subscribe.setOnClickListener(new View.OnClickListener() {\n" +
                "   @Override\n" +
                "   public void onClick(View v) {\n" +
                "       sb.setLength(0);\n" +
                "       tv_bottom.setText(sb.toString());\n" +
                "       Future<List<Integer>> future = Observable.range(0, 3).toList().toBlocking().toFuture();\n" +
                "       try {\n" +
                "           sb.append(future.get());\n" +
                "       } catch (InterruptedException e) {\n" +
                "           sb.append(e.getMessage());\n" +
                "           e.printStackTrace();\n" +
                "       } catch (ExecutionException e) {\n" +
                "           sb.append(e.getMessage());\n" +
                "           e.printStackTrace();\n" +
                "       }\n" +
                "       tv_bottom.setText(sb.toString());\n" +
                "   }\n" +
                "});");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                Future<List<Integer>> future = Observable.range(0, 3).toList().toBlocking().toFuture();
                try {
                    sb.append(future.get());
                } catch (InterruptedException e) {
                    sb.append(e.getMessage());
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    sb.append(e.getMessage());
                    e.printStackTrace();
                }
                tv_bottom.setText(sb.toString());
            }
        });

    }
}
