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
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class DelayActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> delayObservable;
    private Observable<Integer> delaySubscriptionObservable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("delay不会平移onError通知，它会立即将这个通知传递给订阅者，同时丢弃任何待发射的onNext通知。然而它会平移一个onCompleted通知。\n" +
                "例子可以明看到delay是瞬间发送onError而delaySubscription则是延迟2s后才发送onError\n\n" +
                "delayObservable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       for (int i = 0; i < 5; i++) {\n" +
                "           if (i > 2) {\n" +
                "               subscriber.onError(new Throwable(\"Test Error\"));\n" +
                "           }\n" +
                "           subscriber.onNext(i);\n" +
                "       }\n" +
                "       subscriber.onCompleted();\n" +
                "   }\n" +
                "        }).delay(2, TimeUnit.SECONDS);\n" +
                "\n" +
                "delaySubscriptionObservable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       for (int i = 0; i < 5; i++) {\n" +
                "           if (i > 2) {\n" +
                "               subscriber.onError(new Throwable(\"Test Error\"));\n" +
                "           }\n" +
                "           subscriber.onNext(i);\n" +
                "       }\n" +
                "       subscriber.onCompleted();\n" +
                "   }\n" +
                "}).delaySubscription(2, TimeUnit.SECONDS);\n");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
        btn_subscribe.setText("delay");

        Button btn_subscribe2 = (Button) findViewById(R.id.btn_subscribe2);
        btn_subscribe2.setText("delaySubscription");
        btn_subscribe2.setVisibility(View.VISIBLE);

        delayObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    if (i > 2) {
                        subscriber.onError(new Throwable("Test Error"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).delay(2, TimeUnit.SECONDS);

        delaySubscriptionObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    if (i > 2) {
                        subscriber.onError(new Throwable("Test Error"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).delaySubscription(2, TimeUnit.SECONDS);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                delayObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
        btn_subscribe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                delaySubscriptionObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
