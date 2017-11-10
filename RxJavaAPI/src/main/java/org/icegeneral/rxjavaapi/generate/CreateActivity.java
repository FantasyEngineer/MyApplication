package org.icegeneral.rxjavaapi.generate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class CreateActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("从头开始创建一个Observable\n\n" +
                "observable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "            @Override\n" +
                "            public void call(Subscriber<? super Integer> observer) {\n" +
                "                try {\n" +
                "                    for (int i = 0; i < 3; i++) {\n" +
                "                        observer.onNext(i);\n" +
                "                    }\n" +
                "                    observer.onCompleted();\n" +
                "                } catch (Exception e) {\n" +
                "                    observer.onError(e);\n" +
                "                }\n" +
                "            }\n" +
                "        });");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {
                    for (int i = 0; i < 3; i++) {
                        observer.onNext(i);
                        Log.d("RxJavaAPI", "create call() " + i);
                    }
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        });

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        sb.append("onCompleted()");
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }

                    @Override
                    public void onError(Throwable e) {
                        sb.append("onError() ");
                        sb.append(e.getMessage());
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }

                    @Override
                    public void onNext(Integer o) {
                        sb.append("onNext() ");
                        sb.append(o);
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });
    }
}
