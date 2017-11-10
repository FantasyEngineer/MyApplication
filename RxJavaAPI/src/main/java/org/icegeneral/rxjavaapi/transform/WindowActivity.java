package org.icegeneral.rxjavaapi.transform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class WindowActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Observable<Integer>> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("window非常类似buffer操作符，区别在于buffer操作符产生的结果是一个List，而window操作符产生的结果是一个Observable，订阅者可以对这个结果Observable重新进行订阅处理。window有多个重载方法，比如window(count, skip)、window(long,TimeUnit)，例子不一一列举。\n" +
                "\n\n" +
                "Observable<Observable<Integer>> observable = Observable.just(0, 1, 2).buffer(2); //订阅者onNext()收到的是一个Observable<Integer>，再订阅");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.just(0, 1, 2).window(2);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.subscribe(new Subscriber<Observable<Integer>>() {
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
                    public void onNext(Observable<Integer> o) {
                        sb.append("onNext() ");
                        sb.append(o.toString());
                        sb.append(" ");
                        sb.append("\n");
                        tv_bottom.setText(sb);
                        o.subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                sb.append("---sub onCompleted()");
                                sb.append("\n");
                                tv_bottom.setText(sb);
                            }

                            @Override
                            public void onError(Throwable e) {
                                sb.append("---sub onError() ");
                                sb.append(e.getMessage());
                                sb.append("\n");
                                tv_bottom.setText(sb);
                            }

                            @Override
                            public void onNext(Integer integer) {
                                sb.append("---sub onNext() ");
                                sb.append(integer);
                                sb.append(" ");
                                sb.append("\n");
                                tv_bottom.setText(sb);
                            }
                        });
                    }
                });
            }
        });
    }
}
