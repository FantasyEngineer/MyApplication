package org.icegeneral.rxjavaapi.condition;

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

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class TakeUntilActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> observable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("TakeUntil正好跟SkipUntil相反，当第二个Observable发射了一项数据那一刻，SkipUntil开始发射，而TakeUntil终止发射。\n\n" +
                "Observable observable2 = Observable.just(0, 1, 2).delay(200, TimeUnit.MILLISECONDS);\n" +
                "\n" +
                "observable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "    @Override\n" +
                "    public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       for (int i = 0; i < 5; i++) {\n" +
                "           subscriber.onNext(i);\n" +
                "           try {\n" +
                "               Thread.sleep(100);\n" +
                "           } catch (InterruptedException e) {\n" +
                "                e.printStackTrace();\n" +
                "           }\n" +
                "       }\n" +
                "   }\n" +
                "}).skipUntil(observable2);");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        Observable observable2 = Observable.just(0, 1, 2).delay(200, TimeUnit.MILLISECONDS);

        observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).takeUntil(observable2);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
