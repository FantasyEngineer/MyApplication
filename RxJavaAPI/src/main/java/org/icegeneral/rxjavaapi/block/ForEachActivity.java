package org.icegeneral.rxjavaapi.block;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class ForEachActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("对Observable发射的每一项数据调用一个方法，会阻塞直到Observable完成。\n\n" +
                "btn_subscribe.setOnClickListener(new View.OnClickListener() {\n" +
                "   @Override\n" +
                "   public void onClick(View v) {\n" +
                "       sb.setLength(0);\n" +
                "       tv_bottom.setText(sb.toString());\n" +
                "       Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "           @Override\n" +
                "           public void call(Subscriber<? super Integer> subscriber) {\n" +
                "               for (int i = 0; i < 3; i++) {\n" +
                "                   try {\n" +
                "                       Thread.sleep(i * 200);\n" +
                "                   } catch (InterruptedException e) {\n" +
                "                       e.printStackTrace();\n" +
                "                   }\n" +
                "                   subscriber.onNext(i);\n" +
                "               }\n" +
                "           }\n" +
                "       }).subscribeOn(AndroidSchedulers.mainThread()).forEach(new Action1<Integer>() {\n" +
                "           @Override\n" +
                "           public void call(Integer integer) {\n" +
                "               sb.append(integer);\n" +
                "               sb.append(\" \");\n" +
                "               tv_bottom.setText(sb);\n" +
                "           }\n" +
                "       });\n" +
                "   }\n" +
                "});");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);


        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 3; i++) {
                            try {
                                Thread.sleep(i * 200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(i);
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).forEach(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        sb.append(integer);
                        sb.append(" ");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });

    }
}
