package org.icegeneral.rxjavaapi.assist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.schedulers.Timestamped;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class TimestampActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Timestamped<Integer>> observable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("Timestamp将一个发射T类型数据的Observable转换为一个发射类型为Timestamped<T>的数据的Observable，每一项都包含数据的原始发射时间。\n\n" +
                "Observable<Timestamped<Integer>> observable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       for (int i = 0; i < 3; i++) {\n" +
                "           try {\n" +
                "               Thread.sleep(i * 200);\n" +
                "           } catch (InterruptedException e) {\n" +
                "               e.printStackTrace();\n" +
                "           }\n" +
                "           subscriber.onNext(i);\n" +
                "       }\n" +
                "   }\n" +
                "}).subscribeOn(Schedulers.io()).Timestamped();");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.create(new Observable.OnSubscribe<Integer>() {
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
        }).subscribeOn(Schedulers.io()).timestamp();

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Timestamped<Integer>>() {
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
                    public void onNext(Timestamped<Integer> o) {
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
